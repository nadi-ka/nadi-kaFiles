package by.epam.ts.dal.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.XmlDao;

public class XmlDaoImpl implements XmlDao {

	private final static String directoryName = "C:\\Users\\Vadim\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\jwd-parsers-project\\upload";
	private final static String delimiter = "\\";

	private static final Logger log = LogManager.getLogger(XmlDaoImpl.class);

	// In Service we need to use InputStream twice: for validation and parsing - so
	// will read in byte[];
	public byte[] readFromXmlFile(String xmlFileName) throws DaoException {
		byte[] bytes;
		try {
			FileInputStream inputStream = new FileInputStream(directoryName + delimiter + xmlFileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(inputStream, baos);
			bytes = baos.toByteArray();
		} catch (FileNotFoundException e) {
			log.log(Level.ERROR, "Error during calling createInputForSax(String xmlFileName) from XmlDaoImpl. File "
					+ xmlFileName + " wasn't found.", e);
			throw new DaoException("Error during calling createInputForSax(String xmlFileName) from XmlDaoImpl. File "
					+ xmlFileName + " wasn't found.", e);
		} catch (IOException e) {
			log.log(Level.ERROR, "Error when calling IOUtils.copy(inputStream, baos).", e);
			throw new DaoException("Error when calling IOUtils.copy(inputStream, baos).", e);
		}
		return bytes;
	}

}
