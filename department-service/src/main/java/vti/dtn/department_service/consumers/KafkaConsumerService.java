package vti.dtn.department_service.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "department-topic", groupId = "department-group")
    public void consumeMessage(String message) {
        log.info("Received message from Kafka: {}", message);
    }
}
