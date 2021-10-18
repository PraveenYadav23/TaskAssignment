package com.assessment.taskmanagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Async
    public void sendSimpleEmail(SimpleMailMessage simpleMailMessage) {
        try {
            javaMailSender.send(simpleMailMessage);
        }catch (Exception ex){
            logger.info(ex.getMessage());
        }
    }
}
