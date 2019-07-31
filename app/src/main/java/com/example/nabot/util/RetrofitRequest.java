package com.example.nabot.util;


import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ChatDTO;
import com.example.nabot.domain.ChatImageDTO;
import com.example.nabot.domain.CheckVoteDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.domain.VoteDTO;
import com.example.nabot.domain.VoteWheterDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.domain.WritingImageDTO;
import com.google.gson.JsonArray;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitRequest {
    @PUT("updatefcm")
    Call<Void> updateFCM(@Body ClientDTO client);

    @GET("logincheck")
    Call<List<ClientDTO>> loginCheck(@Query("id_name") String id_name, @Query("password") String password);

    @GET("client")
    Call<List<ClientDTO>> getClient(@Query("id") int id);

    @PUT("client")
    Call<Void> putClient(@Body ClientDTO client);

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

    @POST("writing_image_multi")
    Call<Void>postWriting_Image_Multi(@Body List<WritingImageDTO> writingImageInsertDTOS);

    @POST("writing_vote")
    Call<Void>postWriting_Vote(@Body List<VoteDTO> voteDTOS);


    @GET("writing_vote")
    Call<List<VoteDTO>>getWriting_Vote(@Query("writing_id") int writing_id);

    @POST("vote_whether")
    Call<Void>postVoteWheter(@Body VoteWheterDTO voteWheterDTO);

    @GET("writing_image")
    Call<List<WritingImageDTO>> getWriting_Image(@Query("writing_id") int writing_id);

    @HTTP(method = "DELETE", path = "writing_image", hasBody = true)
    Call<Void>deleteWriting_Image(@Body List<WritingImageDTO> writingImageDTO);


    //친구추가-요청


    @DELETE("requestFriend")
    Call<Void> delRequestFreind(@Query("clientid") int clientid, @Query("someid") int someid);

    @GET("requestFriend")
    Call<List<ContactDTO>> getRequestFriendList(@Query("id") int id);

    @GET("friendCheck")
    Call<List<ContactDTO>> getFriendCheck(@Query("clientid") int clientid, @Query("someidname") String someidname);

    @GET("friendCheck2")
    Call<List<ContactDTO>> getFriendCheck2(@Query("someidname") String someidname);

    @GET("friend")
    Call<List<ContactDTO>> getFriend(@Query("id") int id);

    @POST("friend")
    Call<Void> postFriend(@Body ContactDTO contact);


    @GET("check_vote")
    Call<List<CheckVoteDTO>> check_Vote(@Query("writing_id") int writing_id, @Query("client_id") int client_id);


    @DELETE("friend")
    Call<Void> delFreind(@Query("clientid") int clientid, @Query("someid") int someid);

    // 채팅

    @POST("chat")
    Call<List<ChatDTO>> postChat(@Body ChatDTO chat);

    @DELETE("chat")
    Call<Void> delChat(@Query("sendid") int sendid, @Query("recvid") int recvid);

    @POST("chat_image_multi")
    Call<Void>postChat_Image_Multi(@Body List<ChatImageDTO> chatImageInsertDTOS);

    @GET("chat_image")
    Call<List<ChatImageDTO>>getChat_image(@Query("chat_id") int chat_id);

    @HTTP(method = "DELETE", path = "chat_image", hasBody = true)
    Call<Void>deleteChat_Image(@Body List<ChatImageDTO> ChatImageDTO);

    @GET("last_chat")
    Call<List<ChatDTO>> getlast_chat();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://simddong.ga:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}