package com.example.CRMGym.config;

import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.models.Trainer;
import com.example.CRMGym.services.TraineeService;
import com.example.CRMGym.services.TrainerService;
import com.example.CRMGym.services.TrainingService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Value("classpath:data/trainees.json")
    private Resource traineeData;

    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TrainingService trainingService;



    @PostConstruct
    public void loadTraineeData() throws IOException {
        String content = new String(Files.readAllBytes(traineeData.getFile().toPath()));
        List<Trainee> trainees = parseTraineesFromJson(content);
        trainees.forEach(traineeService::createTrainee);
    }

    
    @PostConstruct
    public void initData() {
        // Simulando algunos datos
        trainerService.createTrainer(new Trainer(1L, "John", "Doe", "john.doe", "pass123", true));
        traineeService.createTrainee(new Trainee(1L, "Jane", "Doe", "jane.doe", "pass123", true));
        // Agregar más datos según sea necesario
    }
}
