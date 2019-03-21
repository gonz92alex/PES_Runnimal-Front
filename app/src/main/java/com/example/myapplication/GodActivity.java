package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.myapplication.entrenamiento.EntrenamientoContent;


public class GodActivity extends AppCompatActivity implements EntrenamientoFragment.OnListFragmentInteractionListener {
    DrawerLayout drawerLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_mapa:
                    fragment = new MapFragment();
                    break;

                case R.id.navigation_entrenamientos:
                    fragment = EntrenamientoFragment.newInstance(1);
                    break;

                case R.id.navigation_retos:
                    fragment = new RetosFragment();
                    break;

                case R.id.navigation_mascotas:
                    fragment = new MascotasFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    private NavigationView.OnNavigationItemSelectedListener nOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.NavAmigos:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AmigosFragment()).commit();
                    break;

                case R.id.NavRanking:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new RankingFragment()).commit();
                    break;

                case R.id.NavAjustes:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AjustesFragment()).commit();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return loadFragment(fragment);


        }
    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_god);
        drawerLayout = findViewById(R.id.drawer_layout);

        loadFragment(new MapFragment());

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(nOnNavigationItemSelectedListener);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    public void onListFragmentInteraction(EntrenamientoContent.EntrenamientoItem item) {
        Log.d("clickTest", "onListFragmentInteraction: clicked! ");
    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void ProfileEv(View view) {
        //estoy probando a ver si puedo hacer que funcione un botton en la imagen del navigation drawer
        //tendremos que cambiar esto
        loadFragment(new RetosFragment());

        drawerLayout.closeDrawer(GravityCompat.START);

    }
}
