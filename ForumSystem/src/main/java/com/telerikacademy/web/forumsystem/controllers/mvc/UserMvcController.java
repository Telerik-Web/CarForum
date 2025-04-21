package com.telerikacademy.web.forumsystem.controllers.mvc;

import com.telerikacademy.web.forumsystem.exceptions.AuthenticationFailureException;
import com.telerikacademy.web.forumsystem.helpers.AuthenticationHelper;
import com.telerikacademy.web.forumsystem.models.FilterUserDTO;
import com.telerikacademy.web.forumsystem.models.FilterUserOptions;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.services.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.telerikacademy.web.forumsystem.models.FilterUserDTO.checkIfUserFilterIsEmpty;

@Controller
@RequestMapping("/users")
public class UserMvcController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    public UserMvcController(UserService userService, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @ModelAttribute("currentUser")
    public Optional<User> populateCurrentUser(HttpSession session) {
        if (populateIsAuthenticated(session)) {
            String currentUsername = (String) session.getAttribute("currentUser");
            Optional.ofNullable(userService.getByUsername(currentUsername));
        }
        return Optional.empty();
    }

    @GetMapping("/admin")
    public String showFilteredPosts(@ModelAttribute("filterUserOptions") FilterUserDTO filterDto, Model model, HttpSession session) {
        try {
            FilterUserOptions filterUserOptions = new FilterUserOptions(
                    filterDto.getFirstName(),
                    filterDto.getLastName(),
                    filterDto.getUsername(),
                    filterDto.getEmail(),
                    filterDto.getSortBy(),
                    filterDto.getSortOrder()
            );
            List<User> users = userService.getAll(filterUserOptions);
            User user = authenticationHelper.tryGetUser(session);
            model.addAttribute("users", users);
            model.addAttribute("isFilterEmpty", checkIfUserFilterIsEmpty(filterDto));
            model.addAttribute("filterUserOptions", filterDto);

            if (populateIsAuthenticated(session)) {
                String currentUsername = (String) session.getAttribute("currentUser");
                model.addAttribute("currentUser", userService.getByUsername(currentUsername));
            }

            if(user.isAdmin()){
                return "AdminPortal";
            }

            return "AccessDenied";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

    }

    @PostMapping("/{id}/block")
    public String blockUser(@PathVariable int id, HttpSession session) {
        try {
            User adminUser = authenticationHelper.tryGetUser(session);
            if (adminUser.isAdmin()) {
                User targetUser = userService.getById(adminUser, id);
                userService.alterBlock(id, adminUser, !targetUser.isBlocked());
                return "redirect:/users/admin";
            }
            return "AccessDenied";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }

    @PostMapping("/{id}/promote")
    public String promoteUser(@PathVariable int id, HttpSession session) {
        try{
            User adminUser = authenticationHelper.tryGetUser(session);
            if (adminUser.isAdmin()) {
                User targetUser = userService.getById(adminUser, id);
                userService.alterAdminPermissions(id, adminUser, !targetUser.isAdmin());
                return "redirect:/users/admin";
            }
            return "AccessDenied";
        } catch (AuthenticationFailureException e){
            return "redirect:/auth/login";
        }
    }
}
