package com.example.shoppingMall.entity;
import com.example.shoppingMall.enums.AccountStatus;
import com.example.shoppingMall.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    UUID userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    String email;

    @Column(name = "password", nullable = false, length = 255)
    String password;

    @Column(name = "full_name", nullable = false, length = 100)
    String fullName;

    @Column(name = "phone", unique = true, length = 15)
    String phone;

    @Column(name = "avatar_url", length = 500)
    String avatarUrl;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;
    @ManyToMany
    Set<Role> roles;

    @OneToOne(mappedBy = "user")
    Cart cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Orders> orders;

}
