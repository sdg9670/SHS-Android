package com.example.nabot.util;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.nabot.domain.WritingDTO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class FireBaseStorage {
    Uri filepath=null;//처음 단계에서 진행
    WritingDTO writingDTO=new WritingDTO();
    List<Uri> multifilepath=null;

    public  FireBaseStorage(){
        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<WritingDTO>> call=retrofitRequest.getlasat_writing();
        call.enqueue(new RetrofitRetry<List<WritingDTO>>(call) {
            @Override
            public void onResponse(Call<List<WritingDTO>> call, Response<List<WritingDTO>> response) {
                writingDTO=response.body().get(0);
            }
        });
    }

    public void  SingleUploadFile(Uri filepath){
       this.filepath=filepath;
        Log.e("asd", String.valueOf(writingDTO.getId()));
       if(filepath != null) {
           Log.e("eee","aaaaaaaaaa");
           double d_randomValue=Math.random();
           int  randomValue=(int)(d_randomValue*10000000)+1;
           FirebaseStorage storage = FirebaseStorage.getInstance();
           String filename = Integer.toString(writingDTO.getId())+"_"+Integer.toString(randomValue);
           StorageReference storageReference = storage.getReferenceFromUrl("gs://nabot-application.appspot.com"
           ).child("board/" + filename);

           storageReference.putFile(filepath)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                       }
                   })
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                           double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                       }
                   });
       }
   }
   public  void MultiUploadFile(List<Uri>multifilepath){
    this.multifilepath=multifilepath;
       Log.e("asd", String.valueOf(writingDTO.getId()));


       for(int i=0; i<multifilepath.size(); i++){;
        if(multifilepath != null) {

            Log.e("eee","aaaaaaaaaa");
            double d_randomValue=Math.random();
            int  randomValue=(int)(d_randomValue*10000000)+1;
            FirebaseStorage storage = FirebaseStorage.getInstance();
            String filename = Integer.toString(writingDTO.getId())+"_"+Integer.toString(randomValue);
            StorageReference storageReference = storage.getReferenceFromUrl("gs://nabot-application.appspot.com"
            ).child("board/" + filename);
            storageReference.putFile(multifilepath.get(i))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        }
                    });
        }
    }


    }

   }
