package com.runnimal.app.android.view.viewmodel.converter;

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

        petViewModel.setImageUrl(URI.create("https://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908"));

        petViewModel.setOwner(OwnerViewModelConverter.convert(pet.getOwner()));

        return petViewModel;
    }
}
