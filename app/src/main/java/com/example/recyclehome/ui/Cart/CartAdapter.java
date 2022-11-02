package com.example.recyclehome.ui.Cart;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.recyclehome.Information;
import com.example.recyclehome.Materials;
import com.example.recyclehome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Materials> MaterialsList;
    RequestQueue queue;


    public CartAdapter(Context context, List<Materials> MaterialsList) {
        this.context = context;
        this.MaterialsList = MaterialsList;
        queue= Volley.newRequestQueue(context);

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.material_basket, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Materials materials = MaterialsList.get(position);
        holder.setValues(materials);
        EditText editText=holder.weight;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    double weig=Double.parseDouble(editable.toString());
                    Information.setWeight_material(weig);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return MaterialsList.size();
    }

    public void filterList(ArrayList<Materials> filteredlist) {
        MaterialsList = filteredlist;
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView materialname;
        private ImageView material_image,cancelOrder;
        public EditText weight;
        private Button EditOrder;

        public CartViewHolder(View itemView) {
            super(itemView);
            material_image = itemView.findViewById(R.id.Img_Material);
            materialname = itemView.findViewById(R.id.materialname);
            weight = itemView.findViewById(R.id.weight);
            cancelOrder=itemView.findViewById(R.id.cancel);
            EditOrder=itemView.findViewById(R.id.EditOrder);
            cancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String URL = "http://" + Information.getIpAddress() + "/recycling/Delete_Material.php";
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("true")) {
                                Toast.makeText(itemView.getContext(), "Successful Remove", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            int pos = getAdapterPosition();
                            Materials materials = MaterialsList.get(pos);
                            Information.setId_material(materials.getId());
                            Map<String, String> params = new HashMap<>();
                            params.put("User_id", Information.getId() + "");
                            params.put("Material_id", Information.getId_material() + "");
                            return params;
                        }
                    };
                    queue.add(request);
                }
            });

            EditOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 int pos=getAdapterPosition();
                 Materials materials=MaterialsList.get(pos);
                 int material_id=materials.getId();
                 String url="http://"+Information.getIpAddress()+"/recycling/Update_Material.php";
                 StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                        if (response.trim().equals("true"))
                            Toast.makeText(view.getContext(), "Successful Update", Toast.LENGTH_SHORT).show();
                     }
                 }, new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {

                     }
                 }){
                     @Nullable
                     @Override
                     protected Map<String, String> getParams() throws AuthFailureError {
                         Map<String ,String>param=new HashMap<>();
                         param.put("material_id",material_id+"");
                         param.put("user_id",Information.getId()+"");
                         param.put("weight",Information.getWeight_material()+"");
                         return param;
                     }
                 };
                 queue.add(request);
                }

            });

        }
        public void setValues(Materials materials) {
            Glide.with(context).load(materials.getImageId()).into(material_image);
            cancelOrder.setImageResource(R.drawable.ic_baseline_cancel_24);
            materialname.setText(materials.getMeterial_Name());
            weight.setText(materials.getWeight()+"");

        }
    }
}
