package com.e451.faas.demo.faasDemo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailServiceImpl implements EmailService {

  Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

  @Value("${spring.mail.host}")
  String host;

  public String sendEmail(SimpleMailMessage message) {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN);
    String passwordFromFunction = "Email not provided";
    if (message.getTo().length > 0) {
      HttpEntity<String> entity = new HttpEntity<>(message.getTo()[0], headers);

      String url = "http://" + host;
      try {
        passwordFromFunction = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        log.info(String.format("Updated password is: " + passwordFromFunction));

//        if (forEntity.getStatusCode().is2xxSuccessful()) {
//          passwordFromFunction = forEntity.getBody();
//          log.info(String.format("Updated password is: " + passwordFromFunction));
//        } else {
//          log.error(forEntity.getStatusCode().getReasonPhrase());
//        }
      } catch (Exception e) {
        log.error("Something went wrong", e);
      }
    }

    return passwordFromFunction;
  }
}
