package com.example.healthfood.DAO.entities.reference;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.example.healthfood.DAO.entities.core.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "allergies_list")
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "allergies")
    private List<User> users;
}