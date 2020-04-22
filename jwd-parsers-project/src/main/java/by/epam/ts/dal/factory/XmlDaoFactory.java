package by.epam.ts.dal.factory;

import by.epam.ts.dal.XmlDao;
import by.epam.ts.dal.impl.XmlDaoImpl;

public final class XmlDaoFactory {
	private final static XmlDaoFactory instance = new XmlDaoFactory();
	private final XmlDao xmlDao = new XmlDaoImpl();
	
	private XmlDaoFactory() {
	}
	
	public static XmlDaoFactory getInstance() {
		return instance;
	}
	
	public XmlDao getXmlDao() {
		return xmlDao;
	}

}
