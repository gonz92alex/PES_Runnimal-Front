package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class PetsViewModelConverter {

    public static TrainingViewModel convert(Pet pet) {
        PetViewModel petViewModel = new PetViewModel() //
        .setId(pet.getId()) //
        .setName(pet.getName()) //
        .setDescription()
        trainingViewModel.setImageUrl()
        trainingViewModel.getSteps().addAll(training.getSteps());

        return trainingViewModel;
    }

    public static List<TrainingViewModel> convert(List<Training> trainings) {
        return trainings.stream() //
                .map(TrainingViewModelConverter::convert) //
                .collect(Collectors.toList());
    }
}
