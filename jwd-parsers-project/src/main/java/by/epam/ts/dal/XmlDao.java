package by.epam.ts.dal;

public interface XmlDao {
	
	byte[] readFromXmlFile(String xmlFileName) throws DaoException;

}
