package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.view.viewmodel.RankingViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class RankingViewModelConverter {

    public static RankingViewModel convert(Ranking ranking) {
        RankingViewModel rankingViewModel = new RankingViewModel() //
                .setId(ranking.getId()) //
                .setName(ranking.getName())
                .setPoints(ranking.getPoints())////
                .setImageUrl(ranking.getImageUrl());

        return rankingViewModel;
    }

    public static List<RankingViewModel> convert(List<Ranking> rankings) {
        return rankings.stream() //
                .map(RankingViewModelConverter::convert) //
                .collect(Collectors.toList());
    }
}
