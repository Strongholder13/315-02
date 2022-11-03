package com.springsecurity.bootsecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name= "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "username")
    private String username;

    @Column
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    @JsonIgnore
    private User user;


    public Role() {
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public List<String> getRoleNames() {
        List<String> roleNames = new ArrayList();
        for (Role role : user.getRoles()){
            roleNames.add(role.getRole());
        }
        return roleNames;
    }

    public Role(String username, String role) {

        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role1)) return false;
        return username.equals(role1.username) && role.equals(role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role);
    }

    @Override
    public String toString() {
        return this.role.substring(5);
    }
    @Override
    public String getAuthority() {
        return this.role;
    }
}
