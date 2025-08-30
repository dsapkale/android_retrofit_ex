package com.pralee.restapi;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestPosts {
    @GET("/posts/1")
    Call<Posts> getFirstPost();

    @GET("/posts")
    Call<List<Posts>> getAllPosts();

    @POST("/posts")
    Call<Posts> savePost(@Body Posts post);

    @DELETE("/posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

    @PUT("/posts/{id}")
    Call<Posts> updatePost(@Path("id") int id, @Body Posts post);
}
