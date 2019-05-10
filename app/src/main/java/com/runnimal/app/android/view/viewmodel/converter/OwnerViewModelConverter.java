package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;

import java.net.URI;


public class OwnerViewModelConverter {

    public static OwnerViewModel convert(Owner owner) {
        if (owner == null) {
            return null;
        }

        OwnerViewModel ownerViewModel = new OwnerViewModel() //
                .setId(owner.getId()) //
                .setAlias(owner.getAlias()) //
                .setEmail(owner.getEmail());

        ownerViewModel.setImageUrl(URI.create(""));

        return ownerViewModel;
    }
}
