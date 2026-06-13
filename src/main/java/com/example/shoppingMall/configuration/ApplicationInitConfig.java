package com.example.shoppingMall.configuration;

import com.example.shoppingMall.constant.PredefinedRole;
import com.example.shoppingMall.entity.Role;
import com.example.shoppingMall.entity.User;
import com.example.shoppingMall.repository.RoleRepository;
import com.example.shoppingMall.repository.UserRepository;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
@Slf4j
@Configuration
public class ApplicationInitConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";
    @Bean
    public ApplicationRunner applicationRunner(UserRepository userDao, RoleRepository roleDao) {
        return args ->{
            if(!userDao.existsByUsername(ADMIN_USER_NAME)){

                Role role = new Role();
                role.setName(PredefinedRole.USER_ROLE);
                role.setDescription("User role");
                roleDao.save(role);

                Role roleAdmin = new Role();
                roleAdmin.setName(PredefinedRole.ADMIN_ROLE);
                roleAdmin.setDescription("Admin role");
                roleDao.save(roleAdmin);

                var roles = new HashSet<Role>();
                roles.add(roleAdmin);

                User user = new User();
                user.setUsername(ADMIN_USER_NAME);
                user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
                user.setEmail("admin@localhost.com");
                user.setFullName("System Admin");
                user.setAccountStatus(com.example.shoppingMall.enums.AccountStatus.ACTIVE);
                user.setRoles(roles);
                userDao.save(user);


            }

        };
    }



}
