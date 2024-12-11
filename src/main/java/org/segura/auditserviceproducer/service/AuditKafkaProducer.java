package org.segura.auditserviceproducer.service;
/*
 * Created by Daniel - 18/11/2024 (19:10)
 */

import org.segura.auditserviceproducer.dto.AuditLogDto;
import org.segura.auditserviceproducer.mapper.AuditLogMapper;
import org.segura.auditserviceproducer.model.AuditLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/***
 * Sends audit logs to Kafka asynchronously, logging success or errors
 */

@Service
public class AuditKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(AuditKafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Value("${kafka.audit.topic}")
    private String auditTopic;

    public void sendAuditLog(AuditLog auditLog) {
        AuditLogDto auditLogDto = auditLogMapper.toDto(auditLog);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(auditTopic, auditLogDto);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message =[{}] with offset=[{}]", auditLogDto, result.getRecordMetadata().offset());
            } else {
                logger.error("Unable to send message=[{}] due to : {}", auditLogDto, ex.getMessage());
            }
        });
    }

    /*
    public void sendAuditLog(AuditLog auditLog) {
        AuditLogDto auditLogDto = auditLogMapper.toDto(auditLog);
        kafkaTemplate.send("audit_topic", auditLogDto)
                .addCallback(
                        result -> logger.info("Message sent successfully: {}", auditLogDto),
                        ex -> logger.error("Failed to send message: {}, due to: {}", auditLogDto, ex.getMessage())
                );
    }

     */
}
