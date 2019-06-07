package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.WalkPresenter;
import com.runnimal.app.android.view.viewmodel.WalkViewModel;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalksListViewHolder extends RecyclerView.ViewHolder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT) //
            .withLocale(Locale.forLanguageTag("es-ES")) //
            .withZone(ZoneId.of("Europe/Paris"));

    private final WalkPresenter walkPresenter;

    @BindView(R.id.walk_title)
    TextView title;
    @BindView(R.id.walk_date)
    TextView date;
    @BindView(R.id.walk_duration)
    TextView duration;
    @BindView(R.id.walk_distance)
    TextView distance;

    public WalksListViewHolder(View itemView, WalkPresenter walkPresenter) {
        super(itemView);
        this.walkPresenter = walkPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(WalkViewModel walk) {
        onItemClick(walk);
        title.setText(walk.getTitle());
        date.setText("From " + DATE_TIME_FORMATTER.format(walk.getStart()) + " to " + DATE_TIME_FORMATTER.format(walk.getEnd()));
        duration.setText(walk.getDuration() + " min.");
        distance.setText(walk.getDistance() + " m.");
    }

    private void onItemClick(final WalkViewModel walk) {
        itemView.setOnClickListener(v -> walkPresenter.onWalkClicked(walk));
    }

}
