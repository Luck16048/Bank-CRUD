package com.example.service;

import com.example.entity.UserProfile;
import com.example.repository.UserProfileRepository;

import java.util.List;

public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public Long create(UserProfile userProfile) {
        if (userProfile.getFirstName() == null ||
                userProfile.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (userProfile.getLastName() == null ||
                userProfile.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (userProfile.getPassportNumber() == null ||
                userProfile.getPassportNumber().isBlank()) {
            throw new IllegalArgumentException("Passport number is required");
        }

        if (userProfile.getJoiningDate() == null) {
            throw new IllegalArgumentException("Joining date is required");
        }

        return repository.create(userProfile);
    }

    public List<UserProfile> getAll() {
        return repository.findAll();
    }

    public UserProfile getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        UserProfile user = repository.findById(id);

        if (user == null) {
            throw new IllegalArgumentException("User profile not found");
        }

        repository.delete(id);
    }
}