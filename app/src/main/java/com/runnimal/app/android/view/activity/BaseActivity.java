package com.runnimal.app.android.view.activity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.runnimal.app.android.R;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.util.ImageUtils;

import java.net.URI;
import java.util.Calendar;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView menuView;
    protected BottomNavigationView bottomMenuView;
    public static final String  CHANNEL = "channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(getLayoutId());
        initDrawerLayout();
        initMenu();
        initBottomMenu();
        bindViews();
        initView();
        createNotificationChannels();
        notificationDaily();
    }

    private void createNotificationChannels(){
         if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.O){
             NotificationChannel channel = new NotificationChannel(CHANNEL,  "test", NotificationManager.IMPORTANCE_HIGH);
             channel.setDescription("this is the channel for the notifications");

             NotificationManager manager = getSystemService(NotificationManager.class);
             manager.createNotificationChannel(channel);
         }

    }

    private void notificationDaily() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,15);
        Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
        intent.setAction("MY_NOTIFICATION_MESSAGE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmManager.INTERVAL_DAY,pendingIntent);
    }





    // Remove inter-activity transition to avoid screen tossing on tapping bottom menu_bottom items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBottomMenuState();
    }

    protected abstract int getLayoutId();

    protected abstract int getBottomMenuItemId();

    protected abstract void initView();

    protected void refreshMenuInfo() {
        menuView = findViewById(R.id.navigation_menu);

        URI image = SingletonSession.Instance().getPhoto();
        String alias = SingletonSession.Instance().getUsername();
        String email = SingletonSession.Instance().getMail();
        View headerView = menuView.getHeaderView(0);
        ImageView imageView = headerView.findViewById(R.id.image_menu_current_user);
        TextView aliasView = headerView.findViewById(R.id.text_menu_current_user_alias);
        TextView emailView = headerView.findViewById(R.id.text_menu_current_user_email);
        ImageUtils.setImage(image, imageView);
        aliasView.setText(alias);
        emailView.setText(email);
    }

    protected void closeMenu() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void initDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void initMenu() {
        menuView = findViewById(R.id.navigation_menu);

        refreshMenuInfo();

        View headerView = menuView.getHeaderView(0);
        ImageView imageView = headerView.findViewById(R.id.image_menu_current_user);
        String id = SingletonSession.Instance().getId();
        String email = SingletonSession.Instance().getMail();
        imageView.setOnClickListener(view -> {
            closeMenu();
            OwnerDetailActivity.open(this, id, email);
        });

        menuView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_friends) {
                closeMenu();
                startActivity(new Intent(this, FriendsActivity.class));
                return true;
            } else if (itemId == R.id.menu_settings) {
                closeMenu();
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            } else if (itemId == R.id.menu_search) {
                closeMenu();
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            }else if (itemId == R.id.menu_notifications) {
                closeMenu();
                startActivity(new Intent(this, FriendRequestsActivity.class));
                return true;
            } else if (itemId == R.id.menu_logout){
                closeMenu();
                SharedPreferences prefs =
                        getSharedPreferences("userdetails", MODE_PRIVATE);
                prefs.edit().clear().apply();
                startActivity(new Intent(this, MainActivity.class));
            }
            return false;
        });
    }

    private void initBottomMenu() {
        bottomMenuView = findViewById(R.id.navigation);
        bottomMenuView.setOnNavigationItemSelectedListener(item -> {
            bottomMenuView.postDelayed(() -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_bottom_map) {
                    startActivity(new Intent(this, MapActivity.class));
                } else if (itemId == R.id.menu_bottom_trainings) {
                    startActivity(new Intent(this, TrainingsActivity.class));
                } else if (itemId == R.id.menu_bottom_ranking) {
                    startActivity(new Intent(this, StatisticsActivity.class));
                } else if (itemId == R.id.menu_bottom_pets) {
                    startActivity(new Intent(this, PetsActivity.class));
                }
                finish();
            }, 300);

            return true;
        });
    }

    private void bindViews() {
        ButterKnife.bind(this);
    }

    private void updateBottomMenuState() {
        int actionId = getBottomMenuItemId();
        if (actionId != -1) {
            MenuItem item = bottomMenuView.getMenu().findItem(actionId);
            item.setChecked(true);
        }
    }

}