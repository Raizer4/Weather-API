package com.example.weather.controller;

import com.example.weather.dto.LoginDto;
import com.example.weather.exception.CredentialsException;
import com.example.weather.exception.UserNotFoundExcepiton;
import com.example.weather.service.AuthenticationService;
import com.example.weather.validator.CredentialsValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginDto loginDto,
                               BindingResult bindingResult,
                               HttpServletResponse resp){

        try {
            CredentialsValidator.validateData(loginDto);
            String sessionCookie = authenticationService.logIn(loginDto);
            resp.addCookie(new Cookie("JSESSIONID", sessionCookie));
        }catch (CredentialsException  | UserNotFoundExcepiton e){
            bindingResult.rejectValue("password", "error.loginDto", e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        return "home";
    }

}
