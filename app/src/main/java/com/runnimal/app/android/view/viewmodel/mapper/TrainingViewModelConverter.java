package com.runnimal.app.android.view.viewmodel.mapper;

import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class TrainingViewModelConverter {

    public static TrainingViewModel convert(Training training) {
        TrainingViewModel trainingViewModel = new TrainingViewModel();
        trainingViewModel.getSteps().addAll(training.getSteps());
        trainingViewModel.setName(training.getName());
        trainingViewModel.setDescription(training.getDescription());
        trainingViewModel.setImageUrl(training.getImageUrl());

        return trainingViewModel;
    }

    public static List<TrainingViewModel> convert(List<Training> trainings) {
        return trainings.stream() //
                .map(TrainingViewModelConverter::convert) //
                .collect(Collectors.toList());
    }
}
