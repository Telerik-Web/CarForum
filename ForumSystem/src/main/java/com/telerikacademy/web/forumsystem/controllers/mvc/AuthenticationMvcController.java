package com.telerikacademy.web.forumsystem.controllers.mvc;

import com.telerikacademy.web.forumsystem.exceptions.AuthenticationFailureException;
import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.helpers.AuthenticationHelper;
import com.telerikacademy.web.forumsystem.mappers.UserMapper;
import com.telerikacademy.web.forumsystem.models.LoginDto;
import com.telerikacademy.web.forumsystem.models.RegisterDto;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationMvcController(AuthenticationHelper authenticationHelper,
                                       UserService userService,
                                       UserMapper userMapper) {
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("login", new LoginDto());
        return "Login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("login") LoginDto loginDto,
                               BindingResult errors,
                               HttpSession session) {
        if (errors.hasErrors()) {
            return "Login";
        }

        try {
            User user = authenticationHelper.verifyAuthentication(loginDto.getUsername(), loginDto.getPassword());
            session.setAttribute("currentUser", user.getUsername());
            session.setAttribute("isAdmin", user.isAdmin());
            return "redirect:/home";
        } catch (AuthenticationFailureException e) {
            errors.reject("Invalid authentication");
            return "Login";
        } catch (EntityNotFoundException e) {
            errors.reject("invalid.credentials", "Invalid username or password");
            return "Login";
        }
    }

    @GetMapping("/logout")
    public String showLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "Register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("register") RegisterDto registerDto,
                                  BindingResult errors) {
        if(errors.hasErrors()) {
            return "Register";
        }

        if(!registerDto.getPassword().equals(registerDto.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "passwords.mismatch");
            return "Register";
        }

        try {
            User user = userMapper.fromDto(registerDto);
            userService.create(user);
            return "redirect:/auth/login";
        } catch (DuplicateEntityException e) {
            errors.rejectValue("username", "duplicate.username",
                    "Username is already taken!");
            return "Register";
        }
    }
}
