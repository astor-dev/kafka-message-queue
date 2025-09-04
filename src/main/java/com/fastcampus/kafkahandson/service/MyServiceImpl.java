package com.fastcampus.kafkahandson.service;

import com.fastcampus.kafkahandson.data.MyEntity;
import com.fastcampus.kafkahandson.data.MyJpaRepository;
import com.fastcampus.kafkahandson.model.MyModel;
import com.fastcampus.kafkahandson.model.MyModelConverter;
import com.fastcampus.kafkahandson.model.OperationType;
import com.fastcampus.kafkahandson.producer.MyCdCProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyServiceImpl implements MyService {
    private final MyJpaRepository myJpaRepository;
    private final MyCdCProducer myCdCProducer;

    @Override
    public List<MyModel> findAll() {
        List<MyEntity> myEntities = myJpaRepository.findAll();
        return myEntities.stream().map(MyModelConverter::toModel).toList();
    }

    @Override
    public MyModel findById(Integer id) {
        Optional<MyEntity> myEntity = myJpaRepository.findById(id);
        return myEntity.map(MyModelConverter::toModel).orElse(null);
    }

    @Override
    @Transactional
    public MyModel save(MyModel myModel) {
        MyEntity entity = myJpaRepository.save(MyModelConverter.toEntity(myModel));
        MyModel model = MyModelConverter.toModel(entity);
        try {
            myCdCProducer.sendMessage(
                    MyModelConverter.toMessage(
                            entity.getId(),
                            model,
                            model.getId() == null ? OperationType.CREATE : OperationType.UPDATE
                    )
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return model;

    }

    @Override
    @Transactional
    public void delete(Integer id) {
        myJpaRepository.deleteById(id);
        try {
            myCdCProducer.sendMessage(
                    MyModelConverter.toMessage(
                            id,
                            null,
                            OperationType.DELETE
                    )
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
