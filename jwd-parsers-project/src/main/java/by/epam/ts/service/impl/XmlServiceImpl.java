package by.epam.ts.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import by.epam.ts.bean.Patient;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.XmlDao;
import by.epam.ts.dal.factory.XmlDaoFactory;
import by.epam.ts.service.XmlService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ServiceValidationException;
import by.epam.ts.service.parser.PatientParserDom;
import by.epam.ts.service.parser.PatientParserSax;
import by.epam.ts.service.parser.PatientParserStax;
import by.epam.ts.service.validation.FileValidator;

public class XmlServiceImpl implements XmlService {

	@Override
	public List<Patient> getPatientsListSAX(String xmlFileName) throws ServiceException {
		XmlDaoFactory xmlDaoFactory = XmlDaoFactory.getInstance();
		XmlDao xmlDao = xmlDaoFactory.getXmlDao();
		List<Patient> patients;
		ByteArrayInputStream bais1 = null;
		ByteArrayInputStream bais2 = null;
		try {
			byte[] bytes = xmlDao.readFromXmlFile(xmlFileName);
			bais1 = new ByteArrayInputStream(bytes);

			// Document validation;
			FileValidator fileValidator = new FileValidator();
			boolean isValid = fileValidator.validateXmlDocument(bais1);
			if (!isValid) {
				throw new ServiceValidationException(
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
			}
			// Parsing;
			bais2 = new ByteArrayInputStream(bytes);
			InputSource inputSource = new InputSource(bais2);
			PatientParserSax parserSax = new PatientParserSax();
			patients = new ArrayList<Patient>();
			patients = parserSax.parseDocumentSax(inputSource);
		} catch (SAXException e) {
			throw new ServiceException(
					"Error during calling parserSax.parseDocumentSax(inputSource) from  XmlServiceImpl.", e);
		} catch (IOException e) {
			throw new ServiceException(
					"Error during calling parserSax.parseDocumentSax(inputSource) from  XmlServiceImpl.", e);
		} catch (DaoException e) {
			throw new ServiceException(
					"Error during calling getPatientsListSAX(String xmlFileName) from  XmlServiceImpl.", e);
		} finally {
			try {
				bais1.close();
				bais2.close();
			} catch (IOException e) {
				throw new ServiceException("Error during attempt to close ByteArrayInputStream.", e);
			}
		}
		return patients;
	}

	public List<Patient> getPatientsListStaX(String xmlFileName) throws ServiceException {
		XmlDaoFactory xmlDaoFactory = XmlDaoFactory.getInstance();
		XmlDao xmlDao = xmlDaoFactory.getXmlDao();
		List<Patient> patients;
		ByteArrayInputStream bais1 = null;
		ByteArrayInputStream bais2 = null;
		try {
			byte[] bytes = xmlDao.readFromXmlFile(xmlFileName);
			bais1 = new ByteArrayInputStream(bytes);

			// Document validation;
			FileValidator fileValidator = new FileValidator();
			boolean isValid = fileValidator.validateXmlDocument(bais1);
			if (!isValid) {
				throw new ServiceValidationException(
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
			}
			// Parsing;
			bais2 = new ByteArrayInputStream(bytes);
			PatientParserStax parserStax = new PatientParserStax();
			patients = parserStax.parseDocumentStax(bais2);
		} catch (XMLStreamException e) {
			throw new ServiceException("Error when parsing in method getPatientsListStaX(String xmlFileName)", e);
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling xmlDao.readFromXmlFile(xmlFileName) from getPatientsListStaX(String xmlFileName)",
					e);
		} finally {
			try {
				bais1.close();
				bais2.close();
			} catch (IOException e) {
				throw new ServiceException("Error during attempt to close ByteArrayInputStream.", e);
			}
		}
		return patients;
	}

	public List<Patient> getPatientsListDOM(String xmlFileName) throws ServiceException {
		XmlDaoFactory xmlDaoFactory = XmlDaoFactory.getInstance();
		XmlDao xmlDao = xmlDaoFactory.getXmlDao();
		List<Patient> patients;
		ByteArrayInputStream bais1 = null;
		ByteArrayInputStream bais2 = null;
		try {
			byte[] bytes = xmlDao.readFromXmlFile(xmlFileName);
			bais1 = new ByteArrayInputStream(bytes);

			// Document validation;
			FileValidator fileValidator = new FileValidator();
			boolean isValid = fileValidator.validateXmlDocument(bais1);
			if (!isValid) {
				throw new ServiceValidationException(
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
			}
			// Parsing;
			bais2 = new ByteArrayInputStream(bytes);
			InputSource inputSource = new InputSource(bais2);
			PatientParserDom parserDom = new PatientParserDom();
			patients = parserDom.parseDocumentDom(inputSource);
		} catch (IOException e) {
			throw new ServiceException("Error when parsing in method getPatientsListDOM(String xmlFileName)", e);
		} catch (DaoException e) {
			throw new ServiceException("Error when calling xmlDao.readFromXmlFile(xmlFileName)", e);
		} catch (SAXException e) {
			throw new ServiceException("Error when parsing in method getPatientsListDOM(String xmlFileName)", e);
		} finally {
			try {
				bais1.close();
				bais2.close();
			} catch (IOException e) {
				throw new ServiceException("Error during attempt to close ByteArrayInputStream.", e);
			}
		}
		return patients;
	}

}
