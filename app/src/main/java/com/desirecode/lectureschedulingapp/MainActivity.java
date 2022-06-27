package com.desirecode.lectureschedulingapp;

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
import com.desirecode.lectureschedulingapp.lecture.LectureLogin;
import com.desirecode.lectureschedulingapp.lecture.LectureRegistration;
import com.desirecode.lectureschedulingapp.lecture.TabbedActivity;
import com.desirecode.lectureschedulingapp.student.StudentLogin;
import com.desirecode.lectureschedulingapp.student.StudentRegistration;
import com.desirecode.lectureschedulingapp.timetable.RequestHandler;
import com.desirecode.lectureschedulingapp.timetable.TimeTableOverView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener{

    Button lecture_btn,student_btn,non_acd_btn,ar_btn;

    private EditText ETname, ETindex, mail, userName, password, password2;
    TextView t_v_Login;
    Spinner spinner, spinner_role;
    String dep;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

       /* lecture_btn=findViewById(R.id.lecture);
        student_btn=findViewById(R.id.student);
        non_acd_btn=findViewById(R.id.non_acd);
        ar_btn=findViewById(R.id.ar);

        lecture_btn.setOnClickListener(this);
        student_btn.setOnClickListener(this);

        if(SharedPrefManager.getInstance(this).isStudentLoggedIn()){
            finish();
            startActivity(new Intent(this, StudentProfile.class));
            return;
        }*/


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            if (SharedPrefManager.getInstance(this).getUser().equals("Lecture")){
                finish();
                startActivity(new Intent(this, TabbedActivity.class));
                return;

            }else if(SharedPrefManager.getInstance(this).getUser().equals("Student")){
                finish();
                startActivity(new Intent(this, TimeTableOverView.class));
                return;
            }


        }


       //###########################################################################################################################

        spinner=findViewById(R.id.spinner_l);
        spinner_role=findViewById(R.id.spinner_role);

   /*     String[] value={"1","2","3","4","5"};
        ArrayList<String> arrayList=new ArrayList<>(Arrays.asList(value));
        //ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,spinner,arrayList);
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        String[] value2={"a","b","c","d","e"};
        ArrayList<String> arrayList2=new ArrayList<>(Arrays.asList(value2));
        //ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,spinner,arrayList);
        ArrayAdapter<String> arrayAdapter2= new ArrayAdapter<>(this,R.layout.style_spinner,arrayList2);
        spinner_role.setAdapter(arrayAdapter2);
        spinner_role.setOnItemSelectedListener(this);*/


        // spinner items selection type
        /*int spinner1=spinner.getSelectedItemPosition();
        String[] asd=getResources().getStringArray(R.array.department);
        int size = Integer.valueOf(asd[spinner1]);*/


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.department,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.job_role,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_role.setAdapter(adapter2);
        spinner_role.setOnItemSelectedListener(this);


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

                int s=spinner_role.getSelectedItemPosition();


                if(s==2){
                    lectureRegisterUser();
                }else if(s==1){
                    studentRegisterUser();
                }else if(s==3){

                }
               //studentRegisterUser();

            }
        });

        t_v_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CommonLogin.class));
                //finish();
            }
        });


    }





    public void onClick (View view){



        if (view==lecture_btn){
            /*Intent intent=new Intent(this, LectureRegistration.class);
            startActivity(intent);*/
            //startActivity(new Intent(getApplication(),LectureRegistration.class));
            startActivity(new Intent(getApplicationContext(),LectureRegistration.class));
        }
        else if (view==student_btn){
            startActivity(new Intent(getApplication(), StudentRegistration.class));
        }
        else if (view == non_acd_btn){

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //spinner= (Spinner) parent;
        //spinner_role= (Spinner) parent;
        if (position > 0) {
            dep=spinner.getSelectedItem().toString();

            String spin =parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), spin, Toast.LENGTH_SHORT).show();

            //int spinner_r_p = (int) spinner_role.getId();
            int s=spinner_role.getSelectedItemPosition();
            String spinner_r_p= spinner_role.getSelectedItem().toString();


            switch (spinner_r_p){
                case "Student":
                    ETindex.setHint("Your Batch \n 1617");
                    spinner.setVisibility(View.VISIBLE);
                    break;

                case "Lecture":
                    ETindex.setHint("Employee ID");
                    spinner.setVisibility(View.VISIBLE);
                    break;

                case "Non A S":
                    spinner.setVisibility(View.INVISIBLE);
                    ETindex.setHint("Employee  ID");
                    break;
                default:
                    //spinner.setVisibility(View.VISIBLE);

            }



        }
        if (position==0){
            ((TextView) view).setTextColor(ContextCompat.getColor(this,R.color.textGray));
            //((TextView) view).setText("");
            //spinner.setPrompt("jfhfh");

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void lectureRegisterUser(){
        final String shortname=ETname.getText().toString().trim();
        final String index_no=ETindex.getText().toString().trim();
        //final int batch=Integer.parseInt(ETbatch.getText().toString());
        final String email=mail.getText().toString().trim();
        final String username=userName.getText().toString().trim();
        final String pwd=password.getText().toString().trim();
        //final String dep=spinner.getSelectedItem().toString();
        String type="register";

        if(TextUtils.isEmpty(shortname) || TextUtils.isEmpty(index_no) || TextUtils.isEmpty(dep) || TextUtils.isEmpty(email) || TextUtils.isEmpty(username) ||TextUtils.isEmpty(pwd)) {
            Toast.makeText(MainActivity.this, "some field are not set", Toast.LENGTH_SHORT).show();
            ETindex.setError("asdsad");
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


    private void studentRegisterUser(){
        final String shortname=ETname.getText().toString().trim();
        final String batch=ETindex.getText().toString().trim();
        //final int batch=Integer.parseInt(ETbatch.getText().toString());
        final String email=mail.getText().toString().trim();
        final String username=userName.getText().toString().trim();
        final String pwd=password.getText().toString().trim();
        //final String dep=spinner.getSelectedItem().toString();
        String type="register";

        if(TextUtils.isEmpty(shortname) || TextUtils.isEmpty(batch) || TextUtils.isEmpty(dep) || TextUtils.isEmpty(email) || TextUtils.isEmpty(username) ||TextUtils.isEmpty(pwd)) {
            Toast.makeText(MainActivity.this, "some field not filled", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isDigitsOnly(batch)&&ETindex.getText().length()==4){
                    /*progressDialog.setMessage("Registering User....");
                    progressDialog.show();*/
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constant.URL_S_REGISTER,
                    //"http://192.168.43.20/LectureSS/Android/StudentRegister.php",

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
                                    startActivity(new Intent(getApplicationContext(), StudentLogin.class));
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
                    params.put("batch",batch);
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



        }else {
            Toast.makeText(getApplicationContext(),"index number should be integer",Toast.LENGTH_LONG).show();
        }


    }

}
