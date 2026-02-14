package com.example.meetnow.service.model.event;

import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "event")
@AllArgsConstructor
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "geo_point_id")
    private GeoPoint coordinates;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_interest",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<Interest> interests;
}
