package com.example.healthfood.DAO.entities.userdata;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.example.healthfood.DAO.entities.core.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "favorites")
public class Favorite {

    @EmbeddedId
    private FavoriteId id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("foodId")
    private Food food;

    private LocalDateTime addedAt = LocalDateTime.now();
}