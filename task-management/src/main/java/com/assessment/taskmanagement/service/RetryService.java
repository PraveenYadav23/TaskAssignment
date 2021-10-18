package com.assessment.taskmanagement.service;

import com.assessment.taskmanagement.exception.BackUpServerException;
import com.assessment.taskmanagement.request.TaskBackupRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

@Service
public class RetryService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${task.backup.uri}")
    private String taskBackUri;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Retryable(value = {RuntimeException.class}, maxAttempts = 2, backoff = @Backoff(delay = 5000))
    public void saveBackupTaskMethod(TaskBackupRequest taskBackupRequest) throws RuntimeException {
        logger.info("In Backup Method: "+Calendar.getInstance().getTime());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskBackupRequest> backupRequest = new HttpEntity<>(taskBackupRequest, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                taskBackUri, HttpMethod.POST, backupRequest, String.class
        );
        logger.info(responseEntity.getBody());
    }

    @Recover
    public void recoverMethod(Throwable throwable){
        logger.info(throwable.getMessage());
        throw new BackUpServerException(throwable.getLocalizedMessage());
    }

}
