package by.epam.ts.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String surname;
	private String name;
	private LocalDate dateOfBirth;
	private String email;
	private List<PatientDiagnosis> diagnosisList;
	private List<Treatment> prescriptions;
	private List<Hospitalization> hospitalizations;

	public Patient() {
	}

	public Patient(String id, String surname, String name, LocalDate dateOfBirth, String email) {
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
	}

	public Patient(String id, String surname, String name, LocalDate dateOfBirth, String email,
			List<PatientDiagnosis> diagnosisList, List<Treatment> prescriptions,
			List<Hospitalization> hospitalizations) {
		this(id, surname, name, dateOfBirth, email);
		this.diagnosisList = diagnosisList;
		this.prescriptions = prescriptions;
		this.hospitalizations = hospitalizations;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PatientDiagnosis> getDiagnosisList() {
		return diagnosisList;
	}

	public void setDiagnosisList(List<PatientDiagnosis> diagnosisList) {
		this.diagnosisList = diagnosisList;
	}

	public List<Treatment> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<Treatment> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public List<Hospitalization> getHospitalizations() {
		return hospitalizations;
	}

	public void setHospitalizations(List<Hospitalization> hospitalizations) {
		this.hospitalizations = hospitalizations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((diagnosisList == null) ? 0 : diagnosisList.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prescriptions == null) ? 0 : prescriptions.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((hospitalizations == null) ? 0 : hospitalizations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Patient other = (Patient) obj;

		return (id == other.id || (id != null && id.equals(other.getId())))
				&& (surname == other.surname || (surname != null && surname.equals(other.getSurname())))
				&& (name == other.name || (name != null && name.equals(other.getName())))
				&& (dateOfBirth == other.dateOfBirth
						|| (dateOfBirth != null && dateOfBirth.equals(other.getDateOfBirth())))
				&& (email == other.email || (email != null && email.equals(other.getEmail())))
				&& (prescriptions == other.prescriptions
						|| (prescriptions != null && prescriptions.equals(other.getPrescriptions())))
				&& (diagnosisList == other.diagnosisList
						|| (diagnosisList != null && diagnosisList.equals(other.getDiagnosisList())))
				&& (hospitalizations == other.hospitalizations
						|| (hospitalizations != null && hospitalizations.equals(other.getHospitalizations())));
	}

	@Override
	public String toString() {
		return getClass().getName() + "@[id = " + id + ", surname = " + surname + ", name = " + name
				+ ", dateOfBirth = " + dateOfBirth.toString() + ", email = " + email + "]";
	}

}
