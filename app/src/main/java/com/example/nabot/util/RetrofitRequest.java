package com.example.nabot.util;


import android.support.design.widget.BaseTransientBottomBar;

import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.domain.WritingImageDTO;

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
    Call<Void> postWriting(@Body WritingDTO writing);

    @GET("last_writing")
    Call<List<WritingDTO>> getlasat_writing();

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

    @POST("writing_image")
    Call<Void>postWriting_Image(@Body WritingImageDTO writingImageDTO);

    @GET("writing_image")
    Call<List<WritingImageDTO>> getWriting_Image(@Query("writing_id") int writing_id);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://simddong.ga:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}