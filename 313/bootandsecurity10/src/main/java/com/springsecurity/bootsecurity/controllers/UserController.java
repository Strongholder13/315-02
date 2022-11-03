package com.springsecurity.bootsecurity.controllers;

import com.springsecurity.bootsecurity.model.Role;
import com.springsecurity.bootsecurity.model.User;
import com.springsecurity.bootsecurity.service.UserService;
import com.springsecurity.bootsecurity.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public List<User> showAllUsers(){
        List<User> allUsersList = userService.listUsers();
        return allUsersList;
    }
    @GetMapping("users/{id}")
    public Optional<User> getUser(@PathVariable int id){
        Optional<User> user = userService.getUser(id);
        return user;
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user, String role){
        userService.add(user, role);
        return user;
    }

//    @GetMapping("/admin")
//    public String index(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        model.addAttribute("admin", userService.findByUsername(authentication.getName()));
//        model.addAttribute("users", userService.listUsers());
//        model.addAttribute("newUser", new User());
//        model.addAttribute("newRole", new Role());
//        model.addAttribute("currentUser", (User)userService.loadUserByUsername(authentication.getName()));
//        model.addAttribute("allRoles", userService.listRoles());
//        return "/admin";
//    }
//
//    @GetMapping("/user")
//    public String indexUser(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        model.addAttribute("user", userService.loadUserByUsername(authentication.getName()));
//        return "/user";
//    }
//    @PostMapping("/registration")
//    public String saveUser(@ModelAttribute("newUser") @Valid User user, @ModelAttribute("newRole") Role role, BindingResult bindingResult) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "/admin";
//        }
//        userService.add(user, role.getRole());
//        return "redirect:/admin";
//    }
//    @PostMapping("/update/")
//    public String updateUser(@ModelAttribute("user") User user,
//                             @ModelAttribute("addRoles") Role role) {
//        userService.update(user, role.getRole());
//        return "redirect:/admin";
//    }
//
//    @GetMapping ("/delete/{id}")
//    public String delete(@PathVariable int id) {
//        userService.delete(id);
//        return "redirect:/admin";
//    }
}
