package com.telerikacademy.web.forumsystem.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotNull(message = "Firstname can't be empty.")
    @Size(min = 4, max = 32, message = "Firstname should be between 4 and 32 symbols.")
    private String firstName;

    @NotNull(message = "Lastname can't be empty.")
    @Size(min = 4, max = 32, message = "Lastname should be between 4 and 32 symbols.")
    private String lastName;

    @Email
    //move the unique check to the service - DONE
    @NotNull
    private String email;

    //make userDtoOut - DONE

    //na lacho premissionhelper go vkarai w user - DONE

    //do the admin and the block update in the user - DONE

    //phone number repository and service - DONE

    //filterOptions for the user - DONE

    //username na user da se adwa nqkyde - DONE

    //phone number for admin/ and delete the username and password - DONE

    //update i crate of user allow for the user to be created as an admin. fix it - DONE

    //comment test - DONE

    //findBy neshtata - DONE

    //isAdmin & blocked remove from the DTO class - DONE

    //name of everything must be same - DONE

    //swagger postController

    //test for postService new method - getMost recent post & get most commented post & the last method

    //return phone number if the user is an admin

    //create to check if the user has a phone

    //update & delete for phoneNumber

    //act arrange & assert for the tests

    @NotNull
    private String password;
//
//    @NotNull
//    private Boolean isAdmin;
//
//    @NotNull
//    private Boolean isBlocked;

    @NotNull
    private String username;

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

//    public UserDTO(String firstName, String lastName, String email, String password) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.isAdmin = isAdmin;
//        this.isBlocked = isBlocked;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Boolean isAdmin() {
//        return isAdmin;
//    }
//
//    public void setAdmin(Boolean admin) {
//        isAdmin = admin;
//    }
//
//    public Boolean isBlocked() {
//        return isBlocked;
//    }
//
//    public void setBlocked(Boolean blocked) {
//        isBlocked = blocked;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
