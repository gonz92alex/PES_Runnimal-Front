package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingViewModelConverter {

    public static TrainingViewModel convert(Training training) {
        TrainingViewModel trainingViewModel = new TrainingViewModel() //
                .setId(training.getId()) //
                .setName(training.getName()) //
                .setDescription(training.getDescription());
        trainingViewModel.getSteps().addAll(training.getSteps());

        trainingViewModel.setImageUrl(URI.create("http://nidorana.fib.upc.edu/api/photo/trainnings/" + training.getId()));

        return trainingViewModel;
    }

    public static List<TrainingViewModel> convert(List<Training> trainings) {
        return trainings.stream() //
                .map(TrainingViewModelConverter::convert) //
                .collect(Collectors.toList());
    }
}
