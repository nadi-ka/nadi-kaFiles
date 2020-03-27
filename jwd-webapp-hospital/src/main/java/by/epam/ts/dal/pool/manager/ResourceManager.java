package by.epam.ts.dal.pool.manager;

import java.util.ResourceBundle;

public class ResourceManager {
	
	private static final ResourceManager instance = new ResourceManager();

    private ResourceManager() {}

    private ResourceBundle bundle = ResourceBundle.getBundle("dbHospital");

    public static ResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
