package com.desirecode.lectureschedulingapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.desirecode.lectureschedulingapp.timetable.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class TempTable extends AppCompatActivity {

    ArrayList<String> tableArray;
    TableLayout tl;
    TableRow tr;
    TextView tv,tv0,tv1,tv2,tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_table);

        tl = findViewById(R.id.table1);
        tr = findViewById(R.id.tableRow);
        tableArray = new ArrayList();

        timeTable();
    }


    public void timeTable() {
        //final String index=SharedPrefManager.getInstance(this).getIndex();
        //final String index="";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                //Constant.URL_TIMETABLE,
                "http://192.168.43.20/LectureSS/Android/timetable3.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);


                                for (int i = 0; i < array.length(); i++) {

                                   JSONObject object = array.getJSONObject(i);

                                    //            Toast.makeText(getApplicationContext()," For FOR.",Toast.LENGTH_SHORT).show();

                                /*String day = object.getString("day");
                                String t1 = object.getString("t1");
                                String t2 = object.getString("t2");
                                String t3 = object.getString("t3");
                                String t4 = object.getString("t4");
                                String t5 = object.getString("t5");
                                String t6 = object.getString("t6");
                                String t7 = object.getString("t7");
                                String t8 = object.getString("t8");
                                String t9 = object.getString("t9");
                                String t10 = object.getString("t10");*/

                                    tableArray.add(object.getString("day"));
                                    tableArray.add( object.getString("t1"));
                                    tableArray.add( object.getString("t2"));
                                    tableArray.add( object.getString("t3"));
                                    tableArray.add( object.getString("t4"));
                                    tableArray.add( object.getString("t5"));
                                    tableArray.add( object.getString("t6"));
                                    tableArray.add( object.getString("t7"));
                                    tableArray.add( object.getString("t8"));
                                    tableArray.add( object.getString("t9"));
                                    tableArray.add(object.getString("t10"));

                                /*TimeTableGetter resultGetter = new TimeTableGetter(day, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
                                timeTableList.add(resultGetter);*/

                                    //Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                                }
                                table();

                            //not true object.getBoolean("error") means user success fully authenticate

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(this,"Still not set that Feature",Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_SHORT).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ) {
            //pass the username and password to stringRequest bellow
            //For that we override the method get params
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("timetable", "ict1617");
               // params.put("day","monday" );
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        //Volley.newRequestQueue(this).add(stringRequest);

        //whatever can use through above two
    }

    public void table(){
        String array=tableArray.toString();

        tr= new TableRow(this);
        tv0=new TextView(this);
        tv0.setText("day");
        tr.addView(tv0);

        tv1=new TextView(this);
        tv1.setText("dayjhg");
        tr.addView(tv1);
        tl.addView(tr);

        for (int i=0; i<array.length(); i++){
            tv = new TextView(this);
            tv.setPadding(10,10,10, 10);
            tv.setTextColor(Color.RED);tv.setTextSize(25);
            tv.setText(array);
            tr=new TableRow(this);
            tr.addView(tv);

            tl.addView(tr);
        }

    }
}