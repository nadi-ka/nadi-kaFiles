package by.epam.ts.service.factory.impl;

import by.epam.ts.service.UserService;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.impl.UserServiceImpl;

public final class ServiceFactoryImpl implements ServiceFactory {
	
	private final static ServiceFactoryImpl instance = new ServiceFactoryImpl();
	private final UserService userService = new UserServiceImpl();
	
	private ServiceFactoryImpl() {}

	public static ServiceFactoryImpl getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}

}
