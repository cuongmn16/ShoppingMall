package com.example.shoppingMall.model;
import com.example.shoppingMall.enums.AccountStatus;
import com.example.shoppingMall.enums.Gender;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private long userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private LocalDate dateOfBirth;
    private AccountStatus accountStatus;
    private Gender gender;
    private Set<Role> roles = new HashSet<>();
    private Set<Seller> sellers = new HashSet<>();
    private List<Addresses> addresses;


    public User() {
    }

    public User(long userId, String username, String email, String password, String fullName, String phone, String avatarUrl, LocalDate dateOfBirth, AccountStatus accountStatus, Gender gender, Set<Role> roles, Set<Seller> sellers, List<Addresses> addresses) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.dateOfBirth = dateOfBirth;
        this.accountStatus = accountStatus;
        this.gender = gender;
        this.roles = roles;
        this.sellers = sellers;
        this.addresses = addresses;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(Set<Seller> sellers) {
        this.sellers = sellers;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<Addresses> addresses) {
        this.addresses = addresses;
    }
}
