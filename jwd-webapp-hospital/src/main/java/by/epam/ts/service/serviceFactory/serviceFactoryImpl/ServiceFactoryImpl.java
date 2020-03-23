package by.epam.ts.service.serviceFactory.serviceFactoryImpl;

import by.epam.ts.dal.daoFactory.DaoFactory;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.service.serviceImpl.UserServiceImpl;

public final class ServiceFactoryImpl implements ServiceFactory {

	private final UserService userService;

	public ServiceFactoryImpl(DaoFactory daoFactory) {
		userService = new UserServiceImpl(daoFactory);
	}

	public UserService getUserService() {
		return userService;
	}

}
