package by.epam.ts.dal;

import java.time.LocalDate;
import java.util.List;

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.PatientDiagnosis;

public interface DiagnosisDao {
	
	List<PatientDiagnosis> findPatientsDiagnosisById(String id) throws DaoException;

	List<PatientDiagnosis> findCurrentDiagnosisById(String id, LocalDate entryDate) throws DaoException;
	
	int createNewDiagnosis(Diagnosis diagnosis) throws DaoException;
	
	List<Diagnosis> readAllDiagnosis() throws DaoException;

	int[] createPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws DaoException;
	
	List<Diagnosis> findShortDiagnosisByIdAndDate(String id, LocalDate hospitalizationDate) throws DaoException;

}
