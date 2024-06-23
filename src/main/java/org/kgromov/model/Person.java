package org.kgromov.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
public class Person extends PanacheEntity {

	@Column(name = "first_name")
	@NotBlank
	public String firstName;

	@Column(name = "last_name")
	@NotBlank
	public String lastName;

	public boolean isNew() {
		return this.id == null;
	}
}
