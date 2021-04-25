package org.rogerthat.endpoints;

import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.rogerthat.orm.CsvFiles;
import org.rogerthat.orm.User;
import org.rogerthat.services.CSVParser;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Path("/rest/file")
public class FileEndpoint {

	@ConfigProperty(name = "csv.directory")
	String csvDirectory;

	@ConfigProperty(name = "csv.standard.filename")
	String standardFileName;

	/**
	 * Endpoint for uploading csv files.
	 * @param input The file itself.
	 * @param userId The id of the user, used for connecting the file with the user in the db.
	 * @return Response code.
	 */
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	@Transactional
	public Response uploadFile(MultipartFormDataInput input, @QueryParam("userId") Long userId) {

		String fileName = "";
		String csvFileName = "";
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
		User user;

		userId = 1L;

		try {
			user = User.findById(userId);
		} catch (Exception e) {
			return Response.status(500).build();
		}

		if (user == null) {
			return Response.status(500).build();
		}

		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
				csvFileName = getSystemFileName();
				//convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class,null);

				byte [] bytes = IOUtils.toByteArray(inputStream);

				writeFile(bytes,csvDirectory + csvFileName);

			} catch (IOException e) {
				e.printStackTrace();
				return Response.status(500).build();
			}

		}

		CsvFiles csvFiles = new CsvFiles();
		csvFiles.originalName = fileName;
		csvFiles.changedName = csvFileName;
		csvFiles.user = user;

		csvFiles.persist();

		// After this we need to call somehow the parser but we should not block the thread;
		CSVParser parser = new CSVParser();
		parser.parse(csvDirectory + csvFileName, userId);

		return Response.status(200).build();

	}


	/**
	 * This function will return the original file name.
	 */
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	private String getSystemFileName() {
		return "test.csv";
//		List<CsvFiles> files= CsvFiles.find("ORDER BY id DESC").list();
//		if (files.size() == 0) {
//			return (standardFileName + "1");
//		}
//		return (standardFileName + files.get(0).id + 1);
	}

	/**
	 * Save the file to a folder.
	 * @param content
	 * @param filename
	 * @throws IOException
	 */
	//save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}

}
