package com.fastcampus.kafkahandson.event;

import com.fastcampus.kafkahandson.model.MyModelConverter;
import com.fastcampus.kafkahandson.producer.MyCdCProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Deprecated
public class MyCdcApplicationEventListener {

    private final MyCdCProducer myCdCProducer;

    /**
     * entity listeners 활용으로 이벤트 미사용
     */
    @Deprecated
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void processEvent(MyCdcApplicationEvent event) throws JsonProcessingException {
        myCdCProducer.sendMessage(
                MyModelConverter.toMessage(event.getId(), event.getMyModel(), event.getOperationType())
        );
    }
}
