package com.example.meetnow.service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
@Table(name = "interest")
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Interest {

    @Id
    @Column(name = "id")
    private final Long id;

    @Column(name = "name")
    @EqualsAndHashCode.Include
    private final String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private final Category category;
}
