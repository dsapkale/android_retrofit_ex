package com.pralee.restapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void getFirstPost(View view){
        Toast.makeText(this, "Getting First Post", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestPosts restPosts = retrofit.create(RestPosts.class);
        Call<Posts> res = restPosts.getFirstPost();


       res.enqueue(new Callback<Posts>() {
           @Override
           public void onResponse(Call<Posts> call, Response<Posts> response) {
               Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailure(Call<Posts> call, Throwable t) {
               Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();

           }
       });

    }
    public void getAllPosts(View view) {
        Toast.makeText(this, "Getting All Posts", Toast.LENGTH_SHORT).show();
    }
    public void savePost(View view) {
        Toast.makeText(this, "Saving Post", Toast.LENGTH_SHORT).show();
    }
    public void deletePost(View view) {
        Toast.makeText(this, "Deleting Post", Toast.LENGTH_SHORT).show();
    }
    public void updatePost(View view) {
        Toast.makeText(this, "Updating Post", Toast.LENGTH_SHORT).show();

    }

}