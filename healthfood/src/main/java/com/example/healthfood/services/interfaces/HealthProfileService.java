package com.example.healthfood.services.interfaces;

import com.example.healthfood.DAO.entities.core.HealthProfile;

public interface HealthProfileService {

    HealthProfile createOrUpdateProfile(HealthProfile profile, String userEmail);

    HealthProfile getProfile(String userEmail);
}