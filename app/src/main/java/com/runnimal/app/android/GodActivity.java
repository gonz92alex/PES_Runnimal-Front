package com.runnimal.app.android;

import android.Manifest;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnimal.app.android.models.EntrenamientoContent;
import com.runnimal.app.android.models.MascotaContent;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class GodActivity extends FragmentActivity implements EntrenamientoFragment.OnListFragmentInteractionListener, AnadirMascotaFragment.OnFragmentInteractionListener, MascotasFragment.OnListFragmentInteractionListener, ProfileFragment.OnListFragmentInteractionListener {
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int PERMISSIONS_REQUEST = 1337;

    DrawerLayout drawerLayout;
    String correo;
    String nombre;
    int fotoPerfil;

    Fragment mapaFrag;
    RetosFragment retoFrag;
    EntrenamientoFragment entrenamientoFragment;
    MascotasFragment mascotaFragment;

    AmigosFragment amigosFragment;
    BusquedaFragment busquedaFragment;
    RankingFragment rankingFragment;
    AjustesFragment ajustesFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_mapa:
                    fragment = mapaFrag;
                    break;

                case R.id.navigation_entrenamientos:
                    fragment = entrenamientoFragment;
                    break;

                case R.id.navigation_retos:
                    fragment = retoFrag;
                    break;

                case R.id.navigation_mascotas:
                    fragment = mascotaFragment;
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
                            amigosFragment).commit();
                    break;

                case R.id.NavRanking:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            rankingFragment).commit();
                    break;

                case R.id.NavAjustes:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        ajustesFragment).commit();
                    break;

                case R.id.NavBusqueda:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new BusquedaFragment()).commit();
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
                    .addToBackStack(null)
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

        mapaFrag = initMap();
        retoFrag = new RetosFragment();
        entrenamientoFragment = EntrenamientoFragment.newInstance(1);
        mascotaFragment = new MascotasFragment();

        amigosFragment = new AmigosFragment();
        ajustesFragment = new AjustesFragment();
        rankingFragment = new RankingFragment();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(nOnNavigationItemSelectedListener);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /* esto es para poder cambiar el drawer con la info del usuario */
        correo = SingletonSession.Instance().getMail();
        nombre = SingletonSession.Instance().getUsername();

        View header = (navigationView.getHeaderView(0));
        TextView correoView = (TextView) header.findViewById(R.id.MailInApp);
        TextView nombreView = (TextView) header.findViewById(R.id.NombreInApp);
        correoView.setText(correo);
        nombreView.setText(nombre);
        ImageView perfilImage = (ImageView) header.findViewById(R.id.imageViewProfilePequeña);
        Picasso.get()
                .load("http://nidorana.fib.upc.edu/api/photo/users/" + SingletonSession.Instance().getMail())
                .resize(350,350)
                .onlyScaleDown()
                .into(perfilImage);
        /* hasta aqui lo del drawer dinamico */
    }


    public void ProfileEv(View view) {
        //ToDO estoy probando a ver si puedo hacer que funcione un botton en la imagen del navigation drawer
        //tendremos que cambiar esto
        ProfileFragment profile = ProfileFragment.newInstance(nombre, correo);
        //todo hay que ver como se passa la imagen de perfil
        loadFragment(profile);

        drawerLayout.closeDrawer(GravityCompat.START);

    }


    public void refreshDrawer(String nombre){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = (navigationView.getHeaderView(0));
        TextView nombreView = (TextView) header.findViewById(R.id.NombreInApp);
        SingletonSession.Instance().setUsername(nombre);
        nombreView.setText(SingletonSession.Instance().getUsername());
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
        PermissionUtils.checkPermissionsResult(this, requestCode, permissions, grantResults, getResources().getString(R.string.location_error_title), getResources().getString(R.string.location_error_descr));
    }

    private Fragment initMap() {
        PermissionUtils.checkLocation(this, getResources().getString(R.string.location_error_title), getResources().getString(R.string.location_error_descr));
        return new NoMapFragment();
    }


    //Metodo a implementar de MascotasFragment
    @Override
    public void onListFragmentInteraction(MascotaContent.MascotaItem item) {
        Log.d("clickTest", "onListFragmentInteraction: clicked! ");
        loadFragment(MascotaPerfilFragment.newInstance(item.getName(), item.getDetails(), item.getId()));
    }

    //Metodo a implementar de ProfileFragment
    @Override
    public void HorizontalListFragmentInteractionListener(MascotaContent.MascotaItem item) {
        Log.d("clickTest", "onListFragmentInteraction: clicked! ");
        loadFragment(MascotaPerfilFragment.newInstance(item.getName(), item.getDetails(), item.getId()));
    }

    public void refreshPet() {
        mascotaFragment = new MascotasFragment();

    }

    public void setAppLocale(String languageCode) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(languageCode.toLowerCase()));
        res.updateConfiguration(conf,dm);
    }

    public void refreshAjustes() {
       //ToDo arreglar este codigo 

        Fragment frg = null;
        frg = getSupportFragmentManager().findFragmentByTag("sjsjd");
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
}
