package com.pralee.restapi;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText title, userid, postId, message;
    ListView postListView;
    List<Posts> postsForList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    title = findViewById(R.id.titleTextId);
    userid = findViewById(R.id.useridTextId);
    postId = findViewById(R.id.idTextId);
    message = findViewById(R.id.bodyTextId);
    postListView = findViewById(R.id.postListId);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private RestPosts retrofiteWork(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RestPosts.class);
    }

    public void getFirstPost(View view){
        Toast.makeText(this, "Getting First Post", Toast.LENGTH_SHORT).show();
//        Retrofit retrofit = new Retrofit
//                .Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RestPosts restPosts = retrofit.create(RestPosts.class);

        RestPosts restPosts = retrofiteWork();
        Call<Posts> res = restPosts.getFirstPost();

        res.enqueue(new Callback<Posts>() {
           @Override
           public void onResponse(Call<Posts> call, Response<Posts> response) {
               if(response.isSuccessful()){
                   Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                   Posts post = response.body();
                   title.setText(post.title());
                   userid.setText(""+post.userId());
                   postId.setText(""+post.id());
                   message.setText(post.body());
               }


           }

           @Override
           public void onFailure(Call<Posts> call, Throwable t) {
               Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();

           }
       });

    }
    public void getAllPosts(View view) {
        Toast.makeText(this, "Getting All Posts", Toast.LENGTH_SHORT).show();
        RestPosts restPosts = retrofiteWork();
        Call<List<Posts>> res = restPosts.getAllPosts();
        res.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    var dataFromServer = response.body();
                    postsForList.addAll(dataFromServer);
                    List<String> titles = new ArrayList<>();
                    for (Posts post : postsForList) {
                        titles.add(post.title());
                    }
                    postListView.setAdapter(
                            new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            titles
                    ));
                }else{
                    Toast.makeText(MainActivity.this, "Fetching problem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {

            }
        });



    }
    public void savePost(View view) {
        Toast.makeText(this, "Saving Post", Toast.LENGTH_SHORT).show();
        RestPosts restPosts = retrofiteWork();
        Posts post = new Posts(
                Integer.parseInt(userid.getText().toString()),
                Integer.parseInt(postId.getText().toString()),
                title.getText().toString(),
                message.getText().toString()
        );
        Call<Posts> res = restPosts.savePost(post);
        res.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Posts post = response.body();
                    postsForList.add(post); // adding new post from server to existing list data
                    List<String> titles = new ArrayList<>();
                    for (Posts post1 : postsForList) {
                        titles.add(post1.title()); // updating list for new titles
                    }
                    // setting new data into the list view
                    postListView.setAdapter(
                            new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    titles
                            ));


                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        });


    }
    public void deletePost(View view) {
        Toast.makeText(this, "Deleting Post", Toast.LENGTH_SHORT).show();
        RestPosts restPosts = retrofiteWork();
        int id = Integer.parseInt(postId.getText().toString());
        Call<Void> res = restPosts.deletePost(id);

        res.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                List<String> titles = new ArrayList<>();
                for (Posts p: postsForList) {
                    if(p.id()==id){
                        postsForList.remove(p);
                        break;
                    }
                } // removing post from list
                for (Posts post1 : postsForList) {

                    titles.add(post1.title()); // updating list for new titles
                }
                // setting new data into the list view
                postListView.setAdapter(
                        new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                titles
                        ));

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
    public void updatePost(View view) {
        Toast.makeText(this, "Updating Post", Toast.LENGTH_SHORT).show();
        RestPosts restPosts = retrofiteWork();
        int id = Integer.parseInt(postId.getText().toString());
        Posts post = new Posts(
                Integer.parseInt(userid.getText().toString()),
                Integer.parseInt(postId.getText().toString()),
                title.getText().toString(),
                message.getText().toString()
        );
        Call<Posts> res = restPosts.updatePost(id, post);
        res.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                List<String> titles = new ArrayList<>();
                for (int i=0; i<postsForList.size(); i++)  {
                    var p = postsForList.get(i); // getting post from local list by index
                    if(p.id()==id){ // checking which one is updated
                        Posts updatedPost = response.body(); // getting server pos
                        postsForList.remove(p); // remove local post
                        postsForList.add(i, updatedPost); // update new post at same index
                        break;
                    }
                } // removing post from list
                for (Posts post1 : postsForList) {
                    titles.add(post1.title()); // updating list for new titles
                }
                // setting new data into the list view
                postListView.setAdapter(
                        new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                titles
                        ));


            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        });



    }

}