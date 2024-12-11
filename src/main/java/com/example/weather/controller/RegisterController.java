package com.example.weather.controller;

import com.example.weather.dto.RegistrationDto;

import com.example.weather.exception.CredentialsException;
import com.example.weather.service.AuthenticationService;
import com.example.weather.validator.CredentialsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("registrationDto", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute RegistrationDto dto,
                                  BindingResult bindingResult)
    {

        try {
            CredentialsValidator.validateData(dto);
            authenticationService.register(dto);
        } catch (CredentialsException e){
            bindingResult.rejectValue("password", "error.registrationDto", e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        return "redirect:/login";
    }

}
