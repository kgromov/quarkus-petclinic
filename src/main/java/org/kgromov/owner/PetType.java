package org.kgromov.owner;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.kgromov.model.NamedEntity;

@Entity
@Table(name = "types")
public class PetType extends NamedEntity {

}
