package com.example.recyclehome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private AlertDialog.Builder alert;
    String job;
    String URL="http://"+Information.getIpAddress()+"/Recycling/login.php";
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText phone=findViewById(R.id.editPhone);
        EditText password=findViewById(R.id.editTextPassword);
        Button SignIn=findViewById(R.id.signin);
        TextView Signup=findViewById(R.id.signup);
        alert=new AlertDialog.Builder(this);
        queue= Volley.newRequestQueue(this);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = phone.getText().toString();
                String pas = password.getText().toString();
                if (ph.length() == 10 && pas.length() != 0) {
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            checkjob(response);
                            if (job.trim().equals("Advertiser")) {
                                Intent intent = new Intent(getApplicationContext(), AdvertiserActivity.class);
                                startActivity(intent);
                                password.setText("");
                                phone.setText("");
                            } else if (job.trim().equals("Engineer")) {
                                //Intent intent1=new Intent(getApplicationContext(),EngFarActivity.class);
                               // startActivity(intent1);
                                password.setText("");
                                phone.setText("");
                            } else if (job.trim().equals("false")) {
                                alert.setTitle("Invalid Login");
                                alert.setMessage("Invalid Phone or password");
                                alert.setCancelable(true);
                                alert.setPositiveButton("OK", null);
                                alert.show();
                                password.setText("");
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
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("phone", ph);
                            params.put("pass", pas);
                            return params;
                        }
                    };
                    queue.add(request);
                }else {
                    alert.setTitle("Invalid Login");
                    alert.setMessage("Invalid Phone or password");
                    alert.setCancelable(true);
                    alert.setPositiveButton("OK", null);
                    alert.show();
                    password.setText("");
                }
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });
    }
    private void checkjob(String respones){
        try {
            JSONObject jsonObject = new JSONObject(respones);
            job = jsonObject.getString("Job");
            Information.setId(jsonObject.getInt("ID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}