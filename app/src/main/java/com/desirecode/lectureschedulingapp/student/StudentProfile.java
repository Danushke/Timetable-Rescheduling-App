package com.desirecode.lectureschedulingapp.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desirecode.lectureschedulingapp.CommonLogin;
import com.desirecode.lectureschedulingapp.R;
import com.desirecode.lectureschedulingapp.SharedPrefManager;
import com.desirecode.lectureschedulingapp.timetable.TimeTableOverView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class StudentProfile extends AppCompatActivity { //implements View.OnClickListener

    TextView greeting;
    TextView mon,tues,wed,thurs,fri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        greeting=findViewById(R.id.t_v_s_welcome);
        mon=findViewById(R.id.monday);
        tues=findViewById(R.id.tuesday);

        greeting.setText(SharedPrefManager.getInstance(this).getUsername());

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, StudentLogin.class));
        }

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), TimeTableOverView.class);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), TimeTableOverView.class).putExtra("DAY","monday"));
            }
        });

        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(getApplicationContext(), TimeTableOverView.class);
                startActivity(intent);*/
                startActivity(new Intent(getApplicationContext(),TimeTableOverView.class).putExtra("DAY","tuesday"));
            }
        });
    }

    /*public void onClick (View view){
        if (view==mon){
            Intent intent=new Intent(this, TimeTableOverView.class);
            startActivity(intent);
            //startActivity(new Intent(getApplication(),LectureRegistration.class));
        }
    }*/





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, CommonLogin.class));
                break;
            case R.id.menuSetting:
                Toast.makeText(this,"Still not set that Feature",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
        //switch (item.getItemId()){}

    }


}
