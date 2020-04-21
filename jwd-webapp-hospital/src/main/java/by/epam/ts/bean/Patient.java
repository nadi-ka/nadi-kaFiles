package by.epam.ts.bean;

import java.io.Serializable;
import java.sql.Date;


public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String surname;
	private String name;
	private Date dateOfBirth;
	private String email;

	public Patient() {
	}

	public Patient(String id, String surname, String name, Date dateOfBirth, String email) {
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null ? 0 : dateOfBirth.hashCode()));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
				&& (email == other.email || (email != null && email.equals(other.getEmail())));
	}

	@Override
	public String toString() {
		return getClass().getName() + "@[id = " + id + ", surname = " + surname + ", name = " + name
				+ ", dateOfBirth = " + dateOfBirth.toString() + ", email = " + email + "]";
	}

}
