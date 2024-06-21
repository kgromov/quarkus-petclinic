package org.kgromov.vet;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.kgromov.model.NamedEntity;

@Entity
@Table(name = "specialties")
public class Specialty extends NamedEntity {

}
