package com.dababy.social.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="user_role")
public class UserRole {

    @Id
    private String name;
    @Column(nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_permission_map",
            joinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "permission_name", referencedColumnName = "name"))
    private Set<UserPermission> permissions= new HashSet<>();

    public UserRole() {
    }

    public UserRole(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(UserPermission permission){
        permissions.add(permission);
    }


    @Override
    public String toString() {
        return "UserRole {" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;
        UserRole userRole = (UserRole) o;
        return getName().equals(userRole.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
