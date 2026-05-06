package com.example.healthfood.DAO.entities.userdata;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class FavoriteId implements Serializable {

    private Long userId;
    private Long foodId;
}