package by.epam.ts.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

public class PatientDiagnosis implements Serializable {
	private static final long serialVersionUID = 1L;

	private String idPatient;
	private String codeByICD;
	private boolean primary;
	private LocalDate settingDate;
	private String diagnosisName;

	public PatientDiagnosis() {
	}

	public PatientDiagnosis(String idPatient, String codeByICD, boolean primary, LocalDate settingDate) {
		this.idPatient = idPatient;
		this.codeByICD = codeByICD;
		this.primary = primary;
		this.settingDate = settingDate;
	}

	public PatientDiagnosis(String idPatient, String codeByICD, boolean isPrimary, LocalDate settingDate,
			String diagnosisName) {
		this(idPatient, codeByICD, isPrimary, settingDate);
		this.diagnosisName = diagnosisName;
	}

	public String getCodeByICD() {
		return codeByICD;
	}

	public void setCodeByICD(String codeByICD) {
		this.codeByICD = codeByICD;
	}

	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public LocalDate getSettingDate() {
		return settingDate;
	}

	public void setSettingDate(LocalDate settingDate) {
		this.settingDate = settingDate;
	}

	public String getDiagnosisName() {
		return diagnosisName;
	}

	public void setDiagnosisName(String diagnosisName) {
		this.diagnosisName = diagnosisName;
	}

	public static Comparator<PatientDiagnosis> primaryDiagnosisComparator = new Comparator<PatientDiagnosis>() {
		@Override
		public int compare(PatientDiagnosis o1, PatientDiagnosis o2) {
			return Boolean.compare(o2.isPrimary(), o1.isPrimary());
		}
	};

	public static Comparator<PatientDiagnosis> diagnosisDateComparator = new Comparator<PatientDiagnosis>() {
		@Override
		public int compare(PatientDiagnosis o1, PatientDiagnosis o2) {
			LocalDate date1 = o1.getSettingDate();
			LocalDate date2 = o2.getSettingDate();
			return date2.compareTo(date1);
		}
	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeByICD == null) ? 0 : codeByICD.hashCode());
		result = prime * result + ((idPatient == null) ? 0 : idPatient.hashCode());
		result = prime * result + (primary ? 1231 : 1237);
		result = prime * result + ((settingDate == null) ? 0 : settingDate.hashCode());
		result = prime * result + ((diagnosisName == null) ? 0 : diagnosisName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PatientDiagnosis other = (PatientDiagnosis) obj;
		if (codeByICD == null) {
			if (other.codeByICD != null) {
				return false;
			}
		} else if (!codeByICD.equals(other.codeByICD)) {
			return false;
		}
		if (idPatient == null) {
			if (other.idPatient != null) {
				return false;
			}
		} else if (!idPatient.equals(other.idPatient)) {
			return false;
		}
		if (primary != other.primary) {
			return false;
		}
		if (settingDate == null) {
			if (other.settingDate != null) {
				return false;
			}
		} else if (!settingDate.equals(other.settingDate)) {
			return false;
		}
		if (diagnosisName == null) {
			if (other.diagnosisName != null) {
				return false;
			}
		} else if (!diagnosisName.equals(other.diagnosisName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PatientDiagnosis [codeByICD=" + codeByICD + ", idPatient=" + idPatient + ", primary=" + primary
				+ ", settingDate=" + settingDate + ", diagnosisName=" + diagnosisName + "]";
	}

}
