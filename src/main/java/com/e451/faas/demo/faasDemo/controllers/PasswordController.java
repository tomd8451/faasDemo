package com.e451.faas.demo.faasDemo.controllers;

import com.e451.faas.demo.faasDemo.repositories.models.User;
import com.e451.faas.demo.faasDemo.services.EmailService;
import com.e451.faas.demo.faasDemo.services.UserService;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class PasswordController {

  private EmailService emailService;
  private UserService userService;

  @Autowired
  public PasswordController(
      EmailService emailService, UserService userService) {
    this.emailService = emailService;
    this.userService = userService;
  }


  @GetMapping(value = "/forgot")
  public ModelAndView displayForgotPasswordPage() {
    return new ModelAndView("forgotPassword");
  }

  @PostMapping(value = "/forgot")
  public ModelAndView processForgotPasswordPage(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {

    Optional<User> optional = userService.findUserByEmail(userEmail);

    if (!optional.isPresent()) {
      modelAndView.addObject("errorMessage", "No account for that e-mail address.");
    } else {

      User user = optional.get();
      user.setResetToken(UUID.randomUUID().toString());

      userService.save(user);

      String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";

      SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
      passwordResetEmail.setFrom("support@SuperFaaSApp.com");
      passwordResetEmail.setTo(user.getEmail());
      passwordResetEmail.setSubject("FaaS App Reset Request");
      passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
          + "/reset?token=" + user.getResetToken());

      emailService.sendEmail(passwordResetEmail);

      modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
    }

    modelAndView.setViewName("login");
    return modelAndView;
  }

  @GetMapping(value = "/reset")
  public ModelAndView displayResetPage(ModelAndView modelAndView, @RequestParam("token") String token) {

    Optional<User> user = userService.findUserByResetToken(token);

    if (user.isPresent()) {
      modelAndView.addObject("resetToken", token);
    } else {
      modelAndView.addObject("errorMessage", "invalid link.");
    }

    modelAndView.setViewName("resetPassword");
    return modelAndView;
  }

  @PostMapping(value = "/reset")
  public ModelAndView processResetPasswordPage(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redirectAttributes) {

    Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));

    User resetUser = user.get();

    resetUser.setPassword(requestParams.get("password"));
    resetUser.setResetToken(null);
    userService.save(resetUser);
    redirectAttributes.addFlashAttribute("successMessage", "You have successfully reset your password.");

    modelAndView.setViewName("/login");
    return modelAndView;


  }

}
