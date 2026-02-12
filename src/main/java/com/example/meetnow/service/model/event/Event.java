package com.example.meetnow.service.model.event;

import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "geo_point_id")
    private GeoPoint coordinates;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_interest",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<Interest> interests;

}
