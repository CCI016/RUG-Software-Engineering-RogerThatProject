//package org.rogerthat.endpoints;
//
//import java.io.IOException;
//import java.util.Base64;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//import javax.ws.rs.HttpMethod;
//
//
//@WebFilter(urlPatterns = "/*", filterName = "RequestsFilter")
//public class RestWebFilter implements Filter {
//
//	@Transactional
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		final HttpServletRequest servletRequest = (HttpServletRequest) request;
//		final HttpServletResponse servletResponse = (HttpServletResponse) response;
//
//		servletResponse.setHeader("Access-Control-Allow-Origin", "*");
//		servletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//		servletResponse.setHeader("Access-Control-Max-Age", "1000");
//		servletResponse.setHeader("Access-Control-Allow-Headers",
//				"x-requested-with, Content-Type, origin, authorization, "
//						+ "username, accept, client-security-token, Session-Token");
//		if (servletRequest.getMethod().equals(HttpMethod.OPTIONS)) {
//			servletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
//			return;
//		}
//
//		if (!restFilter(servletRequest, servletResponse)) {
//			return;
//		}
//
//		chain.doFilter(request, response);
//
//	}
//
//	private boolean restFilter(HttpServletRequest request, HttpServletResponse servletResponse) {
//		/* Filter out all Urls that do not point to rest endpoints */
//		/* Or do not have Auth token */
//		/* URLs that do not have Auth token */
//		// String webSockets = ""; /rest/ws?
//		// String logins = ""; /rest/auth/login??
//		if (!request.getRequestURI().contains("/rest/") || request.getRequestURI().equals(webSockets)
//				|| request.getRequestURI().equals(logins)) {
//			return true;
//		} else {
//			/*
//			 * Check token for validity Return 403 if token is invalid
//			 */
//			Sessions session = access(request);
//			if (session != null) {
//				return true;
//			} else {
//				if (request.getHeader("password") != null
//					&& request.getHeader("password").equals("XfZqTGFQ")) {
//					return true;
//				}
//				try {
//					servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				return false;
//			}
//		}
//	}
//
//	// Work in progress...
////	private Sessions access(HttpServletRequest request) {
////		/* Decode token */
////		String uuid = null;
////		if (request.getHeader("Authorization") != null) {
////			try {
////				uuid = new String(Base64.getDecoder().decode(request.getHeader("Authorization")));
////			} catch (IllegalArgumentException iae) {
////				uuid = "null";
////			}
////		}
////		/* Session or null if there are no session with that token */
////		return Sessions.find("session_id = ?1 and isEnded = false", uuid).firstResult();
////	}
////
////}
