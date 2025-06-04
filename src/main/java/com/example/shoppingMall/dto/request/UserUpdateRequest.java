package com.example.shoppingMall.dto.request;

import com.example.shoppingMall.enums.AccountStatus;
import com.example.shoppingMall.enums.Gender;

import java.time.LocalDate;
import java.util.Set;

public class UserUpdateRequest {
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private LocalDate dateOfBirth;
    private AccountStatus accountStatus;
    private Gender gender;
    private Set<String> roles;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String username, String email, String fullName, String phone, String avatarUrl, LocalDate dateOfBirth, AccountStatus accountStatus, Gender gender, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.dateOfBirth = dateOfBirth;
        this.accountStatus = accountStatus;
        this.gender = gender;
        this.roles = roles;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
