package com.schein.controller;
import javax.servlet.http.*;

/**
 * @author Dilip Nair
 *<p>
 * This class is used to get the references of the request ,response and session object
 * Makes easy to pass the information to the app controller classes.
 */
public class WorkContext {
	HttpServletRequest	request;
	HttpServletResponse response;
	HttpSession			session;

	/**
	 * Returns the request.
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Returns the response.
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * Returns the session.
	 * @return HttpSession
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * Sets the request.
	 * @param request The request to set
	 * 
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Sets the response.
	 * @param response The response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * Sets the session.
	 * @param session The session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

}
