package com.fastcampus.kafkahandson.service;

import com.fastcampus.kafkahandson.model.MyModel;

import java.util.List;

public interface MyService {

    List<MyModel> findAll();

    MyModel findById(Integer id);

    MyModel save(MyModel myModel);

    void delete(Integer id);
}
