package by.epam.ts.service.factory;

import by.epam.ts.service.XmlService;
import by.epam.ts.service.impl.XmlServiceImpl;

public final class XmlServiceFactory {
	
	private final static XmlServiceFactory instance = new XmlServiceFactory();
	private final XmlService xmlService = new XmlServiceImpl();
	
	private XmlServiceFactory() {
	}
	
	public static XmlServiceFactory getInstance() {
		return instance;
	}
	
	public XmlService getXmlService() {
		return xmlService;
	}

}
