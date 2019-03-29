package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entrenamiento.EntrenamientoContent;


public class GodActivity extends FragmentActivity implements EntrenamientoFragment.OnListFragmentInteractionListener, AnadirMascotaFragment.OnFragmentInteractionListener {
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int PERMISSIONS_REQUEST = 1337;

    DrawerLayout drawerLayout;
    String correo;
    String nombre;
    Bitmap fotoPerfil;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_mapa:
                    if (! isLocationEnabled()) {
                        showAlert();
                        fragment = new FragmentNoMap();
                    }
                    else {
                        if (!canAccessLocation()) {
                            requestPermissions(INITIAL_PERMS, PERMISSIONS_REQUEST);
                            fragment = new FragmentNoMap();
                        } else {
                            fragment = new MapFragment();
                        }
                    }
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

    public boolean loadFragment(Fragment fragment) {
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

        if (! isLocationEnabled()) {
            showAlert();
            loadFragment(new FragmentNoMap());
        }
        else {
            if (!canAccessLocation()) {
                requestPermissions(INITIAL_PERMS, PERMISSIONS_REQUEST);
                loadFragment(new FragmentNoMap());
            } else {
                loadFragment(new MapFragment());
            }
        }


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(nOnNavigationItemSelectedListener);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /* todo esto es para poder cambiar el drawer con la info del usuario que me esten pasando*/
        View header = (navigationView.getHeaderView(0));
        TextView correoView = (TextView) header.findViewById(R.id.MailInApp);
        TextView nombreView = (TextView) header.findViewById(R.id.NombreInApp);
        correoView.setText(SingletonSession.Instance().getMail());
        nombreView.setText(SingletonSession.Instance().getUsername());
        /* hasta aqui lo del drawer dinamico */
    }


    public void ProfileEv(View view) {
        //ToDO estoy probando a ver si puedo hacer que funcione un botton en la imagen del navigation drawer
        //tendremos que cambiar esto
        ProfileFragment profile = ProfileFragment.newInstance(nombre, correo, fotoPerfil);
        //hay que ver como se passa la imagen de perfil
        loadFragment(profile);

        drawerLayout.closeDrawer(GravityCompat.START);

    }


    //Metodos a implementar de EntrenamientoFragment
    @Override
    public void onListFragmentInteraction(EntrenamientoContent.EntrenamientoItem item) {
        Log.d("clickTest", "onListFragmentInteraction: clicked! ");
        loadFragment(EntrenamientoDetalladoFragment.newInstance(item.getContent(), item.getDetails(), item.getId()));
    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //Metodo a implementar de AnadirMascotaFragment
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadFragment(new MapFragment());
            }
            else {
                showAlert();
                loadFragment(new FragmentNoMap());
            }
        }
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
    }
}
