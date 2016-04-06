package ru.loftschool.moneytrackerbyoxion.rest.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Александр on 06.10.2015.
 */
public class GoogleTokenStatusModel {

    @Expose
    private String status;

    @Expose
    private String id;

    @Expose
    private String email;

    @Expose
    private Boolean verifiedEmail;

    @Expose
    private String name;

    @Expose
    private String givenName;

    @Expose
    private String familyName;

    @Expose
    private String link;

    @Expose
    private String picture;

    @Expose
    private String gender;

    @Expose
    private String locale;

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}
