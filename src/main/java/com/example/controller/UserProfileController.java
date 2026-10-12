package com.example.controller;

import com.example.entity.UserProfile;
import com.example.service.PhotoStorageService;
import com.example.service.UserProfileService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;

import java.time.LocalDate;

public class UserProfileController {

    private final UserProfileService userProfileService;
    private final PhotoStorageService photoStorageService;

    public UserProfileController(
            UserProfileService userProfileService,
            PhotoStorageService photoStorageService
    ) {
        this.userProfileService = userProfileService;
        this.photoStorageService = photoStorageService;
    }

    public void create(Context ctx) {
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String passportNumber = ctx.formParam("passportNumber");
        String joiningDateString = ctx.formParam("joiningDate");
        UploadedFile photo = ctx.uploadedFile("photo");

        if (firstName == null ||
                lastName == null ||
                passportNumber == null ||
                joiningDateString == null ||
                photo == null) {

            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json("All fields including photo are required");
            return;
        }
        LocalDate joiningDate;

        try {
            joiningDate = LocalDate.parse(joiningDateString);
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json("joiningDate must have format YYYY-MM-DD");
            return;
        }
        String photoObjectName = photoStorageService.upload(photo);

        UserProfile userProfile = new UserProfile(
                null,
                firstName,
                lastName,
                passportNumber,
                joiningDate,
                photoObjectName
        );
        Long id;

        try {
            id = userProfileService.create(userProfile);
        } catch (Exception e) {
            photoStorageService.delete(photoObjectName);
            throw e;
        }

        UserProfile created = userProfileService.getById(id);
        ctx.status(HttpStatus.CREATED);
        ctx.json(created);
    }

    public void getAll(Context ctx) {
        ctx.json(userProfileService.getAll());
    }

    public void getById(Context ctx) {
        Long id = Long.parseLong(
                ctx.pathParam("id")
        );
        UserProfile user = userProfileService.getById(id);

        if (user == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("User profile not found");
            return;
        }
        ctx.json(user);
    }

    public void delete(Context ctx) {
        Long id = Long.parseLong(
                ctx.pathParam("id")
        );
        UserProfile user = userProfileService.getById(id);

        if (user == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("User profile not found");
            return;
        }

        userProfileService.delete(id);
        photoStorageService.delete(user.getPhoto()
        );

        ctx.status(HttpStatus.NO_CONTENT);
    }
}