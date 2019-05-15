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

        Log.d("refactor", "convert: " + pet.getOwner().getEmail() + " " + pet.getName());
        petViewModel.setImageUrl(URI.create("http://nidorana.fib.upc.edu/api/photo/pets/" + pet.getOwner().getEmail() + "/" + pet.getName()));
        //petViewModel.setImageUrl(URI.create("https://www.wikipets.es/wp-content/uploads/2015/11/perro.jpg"));

        petViewModel.setOwner(OwnerViewModelConverter.convert(pet.getOwner()));

        return petViewModel;
    }
}
