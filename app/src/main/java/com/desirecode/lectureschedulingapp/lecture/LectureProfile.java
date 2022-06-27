package com.desirecode.lectureschedulingapp.lecture;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.desirecode.lectureschedulingapp.CommonLogin;
import com.desirecode.lectureschedulingapp.R;
import com.desirecode.lectureschedulingapp.SharedPrefManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LectureProfile extends AppCompatActivity {

    TextView greeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_profile);

        greeting=findViewById(R.id.t_v_l_welcome);

        greeting.setText(SharedPrefManager.getInstance(this).getUsername());
    }

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
