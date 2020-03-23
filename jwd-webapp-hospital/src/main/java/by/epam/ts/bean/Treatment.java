package by.epam.ts.bean;

import java.io.Serializable;
import java.util.Date;

public class Treatment implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idAppointment;
	private String idPatient;
	private String treatmentType;
	private String treatmentName;
	private String doctorSurname;
	private String doctorName;
	private Date dateBeginning;
	private Date dateFinishing;
	private boolean consent;

	public Treatment() {
	}

	public Treatment(int idAppointment, String idPatient, String treatmentType, String treatmentName,
			String doctorSurname, String doctorName, Date dateBeggining, Date dateFinishing, boolean consent) {

		this.idAppointment = idAppointment;
		this.idPatient = idPatient;
		this.treatmentType = treatmentType;
		this.treatmentName = treatmentName;
		this.doctorSurname = doctorSurname;
		this.doctorName = doctorName;
		this.dateBeginning = dateBeggining;
		this.dateFinishing = dateFinishing;
		this.consent = consent;
	}

	public int getIdAppointment() {
		return idAppointment;
	}

	public void setIdAppointment(int idAppointment) {
		this.idAppointment = idAppointment;
	}

	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getTreatmentName() {
		return treatmentName;
	}

	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}

	public String getDoctorSurname() {
		return doctorSurname;
	}

	public void setDoctorSurname(String doctorSurname) {
		this.doctorSurname = doctorSurname;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Date getDateBeggining() {
		return dateBeginning;
	}

	public void setDateBeggining(Date dateBeggining) {
		this.dateBeginning = dateBeggining;
	}

	public Date getDateFinishing() {
		return dateFinishing;
	}

	public void setDateFinishing(Date dateFinishing) {
		this.dateFinishing = dateFinishing;
	}

	public boolean isConsent() {
		return consent;
	}

	public void setConsent(boolean consent) {
		this.consent = consent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (consent ? 1 : 0);
		result = prime * result + ((idPatient == null) ? 0 : idPatient.hashCode());
		result = prime * result + ((dateBeginning == null) ? 0 : dateBeginning.hashCode());
		result = prime * result + ((dateFinishing == null) ? 0 : dateFinishing.hashCode());
		result = prime * result + idAppointment;
		result = prime * result + ((doctorSurname == null) ? 0 : doctorSurname.hashCode());
		result = prime * result + ((doctorName == null) ? 0 : doctorName.hashCode());
		result = prime * result + ((treatmentName == null) ? 0 : treatmentName.hashCode());
		result = prime * result + ((treatmentType == null) ? 0 : treatmentType.hashCode());
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
		Treatment other = (Treatment) obj;
		return idAppointment != other.idAppointment && consent != other.consent
				&& (idPatient == other.idPatient || (idPatient != null && idPatient.equals(other.getIdPatient())))
				&& (treatmentType == other.treatmentType
						|| (treatmentType != null && treatmentType.equals(other.getTreatmentType())))
				&& (treatmentName == other.treatmentName
						|| (treatmentName != null && treatmentName.equals(other.getTreatmentName())))
				&& (doctorSurname == other.doctorSurname
						&& (doctorSurname != null && doctorSurname.equals(other.getDoctorSurname())))
				&& (doctorName == other.doctorName || (doctorName != null && doctorName.equals(other.getDoctorName())))
				&& (dateBeginning == other.dateBeginning
						|| (dateBeginning != null && dateBeginning.equals(other.getDateBeggining())))
				&& (dateFinishing == other.dateFinishing
						|| (dateFinishing != null && dateFinishing.equals(other.getDateFinishing())));

	}

	@Override
	public String toString() {
		return getClass().getName() + "@[idAppointment=" + idAppointment + ", treatmentType=" + treatmentType
				+ ", treatmentName=" + treatmentName + ", doctorSurname=" + doctorSurname + ", doctorName = "
				+ doctorName + ", dateBeggining=" + dateBeginning + ", dateFinishing=" + dateFinishing + ", consent="
				+ consent + "]";
	}

}
