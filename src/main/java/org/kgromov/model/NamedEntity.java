package org.kgromov.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class NamedEntity extends PanacheEntity {

	@Column(name = "name")
	public String name;

	@Override
	public String toString() {
		return this.name;
	}

	public boolean isNew() {
		return this.id == null;
	}

}
