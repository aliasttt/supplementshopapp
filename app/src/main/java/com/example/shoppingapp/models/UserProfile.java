package com.example.shoppingapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profiles")
public class UserProfile {
    @PrimaryKey
    @NonNull
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String postalCode;

    public UserProfile(@NonNull String email, String name, String phoneNumber, String address, String city, String postalCode) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    // Getters and Setters
    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
} 