package com.example.nabot.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.activities.BoardActivity;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URI;

public class FireBaseStorage {
    Uri filepath=null;//처음 단계에서 진행
    List<Uri> multifilepath=null;
    int writingid=0;
   public void  SingleUploadFile(Uri filepath ,int writingid){
       this.filepath=filepath;
       this.writingid=writingid;
       if(filepath != null) {
           Log.e("eee","aaaaaaaaaa");
           double d_randomValue=Math.random();
           int  randomValue=(int)(d_randomValue*10000000)+1;
           FirebaseStorage storage = FirebaseStorage.getInstance();
           String filename = Integer.toString(writingid)+"_"+Integer.toString(randomValue);
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
   public  void MultiUploadFile(List<Uri>multifilepath, int writingid ){
    this.writingid=writingid;
    this.multifilepath=multifilepath;
    for(int i=0; i<multifilepath.size(); i++){;
        if(multifilepath != null) {
            Log.e("eee","aaaaaaaaaa");
            double d_randomValue=Math.random();
            int  randomValue=(int)(d_randomValue*10000000)+1;
            FirebaseStorage storage = FirebaseStorage.getInstance();
            String filename = Integer.toString(writingid)+"_"+Integer.toString(randomValue);
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
