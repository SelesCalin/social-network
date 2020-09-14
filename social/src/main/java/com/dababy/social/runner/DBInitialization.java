package com.dababy.social.runner;

import com.dababy.social.domain.ApplicationUser;
import com.dababy.social.domain.UserPermission;
import com.dababy.social.domain.UserRole;
import com.dababy.social.repository.PermissionRepository;
import com.dababy.social.repository.RoleRepository;
import com.dababy.social.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DBInitialization implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DBInitialization(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository=permissionRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        UserPermission permission1= new UserPermission("GOD_AUTHORITY", "admin permission, can do everything");
        UserPermission permission2= new UserPermission("CRUD_USERS_AUTHORITY", "can do operations on users");

        UserPermission permission3= new UserPermission("NORMAL_USER_AUTHORITY", "normal user");

        UserRole role = new UserRole("Admin","Admin");
        role.addPermission(permission1);
        UserRole role2 = new UserRole("User", "User");
        role.addPermission(permission3);
        ApplicationUser user1 = new ApplicationUser("admin@gmail.com",passwordEncoder.encode("pass"), "admin");
        ApplicationUser user2 = new ApplicationUser("user@gmail.com",passwordEncoder.encode("pass"), "user2");



        user1.addRole(role);
        user2.addRole(role2);

        permissionRepository.save(permission1);
        permissionRepository.save(permission2);
        permissionRepository.save(permission3);
        roleRepository.save(role);
        roleRepository.save(role2);

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
