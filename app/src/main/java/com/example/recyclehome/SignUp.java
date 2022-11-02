package com.example.recyclehome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText txtname,txtphone,txtpass1,txtpass2;
    Spinner day,mounth,year;
    String name,phone,pass1,pass2,job1,gender;
    RadioButton maleradio,femaleradio;
    String Url="http://"+ Information.getIpAddress()+"/myfarmer/Signup.php";
    AlertDialog.Builder alert;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtname=findViewById(R.id.txtname);
        txtphone=findViewById(R.id.txtphone);
        txtpass1=findViewById(R.id.txtpass);
        txtpass2=findViewById(R.id.txtpass2);
        day=findViewById(R.id.spinner);
        mounth=findViewById(R.id.spinner2);
        year=findViewById(R.id.spinner3);
        maleradio = findViewById(R.id.radio_male);
        femaleradio = findViewById(R.id.radio_female);

        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation( this,R.id.txtname, RegexTemplate.NOT_EMPTY,R.string.inputname);
        awesomeValidation.addValidation( this,R.id.txtpass, ".{8,}",R.string.inputPass);
        awesomeValidation.addValidation( this,R.id.txtpass, R.id.txtpass2,R.string.notequalpass);
        awesomeValidation.addValidation(this,R.id.txtphone,".{10,}",R.string.inputphone);

        alert=new AlertDialog.Builder(this);
        alert.setCancelable(true);
        alert.setPositiveButton("OK",null);

        Button signup=findViewById(R.id.button1);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=txtname.getText().toString();
                phone=txtphone.getText().toString();
                pass1=txtpass1.getText().toString();
                pass2=txtpass2.getText().toString();
                job1="Advertiser";

                if (maleradio.isChecked())
                    gender = "Male";
                else
                    gender = "Female";
                if (awesomeValidation.validate()){
                    StringRequest request=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("true")) {
                                alert.setTitle("Sign Up");
                                alert.setMessage("Success Sign Up \nplease wait until to check your Information");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                alert.show();
                                txtname.setText("");
                                txtphone.setText("");
                                txtpass1.setText("");
                                txtpass2.setText("");

                            }else if (response.trim().equals("false")){
                                alert.setTitle("Invalid sign up ");
                                alert.setMessage("Please verify that the data is correct");
                                alert.show();
                            }else if (response.trim().equals("exist")){
                                alert.setTitle("sign up ");
                                alert.setMessage("This Number or Email has already been registered");
                                alert.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<>();
                            params.put("name",name);
                            params.put("phone",phone);
                            params.put("pass",pass1);
                            params.put("job",job1);
                            params.put("gender",gender);
                            params.put("birthday",day.getSelectedItem()+"/"
                             + mounth.getSelectedItem()+"/"
                             +year.getSelectedItem());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Validation Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}