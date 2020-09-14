package com.dababy.social.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "user_permission")
public class UserPermission implements GrantedAuthority {

    @Id
    private String name;

    private String description;

    public UserPermission() {
    }

    public UserPermission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserPermission {" +
                "name='" + name + '\'' +
                '}';
    }

}
