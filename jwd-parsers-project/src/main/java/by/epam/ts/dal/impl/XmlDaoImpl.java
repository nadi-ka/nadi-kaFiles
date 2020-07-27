package by.epam.ts.dal.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;

import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.XmlDao;

public class XmlDaoImpl implements XmlDao {

	private final static String delimiter = "\\";

	// In Service we need to use InputStream twice: for validation and parsing - so
	// will read in byte[];
	public byte[] readFromXmlFile(String xmlFileName) throws DaoException {
		byte[] bytes;
		ResourceBundle bundle = ResourceBundle.getBundle("file_location");
		String directoryName = bundle.getString("file.location");
		try {
			FileInputStream inputStream = new FileInputStream(directoryName + delimiter + xmlFileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(inputStream, baos);
			bytes = baos.toByteArray();
		} catch (FileNotFoundException e) {
			throw new DaoException("Error during calling createInputForSax(String xmlFileName) from XmlDaoImpl. File "
					+ xmlFileName + " wasn't found.", e);
		} catch (IOException e) {
			throw new DaoException("Error when calling IOUtils.copy(inputStream, baos).", e);
		}
		return bytes;
	}

}
