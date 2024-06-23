package org.kgromov.vet;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import org.kgromov.model.Person;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "vets")
@Cacheable
public class Vet extends Person {

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<Specialty> specialties;

	protected Set<Specialty> getSpecialtiesInternal() {
		if (this.specialties == null) {
			this.specialties = new HashSet<>();
		}
		return this.specialties;
	}

	protected void setSpecialtiesInternal(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

	@XmlElement
	public List<Specialty> getSpecialties() {
		return this.getSpecialtiesInternal()
				.stream()
				.sorted(Comparator.comparing(specialty -> specialty.name, String::compareToIgnoreCase))
				.collect(Collectors.toUnmodifiableList());
	}

	public int getNrOfSpecialties() {
		return getSpecialtiesInternal().size();
	}

	public void addSpecialty(Specialty specialty) {
		getSpecialtiesInternal().add(specialty);
	}

}
