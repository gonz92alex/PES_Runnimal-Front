package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnadirMascotaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnadirMascotaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnadirMascotaFragment extends Fragment {


    private static final int CAMERA_REQUEST = 1888;
    private ImageView ImageViewProfile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public AnadirMascotaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AnadirMascotaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnadirMascotaFragment newInstance() {
        AnadirMascotaFragment fragment = new AnadirMascotaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View AddPet = inflater.inflate(R.layout.fragment_anadir_mascota, container, false);

        ImageViewProfile = (ImageView) AddPet.findViewById(R.id.imageViewProfile);
        Button ButtonCamera = (Button) AddPet.findViewById(R.id.buttonCameraEdit);
        ButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });

        Button addButton = AddPet.findViewById(R.id.buttonAddPet);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreateEv();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return AddPet;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST){
            Bitmap bitmapPhoto = (Bitmap)data.getExtras().get("data");
            ImageViewProfile.setImageBitmap(bitmapPhoto);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void CreateEv() throws JSONException {
        EditText nombre = (EditText) getView().findViewById(R.id.EditTextDogName);
        EditText descripcion = (EditText) getView().findViewById(R.id.EditTextDogDescription);
        EditText raza = (EditText) getView().findViewById(R.id.EditTextDogBreed);
        EditText peso = (EditText) getView().findViewById(R.id.EditTextDogWeight);
        EditText tamano = (EditText) getView().findViewById(R.id.EditTextDogSize);
        EditText anoNacimiento = (EditText) getView().findViewById(R.id.EditTextDogBirthdate);

        String name = nombre.getText().toString();
        String description = descripcion.getText().toString();
        String breed = raza.getText().toString();
        String weight = peso.getText().toString();
        String size = tamano.getText().toString();
        String year = anoNacimiento.getText().toString();

        if (name.equals("") || description.equals("") || breed.equals("") || weight.equals("") || size.equals("") || year.equals("")){
            new AlertDialog.Builder(getActivity())
                    .setTitle("Missing parameters")
                    .setMessage("You have to fill first all the text camps")

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            petPOST(name,description,breed,weight,size,year);
        }
    }

    private void petPOST(String name, String description, String breed, String weight, String size, String year) throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://nidorana.fib.upc.edu/api/pets/";

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //Construir el cuerpo del request con la información a enviar
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", name);
        jsonBody.put("description", description);
        jsonBody.put("race", breed);
        jsonBody.put("weight", weight);
        jsonBody.put("size", size);
        jsonBody.put("birth", year);
        jsonBody.put("owner", SingletonSession.Instance().getMail());
        final String requestBody = jsonBody.toString();


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("VOLLEY", response);
                        //ToDo -> si la respuesta es 'OK' redirigir a pantalla de mascotas de nuevo/al perfil de la mascota?
                        success();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void success() {
        ((GodActivity)getActivity()).loadFragment(new MascotasFragment());
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
