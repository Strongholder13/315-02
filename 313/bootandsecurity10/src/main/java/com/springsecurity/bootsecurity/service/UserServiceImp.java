package com.springsecurity.bootsecurity.service;



import com.springsecurity.bootsecurity.model.Role;
import com.springsecurity.bootsecurity.model.User;
import com.springsecurity.bootsecurity.repository.RolesRepository;
import com.springsecurity.bootsecurity.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImp(UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void add(User user, String role) {
        List<Role> roles = new ArrayList();
        if (role == null) {
            role = "ROLE_NOROLE";
        }
        Role newRole = new Role(user.getUsername(), role);
        roles.add(newRole);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
    @Transactional
    @Override
    public void update(User user, String roleName) {
        List<Role> roles = user.getRoles();
        roles.add(new Role(user.getUsername(), roleName));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);

    }
    @Transactional
    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
    @Override
    public List<User> listUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<String> listRoles() {
        List<Role> roleList = rolesRepository.findAll();
        List<String> roleNames = new ArrayList<>();
        for (Role role : roleList){
           roleNames.add(role.getRole());
        }
        return roleNames;
    }
    @Transactional
    @Override
    public void delete(int id) {
        rolesRepository.deleteById(usersRepository.getById(id).getUsername());
        usersRepository.deleteById(id);
    }
    @Override
    public User findByUsername(String username) {
        Optional<User> user = usersRepository.findByUsername(username);
        return user.orElse(null);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public Optional<User> getUser(int id) {
        return usersRepository.findById(id);
    }
}
