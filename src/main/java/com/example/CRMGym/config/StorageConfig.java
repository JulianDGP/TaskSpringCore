package com.example.CRMGym.config;

import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.models.Trainer;
import com.example.CRMGym.models.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class StorageConfig {

    @Bean
    public Map<Long, Trainer> trainerStorage(){
        return new ConcurrentHashMap<Long, Trainer>();
    }

    @Bean
    public Map<Long, Trainee> traineeStorage(){
        return new ConcurrentHashMap<Long, Trainee>();
    }

    @Bean
    public Map<Long, Training> trainingStorage(){
        return new ConcurrentHashMap<Long, Training>();
    }

}
