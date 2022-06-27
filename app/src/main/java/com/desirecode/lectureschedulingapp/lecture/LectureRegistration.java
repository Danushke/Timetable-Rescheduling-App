package com.desirecode.lectureschedulingapp.lecture;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.desirecode.lectureschedulingapp.Constant;
import com.desirecode.lectureschedulingapp.R;
import com.desirecode.lectureschedulingapp.timetable.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LectureRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText ETname, ETindex, mail, userName, password, password2;
    TextView t_v_Login;
    Spinner spinner;
    String dep;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_registration);

        spinner=findViewById(R.id.spinner_l);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.department,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        register=findViewById(R.id.l_register);

        t_v_Login=findViewById(R.id.t_v_l_already_reg);

        ETname=findViewById(R.id.l_e_t_name);
        ETindex=findViewById(R.id.l_e_t_index);
        mail=findViewById(R.id.l_e_t_email);
        userName=findViewById(R.id.l_e_t_username);
        password=findViewById(R.id.l_e_t_password);
        password2=findViewById(R.id.l_e_t_password2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        t_v_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LectureLogin.class));
                //finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position > 0) {
            dep=parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), dep, Toast.LENGTH_SHORT).show();
        }
        if (position==0){
            ((TextView) view).setTextColor(ContextCompat.getColor(this,R.color.textGray));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void registerUser(){
        final String shortname=ETname.getText().toString().trim();
        final String index_no=ETindex.getText().toString();
        //final int batch=Integer.parseInt(ETbatch.getText().toString());
        final String email=mail.getText().toString().trim();
        final String username=userName.getText().toString().trim();
        final String pwd=password.getText().toString().trim();
        //final String dep=spinner.getSelectedItem().toString();
        String type="register";

        if(TextUtils.isEmpty(shortname) || TextUtils.isEmpty(index_no) || TextUtils.isEmpty(dep) || TextUtils.isEmpty(email) || TextUtils.isEmpty(username) ||TextUtils.isEmpty(pwd)) {
            Toast.makeText(LectureRegistration.this, "some field not filled", Toast.LENGTH_SHORT).show();
        } else {
                    /*progressDialog.setMessage("Registering User....");
                    progressDialog.show();*/
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constant.URL_L_REGISTER,
                   // "http://192.168.43.20/LectureSS/Android/LectureRegister.php",

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressDialog.dismiss();
                            /*this is the JSONobject {"error":true,"message":"Invalid Requst"} to show this we create JSONobject
                             * in here "error" is the key & "true" is value. same message & Invalid request*/
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if (!jsonObject.getBoolean("error")){
                                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), LectureLogin.class));
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //when use this "progressDialog.hide();" app was crashed
                    // progressDialog.hide();
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params=new HashMap<>();
                    params.put("name",shortname);
                    params.put("index",index_no);
                    params.put("dep",dep);
                    params.put("email",email);
                    // params.put("dep",dep);
                    params.put("username",username);
                    params.put("password",pwd);
                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/



        }


    }
}
