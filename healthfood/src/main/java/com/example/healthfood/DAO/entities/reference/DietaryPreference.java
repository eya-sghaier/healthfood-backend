package com.example.healthfood.DAO.entities.reference;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.example.healthfood.DAO.entities.core.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "dietary_preferences")
public class DietaryPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "preferences")
    private List<User> users;
}