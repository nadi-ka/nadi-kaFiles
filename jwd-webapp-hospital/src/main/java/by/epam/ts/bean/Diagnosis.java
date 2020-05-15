package by.epam.ts.bean;

import java.io.Serializable;
import java.util.Comparator;

public class Diagnosis implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codeByICD;
	private String diseaseName;
	private int averageBedDays;

	public Diagnosis() {
	}

	public Diagnosis(String codeByICD, String diseaseName) {
		this.codeByICD = codeByICD;
		this.diseaseName = diseaseName;
	}

	public Diagnosis(String codeByICD, String diseaseName, int avarageBedDays) {
		this(codeByICD, diseaseName);
		this.averageBedDays = avarageBedDays;
	}

	public String getCodeByICD() {
		return codeByICD;
	}

	public void setCodeByICD(String codeByICD) {
		this.codeByICD = codeByICD;
	}


	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public int getAverageBedDays() {
		return averageBedDays;
	}

	public void setAverageBedDays(int averageBedDays) {
		this.averageBedDays = averageBedDays;
	}
	
	public static Comparator<Diagnosis> diseaseNameComparator = new Comparator<Diagnosis>() {
		@Override
		public int compare(Diagnosis diagnosis1, Diagnosis diagnosis2) {
			String diseaseName1 = diagnosis1.getDiseaseName().toUpperCase();
			String diseaseName2 = diagnosis2.getDiseaseName().toUpperCase();
			//ascending order;
			return diseaseName1.compareTo(diseaseName2);
		}
	};


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + averageBedDays;
		result = prime * result + ((codeByICD == null) ? 0 : codeByICD.hashCode());
		result = prime * result + ((diseaseName == null) ? 0 : diseaseName.hashCode());
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
		Diagnosis other = (Diagnosis) obj;
		if (averageBedDays != other.averageBedDays) {
			return false;
		}
		if (codeByICD == null) {
			if (other.codeByICD != null) {
				return false;
			}
		} else if (!codeByICD.equals(other.codeByICD))
			return false;
		if (diseaseName == null) {
			if (other.diseaseName != null) {
				return false;
			}
		} else if (!diseaseName.equals(other.diseaseName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Diagnosis [codeByICD=" + codeByICD + ", diseaseName=" + diseaseName + ", avarageBedDays="
				+ averageBedDays + "]";
	}

}
