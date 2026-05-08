package com.example.healthfood.DAO.entities.core;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.healthfood.DAO.entities.reference.Allergy;
import com.example.healthfood.DAO.entities.reference.DietaryPreference;
import com.example.healthfood.DAO.entities.reference.Disease;
import com.example.healthfood.DAO.entities.reference.Intolerance;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private HealthProfile healthProfile;

    // USER ↔ DISEASES
    @ManyToMany
    @JoinTable(
        name = "user_diseases",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    private List<Disease> diseases;


    // USER ↔ ALLERGIES
    @ManyToMany
    @JoinTable(
        name = "user_allergies",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "allergy_id")
    )
    private List<Allergy> allergies;


    // USER ↔ INTOLERANCES
    @ManyToMany
    @JoinTable(
        name = "user_intolerances",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "intolerance_id")
    )
    private List<Intolerance> intolerances;


    // USER ↔ PREFERENCES
    @ManyToMany
    @JoinTable(
        name = "user_preferences",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "preference_id")
    )
    private List<DietaryPreference> preferences;
}