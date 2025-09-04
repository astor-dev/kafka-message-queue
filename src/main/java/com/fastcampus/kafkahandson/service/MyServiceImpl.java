package com.fastcampus.kafkahandson.service;

import com.fastcampus.kafkahandson.data.MyEntity;
import com.fastcampus.kafkahandson.data.MyJpaRepository;
import com.fastcampus.kafkahandson.model.MyModel;
import com.fastcampus.kafkahandson.model.MyModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyServiceImpl implements MyService {
    private final MyJpaRepository myJpaRepository;

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
    public MyModel save(MyModel myModel) {
        MyEntity entity = myJpaRepository.save(MyModelConverter.toEntity(myModel));
        return MyModelConverter.toModel(entity);
    }

    @Override
    public void delete(Integer id) {
        myJpaRepository.deleteById(id);
    }
}
