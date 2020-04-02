package by.epam.ts.controller.manager;

import java.util.ResourceBundle;

public class MessageManager {
	
	private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("localization.locale");
	
	private MessageManager() {}
	
	public static String getProperty(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}
	

}
