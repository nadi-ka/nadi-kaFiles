package by.epam.ts.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class Hospitalization implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int idMedicalHistory;
	private String idPatient;
	private LocalDate entryDate;
	private LocalDate dischargeDate;
	
	public Hospitalization() {
	}
	
	public Hospitalization(String idPatient, LocalDate entryDate) {
		this.idPatient = idPatient;
		this.entryDate = entryDate;
	}

	public Hospitalization(String idPatient, LocalDate entryDate, LocalDate dischargeDate) {
		this(idPatient, entryDate);
		this.dischargeDate = dischargeDate;
	}
	
	public Hospitalization(int idMedicalHistory, String idPatient, LocalDate entryDate, LocalDate dischargeDate) {
		this(idPatient, entryDate, dischargeDate);
		this.idMedicalHistory = idMedicalHistory;
	}

	public int getIdMedicalHistory() {
		return idMedicalHistory;
	}

	public void setIdMedicalHistory(int idMedicalHistory) {
		this.idMedicalHistory = idMedicalHistory;
	}

	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public LocalDate getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(LocalDate dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dischargeDate == null) ? 0 : dischargeDate.hashCode());
		result = prime * result + ((entryDate == null) ? 0 : entryDate.hashCode());
		result = prime * result + idMedicalHistory;
		result = prime * result + ((idPatient == null) ? 0 : idPatient.hashCode());
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
		Hospitalization other = (Hospitalization) obj;
		if (dischargeDate == null) {
			if (other.dischargeDate != null) {
				return false;
			}
		} else if (!dischargeDate.equals(other.dischargeDate)) {
			return false;
		}
		if (entryDate == null) {
			if (other.entryDate != null) {
				return false;
			}
		} else if (!entryDate.equals(other.entryDate)) {
			return false;
		}
		if (idMedicalHistory != other.idMedicalHistory) {
			return false;
		}
		if (idPatient == null) {
			if (other.idPatient != null) {
				return false;
			}
		} else if (!idPatient.equals(other.idPatient)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Hospitalization [idMedicalHistory=" + idMedicalHistory + ", idPatient=" + idPatient + ", entryDate="
				+ entryDate.toString() + ", dischargeDate=" + dischargeDate.toString() + "]";
	}

}
