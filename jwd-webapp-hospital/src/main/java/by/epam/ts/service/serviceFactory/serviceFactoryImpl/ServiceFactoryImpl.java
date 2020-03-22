package by.epam.ts.service.serviceFactory.serviceFactoryImpl;

import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.service.serviceImpl.UserServiceImpl;

public final class ServiceFactoryImpl implements ServiceFactory{
	private static ServiceFactoryImpl instance;
	private final UserService userService;
	
	private ServiceFactoryImpl() throws ServiceException{
		try {
			userService  = new UserServiceImpl();
		}catch(ServiceException ex) {
			throw new ServiceException("The instance of userServis wasn't created.");
		}
	}
	
	public static ServiceFactory getInstance() throws ServiceException{
		if (instance == null) {
			try {
				instance = new ServiceFactoryImpl();
			} catch (ServiceException ex) {
				throw new ServiceException();
			}
		}
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}
	
}
