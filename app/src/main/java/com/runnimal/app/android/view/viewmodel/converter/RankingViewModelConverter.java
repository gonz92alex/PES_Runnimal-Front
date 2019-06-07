package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.view.viewmodel.RankingViewModel;

import java.util.List;
import java.util.stream.Collectors;
import java.net.URI;


public class RankingViewModelConverter {


    public static RankingViewModel convert(Ranking ranking) {
        if (ranking == null) {
            return null;
        }

        RankingViewModel ownerViewModel = new RankingViewModel() //
                .setId(ranking.getId()) //
                .setAlias(ranking.getAlias()) //
                .setPoints(ranking.getPoints())
                .setEmail(ranking.getEmail());

        ownerViewModel.setImageUrl(URI.create("http://nidorana.fib.upc.edu/api/photo/users/" + ranking.getEmail()));

        return ownerViewModel;
    }
}
