package by.epam.ts.service.factory;

import by.epam.ts.service.DiagnosisService;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.UserService;

public interface ServiceFactory {

	UserService getUserService();

	TreatmentService getTreatmentService();

	DiagnosisService getDiagnosisService();

	HospitalizationService getHospitalizationService();

}
