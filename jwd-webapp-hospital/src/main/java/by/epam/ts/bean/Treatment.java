package by.epam.ts.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Treatment implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idAppointment;
	private String idPatient;
	private String treatmentType;
	private String treatmentName;
	private String doctorId;
	private String doctorSurname;
	private String doctorName;
	private LocalDate dateBeginning;
	private LocalDate dateFinishing;
	private boolean consent;
	private List<CurrentTreatment> performingList;

	public Treatment() {
	}

	public Treatment(String idPatient, String treatmentType, String treatmentName, String doctorId,
			LocalDate dateBeginning, LocalDate dateFinishing, boolean consent) {

		this.idPatient = idPatient;
		this.treatmentType = treatmentType;
		this.treatmentName = treatmentName;
		this.doctorId = doctorId;
		this.dateBeginning = dateBeginning;
		this.dateFinishing = dateFinishing;
		this.consent = consent;
	}

	public Treatment(int idAppointment, String idPatient, String treatmentType, String treatmentName, String doctorId,
			String doctorSurname, String doctorName, LocalDate dateBeginning, LocalDate dateFinishing,
			boolean consent) {

		this(idPatient, treatmentType, treatmentName, doctorId, dateBeginning, dateFinishing, consent);
		this.idAppointment = idAppointment;
		this.doctorSurname = doctorSurname;
		this.doctorName = doctorName;
	}

	public Treatment(int idAppointment, String idPatient, String treatmentType, String treatmentName, String doctorId,
			String doctorSurname, String doctorName, LocalDate dateBeginning, LocalDate dateFinishing, boolean consent,
			List<CurrentTreatment> performingList) {

		this(idAppointment, idPatient, treatmentType, treatmentName, doctorId, doctorSurname, doctorName, dateBeginning,
				dateFinishing, consent);
		this.performingList = performingList;
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

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
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

	public LocalDate getDateBeginning() {
		return dateBeginning;
	}

	public void setDateBeginning(LocalDate dateBeginning) {
		this.dateBeginning = dateBeginning;
	}

	public LocalDate getDateFinishing() {
		return dateFinishing;
	}

	public void setDateFinishing(LocalDate dateFinishing) {
		this.dateFinishing = dateFinishing;
	}

	public boolean isConsent() {
		return consent;
	}

	public void setConsent(boolean consent) {
		this.consent = consent;
	}

	public List<CurrentTreatment> getPerformingList() {
		return performingList;
	}

	public void setPerformingList(List<CurrentTreatment> performingList) {
		this.performingList = performingList;
	}

	public static Comparator<Treatment> treatmentDateComparator = new Comparator<Treatment>() {
		@Override
		public int compare(Treatment o1, Treatment o2) {
			LocalDate date1 = o1.getDateBeginning();
			LocalDate date2 = o2.getDateBeginning();
			return date2.compareTo(date1);
		}
	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (consent ? 1 : 0);
		result = prime * result + ((idPatient == null) ? 0 : idPatient.hashCode());
		result = prime * result + ((dateBeginning == null) ? 0 : dateBeginning.hashCode());
		result = prime * result + ((dateFinishing == null) ? 0 : dateFinishing.hashCode());
		result = prime * result + idAppointment;
		result = prime * result + ((doctorId == null) ? 0 : doctorId.hashCode());
		result = prime * result + ((doctorSurname == null) ? 0 : doctorSurname.hashCode());
		result = prime * result + ((doctorName == null) ? 0 : doctorName.hashCode());
		result = prime * result + ((treatmentName == null) ? 0 : treatmentName.hashCode());
		result = prime * result + ((treatmentType == null) ? 0 : treatmentType.hashCode());
		result = prime * result + ((performingList == null) ? 0 : performingList.hashCode());
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
				&& (doctorId == other.doctorId || (doctorId != null && doctorId.equals(other.getDoctorId())))
				&& (doctorSurname == other.doctorSurname
						&& (doctorSurname != null && doctorSurname.equals(other.getDoctorSurname())))
				&& (doctorName == other.doctorName || (doctorName != null && doctorName.equals(other.getDoctorName())))
				&& (dateBeginning == other.dateBeginning
						|| (dateBeginning != null && dateBeginning.equals(other.getDateBeginning())))
				&& (dateFinishing == other.dateFinishing
						|| (dateFinishing != null && dateFinishing.equals(other.getDateFinishing())))
				&& (performingList == other.performingList
						|| (performingList != null && performingList.equals(other.getPerformingList())));
	}

	@Override
	public String toString() {
		return getClass().getName() + "@[idAppointment=" + idAppointment + ", treatmentType=" + treatmentType
				+ ", treatmentName=" + treatmentName + ", doctorId=" + doctorId + ", doctorSurname=" + doctorSurname
				+ ", doctorName = " + doctorName + ", dateBeggining=" + dateBeginning.toString() + ", dateFinishing="
				+ dateFinishing.toString() + ", consent=" + consent + "]";
	}

}
