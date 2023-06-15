package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle, tvValue;
    ImageView imgProfilePic;

//    ImageButton []imageButtons;

    ImageButton imgName, imgEmail, imgDob, imgAdd, imgContact, imgPass;

    String []imgButtonNames={"imgName", "imgEmail", "imgDob", "imgContact", "imgLocation", "imgPassword"};

    String name="Invalid", email="Invalid", dob="--/--/----", phone="Invalid", location="Invalid", password="Invalid";

    int green, grey3;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle=findViewById(R.id.tvHeading);
        tvValue=findViewById(R.id.tvValue);
        imgProfilePic=findViewById(R.id.imageView);
        imgName=findViewById(R.id.imgName);
        imgEmail=findViewById(R.id.imgEmail);
        imgDob=findViewById(R.id.imgDob);
        imgContact=findViewById(R.id.imgContact);
        imgAdd=findViewById(R.id.imgLocation);
        imgPass=findViewById(R.id.imgPassword);

        green= ContextCompat.getColor(getApplicationContext(), R.color.green);
        grey3= ContextCompat.getColor(getApplicationContext(), R.color.grey3);

        gettingUserInfo();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            gettingUserInfo();
            new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false),1000);
        });

        imgName.setOnClickListener(view -> {
            tvTitle.setText("Hi, My name is");
            tvValue.setText(name);
            buttonPressed("imgName");
        });

        imgEmail.setOnClickListener(view -> {
//                Toast.makeText(MainActivity.this, "Email Clicked", Toast.LENGTH_SHORT).show();
            tvTitle.setText("My email address is");
            tvValue.setText(email);

            buttonPressed("imgEmail");
        });

        imgDob.setOnClickListener(view -> {
            tvTitle.setText("My birthday is");
            tvValue.setText(dob);
            buttonPressed("imgDob");
        });

        imgAdd.setOnClickListener(view -> {
            tvTitle.setText("My address is");
            tvValue.setText(location);
            buttonPressed("imgLocation");
        });

        imgContact.setOnClickListener(view -> {
            tvTitle.setText("My phone number is");
            tvValue.setText(phone);
            buttonPressed("imgContact");
        });

        imgPass.setOnClickListener(view -> {
            tvTitle.setText("My password is");
            tvValue.setText(password);
            buttonPressed("imgPassword");
        });

    }

    public void gettingUserInfo(){
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                "https://randomuser.me/api/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject currUserInfo=response.getJSONArray("results").getJSONObject(0);
                    name=currUserInfo.getJSONObject("name").getString("title")+" "+currUserInfo.getJSONObject("name").getString("first")+" "+currUserInfo.getJSONObject("name").getString("last");

                    tvTitle.setText("Hi, My name is");
                    tvValue.setText(name);

                    String url=currUserInfo.getJSONObject("picture").getString("large");
                    Picasso.get().load(url).into(imgProfilePic);

                    buttonPressed("imgName");

                    email=currUserInfo.getString("email");
                    String Dob=currUserInfo.getJSONObject("dob").getString("date").substring(0,10);
                    dob=Dob.substring(8,10)+"/"+Dob.substring(5,7)+"/"+Dob.substring(0,4);

                    phone=currUserInfo.getString("phone");

                    password=currUserInfo.getJSONObject("login").getString("password");

                    location=currUserInfo.getJSONObject("location").getJSONObject("street").getInt("number")+" "+currUserInfo.getJSONObject("location").getJSONObject("street").getString("name");

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Something went wrong2", Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);
    }

    public void buttonPressed(String s){
        for (String name:imgButtonNames){
            Log.d("abc","pressed "+name);
            int resID = getResources().getIdentifier(name,
                    "id", getPackageName());
            ImageButton btn=findViewById(resID);
            if (name==s){
                btn.setColorFilter(green);
            }
            else{
                btn.setColorFilter(grey3);
            }
        }
    }

}