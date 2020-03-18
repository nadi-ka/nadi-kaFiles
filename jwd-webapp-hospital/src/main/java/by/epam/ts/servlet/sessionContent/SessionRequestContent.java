package by.epam.ts.servlet.sessionContent;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class SessionRequestContent {
	private HashMap<String, Object> requestAttributes;
	private HashMap<String, String []> requestParameters;
	private HashMap<String, Object> sessionAttributes;
	
	public SessionRequestContent() {
	}

	public SessionRequestContent(HashMap<String, Object> requestAttributes, HashMap<String, String[]> requestParameters,
			HashMap<String, Object> sessionAttributes) {
		this.requestAttributes = requestAttributes;
		this.requestParameters = requestParameters;
		this.sessionAttributes = sessionAttributes;
	}
	
	public void extractValues(HttpServletRequest request) {
		
	}
	
	public void insertAttributes(HttpServletRequest request) {
		
	}
	
	

}
