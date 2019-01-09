package com.e451.faas.demo.faasDemo.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
  String sendEmail(SimpleMailMessage message);
}
