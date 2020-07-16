package by.epam.ts.service.factory.impl;

import by.epam.ts.service.DiagnosisService;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.UserService;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.impl.DiagnosisServiceImpl;
import by.epam.ts.service.impl.HospitalizationServiceImpl;
import by.epam.ts.service.impl.TreatmentServiceImpl;
import by.epam.ts.service.impl.UserServiceImpl;

public final class ServiceFactoryImpl implements ServiceFactory {
	
	private final static ServiceFactoryImpl instance = new ServiceFactoryImpl();
	private final UserService userService = new UserServiceImpl();
	private final TreatmentService treatmentService = new TreatmentServiceImpl();
	private final DiagnosisService diagnosisService = new DiagnosisServiceImpl();
	private final HospitalizationService hospitalizationService = new HospitalizationServiceImpl();
	
	private ServiceFactoryImpl() {}

	public static ServiceFactoryImpl getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}
	
	public TreatmentService getTreatmentService() {
		return treatmentService;
	}
	
	public DiagnosisService getDiagnosisService() {
		return diagnosisService;
	}
	
	public HospitalizationService getHospitalizationService() {
		return hospitalizationService;
	}

}
