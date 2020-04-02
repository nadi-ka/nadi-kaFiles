package by.epam.ts.controller.manager;

import java.util.ResourceBundle;

public class NavigationManager {
	
	private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("navigation");
	
	private NavigationManager() {}
	
	public static String getProperty(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}

}
