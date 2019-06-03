package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.User;
import com.runnimal.app.android.view.viewmodel.SearchViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class SearchViewModelConverter {

    public static SearchViewModel convert(User user) {
        SearchViewModel searchViewModel = new SearchViewModel() //
                .setId(user.getId()) //
                .setName(user.getName())
                .setImageUrl(user.getImageUrl());

        return searchViewModel;
    }

    public static List<SearchViewModel> convert(List<User> user) {
        return user.stream() //
                .map(SearchViewModelConverter::convert) //
                .collect(Collectors.toList());
    }
}
