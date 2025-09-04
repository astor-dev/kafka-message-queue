package com.fastcampus.kafkahandson.event;

import com.fastcampus.kafkahandson.model.MyModelConverter;
import com.fastcampus.kafkahandson.producer.MyCdCProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MyCdcApplicationEventListener {

    private final MyCdCProducer myCdCProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processEvent(MyCdcApplicationEvent event) throws JsonProcessingException {
        myCdCProducer.sendMessage(
                MyModelConverter.toMessage(event.getId(), event.getMyModel(), event.getOperationType())
        );
    }
}
