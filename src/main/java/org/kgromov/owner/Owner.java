package org.kgromov.owner;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.kgromov.model.Person;
import org.kgromov.system.Utils;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "owners")
public class Owner extends Person {

	@Column(name = "address")
	@NotBlank
	public String address;

	@Column(name = "city")
	@NotBlank
	public String city;

	@Column(name = "telephone")
	@NotBlank
	@Pattern(regexp = "\\d{10}", message = "Telephone must be a 10-digit number")
	public String telephone;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	@OrderBy("name")
	private List<Pet> pets = new ArrayList<>();

	public List<Pet> getPets() {
		return this.pets;
	}

	public void addPet(Pet pet) {
		if (pet.isNew()) {
			getPets().add(pet);
		}
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * @param name to test
	 * @return a pet if pet name is already in use
	 */
	public Pet getPet(String name) {
		return getPet(name, false);
	}

	/**
	 * Return the Pet with the given id, or null if none found for this Owner.
	 * @param id to test
	 * @return a pet if pet id is already in use
	 */
	public Pet getPet(Long id) {
		for (Pet pet : getPets()) {
			if (!pet.isNew()) {
				var compId = pet.id;
				if (compId.equals(id)) {
					return pet;
				}
			}
		}
		return null;
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * @param name to test
	 * @return a pet if pet name is already in use
	 */
	public Pet getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (Pet pet : getPets()) {
			String compName = pet.name;
			if (compName != null && compName.equalsIgnoreCase(name)) {
				if (!ignoreNew || !pet.isNew()) {
					return pet;
				}
			}
		}
		return null;
	}

	/**
	 * Adds the given {@link Visit} to the {@link Pet} with the given identifier.
	 * @param petId the identifier of the {@link Pet}, must not be {@literal null}.
	 * @param visit the visit to add, must not be {@literal null}.
	 */
	public void addVisit(Long petId, Visit visit) {

		Utils.notNull(petId, "Pet identifier must not be null!");
		Utils.notNull(visit, "Visit must not be null!");

		Pet pet = this.getPet(petId);

		Utils.notNull(pet, "Invalid Pet identifier!");

		pet.addVisit(visit);
	}

	@Override
	public String toString() {
		return "Owner{" +
				"new='" + this.isNew() + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", telephone='" + telephone + '\'' +
				'}';
	}

}
