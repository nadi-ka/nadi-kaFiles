package by.epam.ts.bean;

import java.io.Serializable;
import java.time.LocalDate;

import by.epam.ts.bean.treat_status.TreatmentStatus;

public class CurrentTreatment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idProcedure;
	private int idAppointment;
	private LocalDate datePerforming;
	private String idPerformer;
	private String performerSurname;
	private String performerName;
	private TreatmentStatus status;
	
	public CurrentTreatment() {
	}

	public CurrentTreatment(int idAppointment, LocalDate datePerforming, String idPerformer, TreatmentStatus status) {
		this.idAppointment = idAppointment;
		this.datePerforming = datePerforming;
		this.idPerformer = idPerformer;
		this.status = status;
	}

	public CurrentTreatment(int idProcedure, int idAppointment, LocalDate datePerforming, String idPerformer,
			String performerSurname, String performerName, TreatmentStatus status) {
		this(idAppointment, datePerforming, idPerformer, status);
		this.idProcedure = idProcedure;
		this.performerSurname = performerSurname;
		this.performerName = performerName;
	}

	public int getIdProcedure() {
		return idProcedure;
	}

	public void setIdProcedure(int idProcedure) {
		this.idProcedure = idProcedure;
	}

	public int getIdAppointment() {
		return idAppointment;
	}

	public void setIdAppointment(int idAppointment) {
		this.idAppointment = idAppointment;
	}

	public LocalDate getDatePerforming() {
		return datePerforming;
	}

	public void setDatePerforming(LocalDate datePerforming) {
		this.datePerforming = datePerforming;
	}

	public String getIdPerformer() {
		return idPerformer;
	}

	public void setIdPerformer(String idPerformer) {
		this.idPerformer = idPerformer;
	}

	public String getPerformerSurname() {
		return performerSurname;
	}

	public void setPerformerSurname(String performerSurname) {
		this.performerSurname = performerSurname;
	}

	public String getPerformerName() {
		return performerName;
	}

	public void setPerformerName(String performerName) {
		this.performerName = performerName;
	}

	public TreatmentStatus getStatus() {
		return status;
	}

	public void setStatus(TreatmentStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datePerforming == null) ? 0 : datePerforming.hashCode());
		result = prime * result + idAppointment;
		result = prime * result + ((idPerformer == null) ? 0 : idPerformer.hashCode());
		result = prime * result + idProcedure;
		result = prime * result + ((performerName == null) ? 0 : performerName.hashCode());
		result = prime * result + ((performerSurname == null) ? 0 : performerSurname.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CurrentTreatment other = (CurrentTreatment) obj;
		if (datePerforming == null) {
			if (other.datePerforming != null) {
				return false;
			}
		} else if (!datePerforming.equals(other.datePerforming)) {
			return false;
		}
		if (idAppointment != other.idAppointment) {
			return false;
		}
		if (idPerformer == null) {
			if (other.idPerformer != null) {
				return false;
			}
		} else if (!idPerformer.equals(other.idPerformer)) {
			return false;
		}
		if (idProcedure != other.idProcedure) {
			return false;
		}
		if (performerName == null) {
			if (other.performerName != null) {
				return false;
			}
		} else if (!performerName.equals(other.performerName)) {
			return false;
		}
		if (performerSurname == null) {
			if (other.performerSurname != null) {
				return false;
			}
		} else if (!performerSurname.equals(other.performerSurname)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CurrentTreatment [idProcedure=" + idProcedure + ", idAppointment=" + idAppointment + ", datePerforming="
				+ datePerforming.toString() + ", idPerformer=" + idPerformer + ", performerSurname=" + performerSurname
				+ ", performerName=" + performerName + ", status=" + status.getStatusValue() + "]";
	}

}
