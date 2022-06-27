package com.desirecode.lectureschedulingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.desirecode.lectureschedulingapp.Connections.ConnectionChecker;
import com.desirecode.lectureschedulingapp.Constant;
import com.desirecode.lectureschedulingapp.R;
import com.desirecode.lectureschedulingapp.timetable.FragmentTimeTableAdapter;
import com.desirecode.lectureschedulingapp.timetable.RequestHandler;
import com.desirecode.lectureschedulingapp.timetable.TimeTableGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment2 extends Fragment {
    Context context;
    List<TimeTableGetter> timeTableList;
    FragmentTimeTableAdapter adapter; //@@@@@@@@@@
    RecyclerView recyclerView;
    Spinner spinner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (ConnectionChecker.checkConnectivity(getContext())){
            Toast.makeText(getContext(),"Connection OK",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getContext(),"Not Connection",Toast.LENGTH_SHORT).show();
        }




        timeTableList = new ArrayList<>();
        View view=inflater.inflate(R.layout.activity_fragment2, container, false);
        recyclerView=view.findViewById(R.id.recycler2);
        //recyclerView = findViewById(R.id.recycler);//@@@@@@@@
        recyclerView.setHasFixedSize(true);//@@@@@@@@

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        spinner=view.findViewById(R.id.spinnerDay);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.day,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new DaySpinnerClass());

        String table= (String) DateFormat.format("EEEE", Calendar.getInstance().getTime());
        //table = String.valueOf(SharedPrefManager.getInstance(getContext()).getTable());
        //day = getIntent().getStringExtra("DAY");

        timeTable(table);
        return view;
        //return inflater.inflate(R.layout.activity_fragment1, container, false);
    }

    public void timeTable(final String table) {
        timeTableList.clear();

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
                            }
                            adapter = new FragmentTimeTableAdapter(getContext(), timeTableList); //@@@@@@@@
                            recyclerView.setAdapter(adapter);//@@@@@@@@
                            //adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(this,"Still not set that Feature",Toast.LENGTH_LONG).show();
                            Toast.makeText(getContext(),"Catch", Toast.LENGTH_SHORT).show();

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


        class DaySpinnerClass implements AdapterView.OnItemSelectedListener{

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //spinner= (Spinner) parent;
                //spinner_role= (Spinner) parent;
                int d=parent.getSelectedItemPosition();
                if (position > 0) {

                    String spin =parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), spin, Toast.LENGTH_SHORT).show();



                    switch (spin){
                        case "Monday":
                            timeTable("Monday");
                            break;

                        case "Tuesday":
                            timeTable("Tuesday");
                            break;

                        case "Wednesday":
                            timeTable("Wednesday");
                            break;
                        case "Thursday":
                            timeTable("Thursday");
                            break;
                        case "Friday":
                            timeTable("Friday");
                            break;
                        default:
                            //spinner.setVisibility(View.VISIBLE);

                    }
                    if (position==0){
                        ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(),R.color.textGray));
                        //((TextView) view).setText("");
                        //spinner.setPrompt("jfhfh");
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }

    }


class Action extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2);
    }
}
