package com.springsecurity.bootsecurity.model;

import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Username")
    @NonNull
    private String username;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String surname;

    @Column(name = "Age")
    private int age;

    @Column(name = "Password")
    @NonNull
    private String password;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Role> roles;

    private String role1;
    private String role2;

    public String getRoleName(int index) {
        String roleName = roles.get(index).getRole();
        return roleName;
    }


    public List<String> getRoleNames() {
        List<String> roleNames = new ArrayList<>();
        for (Role role : roles) {
            roleNames.add(role.getRole());
        }
         return roleNames;
    }

    public User(int Id, @NonNull String username, String name, String surname, int age, @NonNull String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.password = password;
//        this.role1 = getRoleNames(0);
//        this.role2 = getRoleNames(1);
//


    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
//    public void setRole(String role) {
//
//        List<Role> roles = new ArrayList();
//    Role newRole = new Role(user.getUsername(), role) ;
//    roles.add(newRole);
//    user.setRoles(roles);
//        this.roles = roles;
//    }

    public User() {

    }

    @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                ", id='" + id +
                ", username='" + username +
                ", name='" + name +
                ", surname='" + surname +
                ", age=" + age +
                ", password='" + password +
                ", role='" + getRoleNames() +
                '}';
    }
}
