package com.example.meetnow.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_profile")
@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    String name;

    @Column(name = "birthday_date")
    LocalDate birthdayDate;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private FileResource photo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_interest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    @ToString.Exclude
    private Set<Interest> interests;

}
