package com.example.recyclehome.ui.Cart;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recyclehome.Information;
import com.example.recyclehome.Materials;
import com.example.recyclehome.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Materials> MaterialList;
    AlertDialog.Builder alert;
    private String URL="http://"+ Information.getIpAddress()+"/Recycling/Cart_contents.php";
    RequestQueue queue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_basket, container, false);
        alert = new AlertDialog.Builder(getContext());
        recyclerView = view.findViewById(R.id.CartRecycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());

        Button btnOrder=view.findViewById(R.id.button2);
        
        StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                buildmateriallist(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
             Map<String,String>param=new HashMap<>();
             param.put("user_id",Information.getId()+"");
                return param;
            }
        };
        queue.add(request);
        
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Information.getWeight_material()>0.0){
                    String Url="http://"+Information.getIpAddress()+"/recycling/Load_order.php";
                    StringRequest request1=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(), "Sccussful Load Order", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>param=new HashMap<>();
                            param.put("id", Information.getId()+"");
                            return param;
                        }
                    };
                    queue.add(request1);
                }else {
                    alert.setTitle("Invalid");
                    alert.setMessage("Please Check Weight Value");
                    alert.setCancelable(true);
                    alert.setPositiveButton("Yes",null);
                    alert.show();
                }

            }
        });
        return view;
    }
    private void buildmateriallist(String response){
        try {
            MaterialList = new ArrayList<Materials>();
            JSONObject object=new JSONObject(response);
            JSONArray allmaterial=object.getJSONArray("Materials");
            for (int i=0;i<allmaterial.length();i++){
                JSONObject onematerial=allmaterial.getJSONObject(i);
                int id=onematerial.getInt("ID_Material");
                String namematerial=onematerial.getString("Material_Name");
                String image=Information.getPathimage()+onematerial.getString("Material_image");
                float weight=(float) onematerial.getDouble("Weight");
                Materials materials=new Materials(id,image,namematerial,weight);
                MaterialList.add(materials);
            }
            adapter = new CartAdapter(getActivity().getApplicationContext(),MaterialList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }
}