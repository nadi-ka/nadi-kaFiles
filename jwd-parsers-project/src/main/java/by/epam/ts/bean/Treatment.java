package by.epam.ts.bean;

import java.io.Serializable;

public class Treatment implements Serializable, Comparable<Treatment> {
	private static final long serialVersionUID = 1L;

	private int idAppointment;
	private String treatmentName;
	private String doctorSurname;

	public Treatment() {
	}

	public Treatment(int idAppointment, String treatmentName, String doctorSurname) {

		this.idAppointment = idAppointment;
		this.treatmentName = treatmentName;
		this.doctorSurname = doctorSurname;
	}

	public int getIdAppointment() {
		return idAppointment;
	}

	public void setIdAppointment(int idAppointment) {
		this.idAppointment = idAppointment;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idAppointment;
		result = prime * result + ((doctorSurname == null) ? 0 : doctorSurname.hashCode());
		result = prime * result + ((treatmentName == null) ? 0 : treatmentName.hashCode());
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
		return idAppointment != other.idAppointment
				&& (treatmentName == other.treatmentName
						|| (treatmentName != null && treatmentName.equals(other.getTreatmentName())))
				&& (doctorSurname == other.doctorSurname
						&& (doctorSurname != null && doctorSurname.equals(other.getDoctorSurname())));

	}

	@Override
	public String toString() {
		return getClass().getName() + "@[idAppointment=" + idAppointment + ", treatmentName="
				+ treatmentName + ", doctorSurname=" + doctorSurname + "]";
	}

	@Override
	public int compareTo(Treatment o) {
		return Integer.compare(idAppointment, o.idAppointment);
	}

}
