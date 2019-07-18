package com.example.nabot.util;


import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.domain.WritingDTO;
import com.google.android.gms.common.api.Api;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitRequest {
    @GET("client")
    Call<List<ClientDTO>> getClient(@Query("id") int id);

    @GET("board")
    Call<List<BoardDTO>> getBoard();

    @GET("writing")
    Call<List<WritingDTO>> getWriting(@Query("board_id") int boardid);

    @POST("writing")
    Call<List<WritingDTO>> postWriting(@Body WritingDTO writing);

    @PUT("writing")
    Call<Void> putWriting(@Body WritingDTO writing);

    @DELETE("writing")
    Call<Void> deleteWriting(@Query("id") int id);

    @POST("comment")
    Call<Void> postComment(@Body CommentDTO comment);

    @GET("comment")
    Call<List<CommentDTO>> getComment(@Query("writing_id") int writing_id);

    @DELETE("comment")
    Call<Void> deleteComment(@Query("id") int id);

    @PUT("comment")
    Call<Void> putComment(@Body CommentDTO comment);


    //친구추가-요청

    @GET("friendList")
    Call<List<ContactDTO>> getFriend();

    @GET("friendCheck")
    Call<List<ContactDTO>> getFriendCheckList();

    @GET("friend")
    Call<List<ClientDTO>> getFriendList();

    @POST("friend")
    Call<List<ContactDTO>> postFriend(@Body ContactDTO contact);

    @PUT("friendCheck")
    Call<Void> putFriendCheck(@Query("someid") int someid);

    @DELETE("friend")
    Call<Void> delFreind(@Query("someid") int someid);



    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://simddong.ga:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}