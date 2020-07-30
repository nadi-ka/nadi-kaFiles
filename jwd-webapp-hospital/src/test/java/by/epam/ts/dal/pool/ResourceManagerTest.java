package by.epam.ts.dal.pool;

import java.util.ResourceBundle;

public class ResourceManagerTest {

	private static final ResourceManagerTest instance = new ResourceManagerTest();

	private ResourceBundle bundle = ResourceBundle.getBundle("dbTest");

	public static ResourceManagerTest getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return bundle.getString(key);
	}

}
