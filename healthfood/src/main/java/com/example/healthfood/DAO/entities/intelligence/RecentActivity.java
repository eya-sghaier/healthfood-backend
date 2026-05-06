package com.example.healthfood.DAO.entities.intelligence;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.example.healthfood.DAO.entities.core.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "recent_activities")
public class RecentActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String activityType;

    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();
}