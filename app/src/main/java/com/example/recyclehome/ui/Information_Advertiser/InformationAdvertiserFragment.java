package com.example.recyclehome.ui.Information_Advertiser;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.recyclehome.Information;
import com.example.recyclehome.LoginActivity;
import com.example.recyclehome.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class InformationAdvertiserFragment extends Fragment {
    private TextView name,gender,birthday;
    private EditText phone;
    String Url="http://"+ Information.getIpAddress()+"/Recycling/GetUserInfo.php",
            URL2="http://"+ Information.getIpAddress()+"/Recycling/UPDATE_PHONE.php";

    AwesomeValidation awesomeValidation;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_information_advertiser,container,false);
        name=view.findViewById(R.id.nametxt);
        gender=view.findViewById(R.id.textgender);
        birthday=view.findViewById(R.id.textbirthday);
        phone=view.findViewById(R.id.editPhone);
        Button logout=view.findViewById(R.id.btnlogout);
        Button update=view.findViewById(R.id.btnupdate);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(getActivity(), R.id.editPhone, "^[+]?[0-9]{10,13}$", R.string.inputphone);
        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        StringRequest request=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GETinformation(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("ID", Information.getId()+"");
                return param;
            }
        };
        queue.add(request);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.trim().equals("true")) {
                                Toast.makeText(getContext(), "Successful Update", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "fail Update", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("ID", Information.getId() + "");
                            param.put("phone", phone.getText().toString());
                            return param;
                        }
                    };
                    queue.add(request);
                }
            }
        });
        return view;
    }
    private void GETinformation(String response){
        try {
            JSONObject user=new JSONObject(response);
            name.setText(user.getString("Name"));
            birthday.setText(user.getString("Birthday"));
            gender.setText(user.getString("Gender"));
            phone.setText(user.getString("Phone"));
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}