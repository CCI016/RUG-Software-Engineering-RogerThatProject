package org.rogerthat.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.rogerthat.orm.*;
import org.rogerthat.services.CSVParser;
import org.rogerthat.services.PDFGenerator;

import javax.json.JsonArray;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.StreamingOutput;


/**
 * This Endpoint will be used by users who want to help us improve the ML model.
 */
@Path("/rest/transaction")
public class TransactionsEndpoint {

	private final ObjectMapper mapper;

	public TransactionsEndpoint() {
		mapper = new ObjectMapper();
	}

	/**
	 * @param id User id
	 * @return List of transactions that could not be categorized
	 * @throws JsonProcessingException
	 */
	@GET
	@Path("getById")
	@Transactional
	public Response getTransactionsById(@QueryParam("id") Long id) throws JsonProcessingException {
		System.out.println(id);
		User user = User.findById(id);
		if (user == null) {
			return Response.status(500).build();
		}
		Person person = user.person;
		if (person.transactions.size() == 0) {
			List<Transactions> transactions = new ArrayList<>();
			return Response.ok(mapper.writeValueAsString(transactions)).build();
		}
		List<Transactions> transactions = person.transactions.stream()
				.filter(t -> t.spendingCategory == SpendingCategories.UNKNOWN).collect(Collectors.toList());

		List<Transactions> returnTranscations = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			if (transactions.get(i) != null) {
				returnTranscations.add(transactions.get(i));
			}
		}

		return Response.ok(mapper.writeValueAsString(returnTranscations)).build();
	}

	/**
	 * Here the program will parse the user's input, will save new entries into our data model,
	 * will update the transactions for all Users (Can be modified)
	 * @param body categorized transactions.
	 * @return Code
	 */
	@POST
	@Path("updateCategorization")
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response updateCategorizationByWord(String body) {
		try {
			JsonArray json = Json.createReader(new StringReader(body)).readArray();
			for (int i = 0; i < json.size(); i++) {
				JsonObject jsonObject = json.get(i).asJsonObject();
				SpendingClassification current = new SpendingClassification();
				current.wordAssociated = jsonObject.getString("name");
				current.category = getSpendingCategory(jsonObject.getString("spendingCategory"));
				if (current.category != SpendingCategories.UNKNOWN) {
					current.persist();
				}
			}
			CSVParser csvParser = new CSVParser();
			csvParser.recategorizeTransactions();
		} catch (Exception e) {
			return Response.status(500).build();
		}
		return Response.status(200).build();
	}

	/**
	 * Calls PDF generator, which creates a file that will be downloaded by the user
	 * @param id user id
	 * @return byte stream
	 */
	@GET
	@Path("downloadOverview")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Transactional
	public Response getOverview(@QueryParam("id") Long id) {
		User user = User.findById(id);
		if (user == null) {
			return Response.status(500).build();
		} else {
			Person person = user.person;
			PDFGenerator pdfGenerator = new PDFGenerator();
			int i = pdfGenerator.generatePDF(person.id);
			StreamingOutput stream = output -> {
				File file = new File("overview.pdf");
				byte[] data = null;
				if (file != null) {
					try {
						data = Files.readAllBytes(file.toPath());
					} catch (IOException e) {
						System.out.println("Could not read bytes");
					}
					output.write(data);
					output.flush();
					file.delete();
				}

			};
			return Response.ok(stream, MediaType.APPLICATION_OCTET_STREAM).build();
		}
	}

	private SpendingCategories getSpendingCategory(String s) {
		switch (s) {
			case "ENTERTAINMENT":
				return SpendingCategories.ENTERTAINMENT;
			case "HOUSING":
				return SpendingCategories.HOUSING;
			case "EATING_OUT" :
				return SpendingCategories.EATING_OUT;
			case "CLOTHES" :
				return SpendingCategories.CLOTHES;
			case "OTHER" :
				return SpendingCategories.OTHER;
			default:
				return SpendingCategories.UNKNOWN;

		}
	}
}
