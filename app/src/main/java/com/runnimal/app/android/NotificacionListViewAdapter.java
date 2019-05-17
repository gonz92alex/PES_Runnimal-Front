package com.runnimal.app.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;

public class NotificacionListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<ModelSolicitud> modelslist;
    ArrayList<ModelSolicitud> arrayList;


    public NotificacionListViewAdapter(Context Context, List<ModelSolicitud> modelslist) {
        mContext = Context;
        this.modelslist = modelslist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ModelSolicitud>();
        this.arrayList.addAll(modelslist);
    }

    public class ViewHolder {
        TextView mTitleTv;
        TextView mMailTv;
        ImageView mIconIv;
        Button aceptBtn;
        Button rechazarBtn;

    }

    @Override
    public int getCount() {
        return modelslist.size();
    }

    @Override
    public Object getItem(int position) {
        return modelslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        NotificacionListViewAdapter.ViewHolder holder;
        if (convertView == null){
            holder = new NotificacionListViewAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.row_notificaciones, null);

            holder.mTitleTv = convertView.findViewById(R.id.mainTitle);
            holder.mIconIv = convertView.findViewById(R.id.fotoUser);
            holder.mMailTv = convertView.findViewById(R.id.mailUsers);
            holder.aceptBtn = convertView.findViewById(R.id.aceptar);
            holder.rechazarBtn = convertView.findViewById(R.id.rechazar);

            convertView.setTag(holder);
        }
        else{
            holder = (NotificacionListViewAdapter.ViewHolder)convertView.getTag();
        }

        holder.mTitleTv.setText(modelslist.get(position).getTitle());
        holder.mIconIv.setImageResource(modelslist.get(position).getIcon());
        holder.mMailTv.setText(modelslist.get(position).getMail());
        holder.aceptBtn.setTag(position);
        holder.rechazarBtn.setTag(position);
        holder.aceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i("VOLLEYACTION", modelslist.get(position).getId());
                    ApiAceptar(modelslist.get(position).getId());
                    int posToRemove= (int) v.getTag();
                    modelslist.remove(posToRemove);
                    notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.rechazarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ApiRechazar(modelslist.get(position).getId());
                    int posToRemove= (int) v.getTag();
                    modelslist.remove(posToRemove);
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });






        return convertView;
    }
    private void ApiAceptar(final String idReq) throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="http://nidorana.fib.upc.edu/api/friendRequests/accept";

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("id",idReq );
        final String requestBody = jsonBody.toString();
        Log.i("VOLLEYANTESACCEPT", idReq);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
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
        queue.add(stringRequest);

    }
    private void ApiRechazar(final String idReq) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="http://nidorana.fib.upc.edu/api/friendRequests/refuse/";

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Log.i("VOLLEY", idReq);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("id",idReq );
        final String requestBody = jsonBody.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
