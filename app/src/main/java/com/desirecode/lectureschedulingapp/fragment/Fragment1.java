package com.desirecode.lectureschedulingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.desirecode.lectureschedulingapp.Constant;
import com.desirecode.lectureschedulingapp.R;
import com.desirecode.lectureschedulingapp.SharedPrefManager;
import com.desirecode.lectureschedulingapp.timetable.FragmentTimeTableAdapter;
import com.desirecode.lectureschedulingapp.timetable.RequestHandler;
import com.desirecode.lectureschedulingapp.timetable.TimeTableGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment1 extends Fragment {

    Context context,context2;
    String table, day;
    List<TimeTableGetter> timeTableList;
    FragmentTimeTableAdapter adapter; //@@@@@@@@@@
    RecyclerView recyclerView;
    TextView textViewNotityMsg,user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context2=inflater.getContext();



        /**new AsyncConnectionChecker(getContext(), new AsyncConnectionChecker.OnConnectionCallback() {

            @Override
            public void onConnectionSuccess() {
                Toast.makeText(context, "onSuccess()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConnectionFail(String msg) {
                Toast.makeText(context, "onFail()", Toast.LENGTH_SHORT).show();
            }
        }).execute();*/

        timeTableList = new ArrayList<>();
        View view=inflater.inflate(R.layout.activity_fragment1, container, false);
        recyclerView=view.findViewById(R.id.recycler2);
        //recyclerView = findViewById(R.id.recycler);//@@@@@@@@
        recyclerView.setHasFixedSize(true);//@@@@@@@@

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        textViewNotityMsg=view.findViewById(R.id.notification_message);
        user=view.findViewById(R.id.t_v_name);
        user.setText("Welcome\n"+String.valueOf(SharedPrefManager.getInstance(getContext()).getUsername()));
        table = String.valueOf(SharedPrefManager.getInstance(getContext()).getTable());
        //day = getIntent().getStringExtra("DAY");

        timeTable();
        return view;
        //return inflater.inflate(R.layout.activity_fragment1, container, false);
    }

    public void timeTable() {
        //final String index=SharedPrefManager.getInstance(this).getIndex();
        //final String index="";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.URL_TIMETABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            //not true object.getBoolean("error") means user success fully authenticate
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);

                                /*String d= String.valueOf(i);
                                            Toast.makeText(getContext(),d,Toast.LENGTH_SHORT).show();*/

                                String day = object.getString("day");
                                String t1 = object.getString("t1");
                                String t2 = object.getString("t2");
                                String t3 = object.getString("t3");
                                String t4 = object.getString("t4");
                                String t5 = object.getString("t5");
                                String t6 = object.getString("t6");
                                String t7 = object.getString("t7");
                                String t8 = object.getString("t8");
                                String t9 = object.getString("t9");
                                String t10 = object.getString("t10");

                                TimeTableGetter resultGetter = new TimeTableGetter(day, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
                                //TimeTableGetter resultGetter = new TimeTableGetter("day", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9", "t10");
                                timeTableList.add(resultGetter);

                                //Toast.makeText(getContext(),("end of for"),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext().getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(getContext(),("message1"),Toast.LENGTH_SHORT).show();
                            adapter = new FragmentTimeTableAdapter(getContext(), timeTableList); //@@@@@@@@
                            //Toast.makeText(getContext(),("message2"),Toast.LENGTH_SHORT).show();

                            recyclerView.setAdapter(adapter);//@@@@@@@@
                            //Toast.makeText(getContext(),("message3"),Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(this,"Still not set that Feature",Toast.LENGTH_LONG).show();
                            Toast.makeText(getContext(), "Catch", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(getContext(),"Error Response",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext()," For FOR.",Toast.LENGTH_SHORT).show();

                        Toast.makeText(
                                getContext(),
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
                params.put("timetable",table);
                //params.put("day",day);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
        //Volley.newRequestQueue(this).add(stringRequest);

        //whatever can use through above two*/
    }
}




/*    ActionListener actionListener=new ActionListener();

    class ActionListener extends AppCompatActivity {
        String table, day;
        List<TimeTableGetter> timeTableList;
        TimeTableAdapter adapter; //@@@@@@@@@@
        RecyclerView recyclerView;
        TextView textView;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fragment1);

            timeTableList = new ArrayList<>();
            recyclerView = findViewById(R.id.recycler);//@@@@@@@@
            recyclerView.setHasFixedSize(true);//@@@@@@@@
            //recyclerView.setLayoutManager(new LinearLayoutManager(this));//@@@@@@@@   normally we can use for vertical recycler view (Default view is vertical)

            // Horizontal recycler view start
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            //recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            // Horizontal recycler view end

            table = String.valueOf(SharedPrefManager.getInstance(this).getTable());
            day = getIntent().getStringExtra("DAY");


        }
        public void timeTable1(){
            String s="fds";

            StringRequest stringRequest=new StringRequest(
                    Request.Method.POST,
                    Constant.URL_TABLE_UPDATE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("timetable", table);
                    //params.put("day",day);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }

        public void timeTable() {
            //final String index=SharedPrefManager.getInstance(this).getIndex();
            //final String index="";
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constant.URL_TIMETABLE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);

                                //not true object.getBoolean("error") means user success fully authenticate
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject object = array.getJSONObject(i);

                                    //            Toast.makeText(getApplicationContext()," For FOR.",Toast.LENGTH_SHORT).show();

                                    String day = object.getString("day");
                                    String t1 = object.getString("t1");
                                    String t2 = object.getString("t2");
                                    String t3 = object.getString("t3");
                                    String t4 = object.getString("t4");
                                    String t5 = object.getString("t5");
                                    String t6 = object.getString("t6");
                                    String t7 = object.getString("t7");
                                    String t8 = object.getString("t8");
                                    String t9 = object.getString("t9");
                                    String t10 = object.getString("t10");

                                    TimeTableGetter resultGetter = new TimeTableGetter(day, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
                                    timeTableList.add(resultGetter);

                                    //Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                                }
                                adapter = new TimeTableAdapter(getApplicationContext(), timeTableList); //@@@@@@@@
                                recyclerView.setAdapter(adapter);//@@@@@@@@
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
                    params.put("timetable", table);
                    //params.put("day",day);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
            //Volley.newRequestQueue(this).add(stringRequest);

            //whatever can use through above two
        }
    }
} */
