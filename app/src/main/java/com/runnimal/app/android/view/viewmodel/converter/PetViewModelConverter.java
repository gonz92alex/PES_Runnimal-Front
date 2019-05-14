package com.runnimal.app.android.view.viewmodel.converter;

import android.util.Log;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import java.net.URI;

public class PetViewModelConverter {

    public static PetViewModel convert(Pet pet) {
        if (pet == null) {
            return null;
        }

        PetViewModel petViewModel = new PetViewModel() //
                .setId(pet.getId()) //
                .setName(pet.getName()) //
                .setDescription(pet.getDescription()) //
                .setWeight(pet.getWeight()) //
                .setBreed(pet.getBreed()) //
                .setBirth(pet.getBirth()) //
                .setSize(pet.getSize());

        petViewModel.setImageUrl(URI.create("http://nidorana.fib.upc.edu/api/photo/pets/" + pet.getOwner().getEmail() + "/" + pet.getName()));

        petViewModel.setOwner(OwnerViewModelConverter.convert(pet.getOwner()));

        return petViewModel;
    }
}
