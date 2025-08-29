package com.pralee.restapi;


import retrofit2.Call;
import retrofit2.http.GET;

public interface RestPosts {
    @GET("/posts/1")
    Call<Posts> getFirstPost();
}
