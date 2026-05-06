package com.example.healthfood.DAO.entities.reference;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.example.healthfood.DAO.entities.core.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "intolerances")
public class Intolerance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "intolerances")
    private List<User> users;
}