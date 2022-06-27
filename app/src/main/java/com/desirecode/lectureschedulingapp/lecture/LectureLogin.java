package com.desirecode.lectureschedulingapp.lecture;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.desirecode.lectureschedulingapp.Constant;
import com.desirecode.lectureschedulingapp.R;
import com.desirecode.lectureschedulingapp.SharedPrefManager;
import com.desirecode.lectureschedulingapp.timetable.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class LectureLogin extends AppCompatActivity {

    EditText user_name,password;
    Button l_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_login);


        user_name=findViewById(R.id.e_t_l_username);
        password=findViewById(R.id.e_t_l_password);
        l_login=findViewById(R.id.l_login_btn);

        l_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonLogin();
            }
        });
    }

    private void commonLogin(){

        final String username=user_name.getText().toString().trim();
        final String pwd=password.getText().toString().trim();
        //final String dep=spinner.getSelectedItem().toString();
        String type="register";

        if( TextUtils.isEmpty(username) ||TextUtils.isEmpty(pwd)) {
            Toast.makeText(LectureLogin.this, "some field not filled", Toast.LENGTH_SHORT).show();
        } else {
                    /*progressDialog.setMessage("Registering User....");
                    progressDialog.show();*/
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constant.URL_COM_LOGIN,
                    //"http://192.168.43.20/LectureSS/Android/LectureLogin.php",

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressDialog.dismiss();
                            /*this is the JSONobject {"error":true,"message":"Invalid Requst"} to show this we create JSONobject
                             * in here "error" is the key & "true" is value. same message & Invalid request*/
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if (!jsonObject.getBoolean("error")){
                                    //store the user data to shared preference
                                    SharedPrefManager.getInstance(getApplicationContext())
                                            .lecLogin(
                                                    //in here should equal the parsing variables with php response

                                                    jsonObject.getString("name"),
                                                    jsonObject.getString("table"),
                                                    jsonObject.getString("user")
                                            );
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "User login success",
                                            Toast.LENGTH_LONG
                                    ).show();

                                    startActivity(new Intent(getApplicationContext(), LectureProfile.class));
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
