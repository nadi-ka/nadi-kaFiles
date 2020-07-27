package by.epam.ts.bean;

import by.epam.ts.bean.specialty.Specialty;

/**
 * 
 * Class are ready for serialization and implements Serializable interface as
 * inheritor of User
 *
 */

public class MedicalStaff extends User {
	private static final long serialVersionUID = 1L;

	private String id;
	private Specialty specialty;
	private String surname;
	private String name;
	private String email;

	public MedicalStaff() {
		super();
	}

	public MedicalStaff(String id, Specialty specialty, String surname, String name, String email) {
		super();
		this.id = id;
		this.specialty = specialty;
		this.surname = surname;
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((specialty == null) ? 0 : specialty.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		MedicalStaff other = (MedicalStaff) obj;
		return super.equals(other) && specialty == other.specialty
				&& (id == other.id || (id != null && id.equals(other.getId())))
				&& (surname == other.surname || (surname != null && surname.equals(other.getSurname())))
				&& (name == other.name || (name != null && name.equals(other.getName())))
				&& (email == other.email || (email != null && email.equals(other.getEmail())));
	}

	@Override
	public String toString() {
		return getClass().getName() + "@[id = " + id + ", specialty = " + specialty.getSpecialtyValue() + ", surname = "
				+ surname + ", name = " + name + ", email = " + email + "]";
	}

}
