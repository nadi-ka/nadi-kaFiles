package src.main.java.by.epam.ts.servlet.sessionContent;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/*I plan, that the instance of this type will be passed to 
 * defineCommand(HttpServletRequest request) of ActionFactory 
 * instead of HttpServletRequest.
 * Is it a good idea to your mind or better leave how it is?
 */

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
