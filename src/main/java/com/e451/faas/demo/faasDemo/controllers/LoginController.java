package com.e451.faas.demo.faasDemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  //TODO STRETCH: Actually handle validation to ensure the user can login


  //Basically functions as a home page
  @GetMapping("/login")
  public ModelAndView login(ModelAndView modelAndView) {
    modelAndView.setViewName("login");
    return modelAndView;
  }
}
