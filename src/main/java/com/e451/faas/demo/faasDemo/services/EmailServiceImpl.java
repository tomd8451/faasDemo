package com.e451.faas.demo.faasDemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private JavaMailSender javaMailSender;

  //TODO: Turn this sendEmail method into it's own function
  @Async
  public void sendEmail(SimpleMailMessage message){
    javaMailSender.send(message);
  }
}
