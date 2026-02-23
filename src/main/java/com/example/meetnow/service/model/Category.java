package com.example.meetnow.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Category {
    @Id
    private final Long id;

    private final String name;
}
