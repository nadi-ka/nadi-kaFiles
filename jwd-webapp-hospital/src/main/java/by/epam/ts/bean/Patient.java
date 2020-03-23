package by.epam.ts.bean;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String surname;
	private String name;
	private int age;
	private Date entryDate;
	private Date dischargeDate;
	private String email;

	private Treatment treatment;

	public Patient() {
	}

	public Patient(String surname, String name, Treatment treatment) {
		this.surname = surname;
		this.name = name;
		this.treatment = treatment;
	}

	public Patient(String id, String surname, String name, int age, Date entryDate, Date dischargeDate, String email,
			Treatment treatment) {
		this(surname, name, treatment);
		this.id = id;
		this.age = age;
		this.entryDate = entryDate;
		this.dischargeDate = dischargeDate;
		this.email = email;
	}

	public Patient(String id, String surname, String name, int age, Date entryDate, Date dischargeDate, String email) {
		super();
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.age = age;
		this.entryDate = entryDate;
		this.dischargeDate = dischargeDate;
		this.email = email;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((dischargeDate == null) ? 0 : dischargeDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((entryDate == null) ? 0 : entryDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((treatment == null) ? 0 : treatment.hashCode());
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

		return age == other.age && (id == other.id || (id != null && id.equals(other.getId())))
				&& (surname == other.surname || (surname != null && surname.equals(other.getSurname())))
				&& (name == other.name || (name != null && name.equals(other.getName())))
				&& (entryDate == other.getEntryDate() || (entryDate != null && entryDate.equals(other.getEntryDate())))
				&& (dischargeDate == other.dischargeDate
						|| (dischargeDate != null && (dischargeDate.equals(other.dischargeDate))))
				&& (email == other.email || (email != null && email.equals(other.getEmail())))
				&& (treatment == other.treatment || (treatment != null && treatment.equals(other.getTreatment())));
	}

	@Override
	public String toString() {
		return getClass().getName() + "@[id = " + id + ", surname = " + surname + ", name = " + name + ", age = " + age
				+ ", entryDate = " + entryDate + ", dischargeDate = " + dischargeDate + ", email = " + email
				+ ", treatment = " + treatment.toString() + "]";
	}

}
