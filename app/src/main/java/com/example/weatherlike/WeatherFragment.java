package com.example.weatherlike;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeatherFragment extends Fragment {

    private TextView tempTextView;
    private TextView cityNameTextView;
    private TextView dateTextView;
    private TextView weatherDescriptionTextView;
    private TextView tempFeelTextView;
    private RadioButton celsiusRadioButton;
    private RadioButton farRadioButton;
    private TextView tempIcon1TextView;
    private TextView tempIcon2TextView;
    private EditText cityNameEditText;
    private ImageButton searchButton;
    private TextView weatherLikeText;
    private RadioGroup radioGroup;
    private TextView currentDayTextView;

    private RequestQueue requestQueue;


    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false);

        tempTextView = view.findViewById(R.id.text_view_temperature);
        cityNameTextView = view.findViewById(R.id.text_view_location);
        dateTextView = view.findViewById(R.id.current_date);
        weatherDescriptionTextView = view.findViewById(R.id.weather_description);
        tempFeelTextView = view.findViewById(R.id.feeling_temp);
        celsiusRadioButton = view.findViewById(R.id.celsius_radio_button);
        farRadioButton = view.findViewById(R.id.far_radio_button);
        tempIcon1TextView = view.findViewById(R.id.temp_icon1);
        tempIcon2TextView = view.findViewById(R.id.temp_icon2);
        cityNameEditText = view.findViewById(R.id.city_name);
        searchButton = view.findViewById(R.id.search_city);
        weatherLikeText = view.findViewById(R.id.weather_like_text);
        radioGroup = view.findViewById(R.id.radio_group);
        currentDayTextView = view.findViewById(R.id.current_day);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCity();
                celsiusRadioButton.setChecked(true);
                farRadioButton.setChecked(false);
                tempIcon1TextView.setText("°C");
                tempIcon2TextView.setText("°C");

                weatherLikeText.setVisibility(View.INVISIBLE);
                tempTextView.setVisibility(View.VISIBLE);
                cityNameTextView.setVisibility(View.VISIBLE);
                currentDayTextView.setVisibility(View.VISIBLE);
                dateTextView.setVisibility(View.VISIBLE);
                weatherDescriptionTextView.setVisibility(View.VISIBLE);
                tempFeelTextView.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                tempIcon1TextView.setVisibility(View.VISIBLE);
                tempIcon2TextView.setVisibility(View.VISIBLE);

            }
        });

        farRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(farRadioButton.isChecked()){
                    farWeather();
                    tempIcon1TextView.setText("°F");
                    tempIcon2TextView.setText("°F");
                }else {
                    celsiusWeather();
                }
            }
        });

        celsiusRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(celsiusRadioButton.isChecked()){
                    celsiusWeather();
                    tempIcon1TextView.setText("°C");
                    tempIcon2TextView.setText("°C");
                }else {
                    farWeather();
                }
            }
        });

        return view;

    }

    public void celsiusWeather() {

        requestQueue = Volley.newRequestQueue(getActivity());

        String cityName = cityNameEditText.getText().toString();

        final String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=083b1de5e80e0d1b379e0fd0ff72e8dd";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(JSONObject response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject main_object = response.getJSONObject("main");
                        JSONArray array = response.getJSONArray("weather");
                        JSONObject object = array.getJSONObject(0);
                        String description = object.getString("description");
                        String temp = String.valueOf(main_object.getInt("temp"));
                        String tempFeel = String.valueOf(main_object.getInt("feels_like"));
                        String city = response.getString("name");

                        tempTextView.setText(temp);
                        cityNameTextView.setText(city);
                        weatherDescriptionTextView.setText(description);
                        tempFeelTextView.setText("Feels Like: " +tempFeel);

                        Calendar calendar = Calendar.getInstance();
                        Date currentDate = calendar.getTime();

                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                .format(currentDate.getTime());

                        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(currentDate.getTime());

                        currentDayTextView.setText(currentDay);
                        dateTextView.setText(date);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    public void farWeather(){

        requestQueue = Volley.newRequestQueue(getActivity());

        String cityName = cityNameEditText.getText().toString();

        final String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=imperial&appid=083b1de5e80e0d1b379e0fd0ff72e8dd";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(JSONObject response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject main_object = response.getJSONObject("main");
                        JSONArray array = response.getJSONArray("weather");
                        JSONObject object = array.getJSONObject(0);
                        String description = object.getString("description");
                        String temp = String.valueOf(main_object.getInt("temp"));
                        String tempFeel = String.valueOf(main_object.getInt("feels_like"));
                        String city = response.getString("name");

                        tempTextView.setText(temp);
                        cityNameTextView.setText(city);
                        weatherDescriptionTextView.setText(description);
                        tempFeelTextView.setText("Feels Like: " +tempFeel);

                        Calendar calendar = Calendar.getInstance();
                        Date currentDate = calendar.getTime();

                        String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
                                .format(currentDate.getTime());

                        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(currentDate.getTime());

                        currentDayTextView.setText(currentDay);
                        dateTextView.setText(date);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void searchCity(){

        requestQueue = Volley.newRequestQueue(getActivity());

        String cityName = cityNameEditText.getText().toString();

        final String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=083b1de5e80e0d1b379e0fd0ff72e8dd";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(JSONObject response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject main_object = response.getJSONObject("main");
                        JSONArray array = response.getJSONArray("weather");
                        JSONObject object = array.getJSONObject(0);
                        String description = object.getString("description");
                        String temp = String.valueOf(main_object.getInt("temp"));
                        String tempFeel = String.valueOf(main_object.getInt("feels_like"));
                        String city = response.getString("name");

                        tempTextView.setText(temp);
                        cityNameTextView.setText(city);
                        weatherDescriptionTextView.setText(description);
                        tempFeelTextView.setText("Feels Like: " +tempFeel);

                        Calendar calendar = Calendar.getInstance();
                        Date currentDate = calendar.getTime();

                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                .format(calendar.getTime());

                        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(currentDate.getTime());

                        currentDayTextView.setText(currentDay);
                        dateTextView.setText(date);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);


    }
}
