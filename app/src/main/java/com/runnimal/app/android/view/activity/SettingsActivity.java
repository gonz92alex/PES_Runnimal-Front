package com.runnimal.app.android.view.activity;

public class SettingsActivity {

    //TODO: Implementar
    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ajustes, container, false);


        Button notificacionButton = view.findViewById(R.id.btn_notificaciones);
        notificacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               NotificacionEv();
            }
        });

        final Spinner spinnerIdioma = view.findViewById(R.id.ChooseLanguage);
        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //ToDO coger el texto y llamar a la funcion qeu cambia el texto de la aplicaicon
                String LanguageCode = (String) spinnerIdioma.getSelectedItem();

                GodActivity godActivity = (GodActivity)getActivity();
                godActivity.setAppLocale(LanguageCode);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });



        return view;
    }

    private void AddEvPet(View view) {
        //((GodActivity)getActivity()).loadFragment(ModifyPetFragment.newInstance());
    }

    public void AddEvUser() {
        ((GodActivity)getActivity()).loadFragment(ModifyUserFragment.newInstance());
    }

    public void NotificacionEv(){
        ((GodActivity)getActivity()).loadFragment(SolicitudesFragment.newInstance());
    }
     */
}
