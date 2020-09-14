package com.dababy.social.domain;

import com.dababy.social.domain.base.BaseUUIDModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="application_user")
public class ApplicationUser extends BaseUUIDModel {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column (name = "password", nullable = false)
    private String password;

    @Column (name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles_map",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"))
    private Set<UserRole> roles= new HashSet<>();

    public ApplicationUser() {
    }

    public ApplicationUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserRole role){
        this.roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationUser)) return false;
        ApplicationUser user = (ApplicationUser) o;
        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
