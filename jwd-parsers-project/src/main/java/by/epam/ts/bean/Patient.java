package by.epam.ts.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String surname;
	private String name;
	private LocalDate dateOfBirth;
	private List<Treatment> prescriptions;

	public Patient() {
	}

	public Patient(String id, String surname, String name, LocalDate dateOfBirth, List<Treatment> prescriptions) {
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.prescriptions = prescriptions;
	}
	
	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement 
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@XmlElement 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement 
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@XmlElement 
	public List<Treatment> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<Treatment> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((dateOfBirth == null ? 0 : dateOfBirth.hashCode()));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((prescriptions == null) ? 0 : prescriptions.hashCode());
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
		Collections.sort(prescriptions);
		Collections.sort(other.getPrescriptions());

		return (id == other.id || (id != null && id.equals(other.getId()))) 
				&& (surname == other.surname || (surname != null && surname.equals(other.getSurname())))
				&& (name == other.name || (name != null && name.equals(other.getName())))
				&& (dateOfBirth == other.dateOfBirth
						|| (dateOfBirth != null && dateOfBirth.equals(other.getDateOfBirth())))
				&& (prescriptions == other.prescriptions || (prescriptions != null && prescriptions.equals(other.getPrescriptions())));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String str = getClass().getName() + "@[id = " + id + ", surname = " + surname + ", name = " + name
				+ ", dateOfBirth = " + dateOfBirth.toString() + ", prescriptions = ";
		builder.append(str);
		for(Treatment treatment: prescriptions) {
			builder.append(treatment.toString() + "; ");
		}
		builder.append("]");
		return builder.toString();
		
	}

}
