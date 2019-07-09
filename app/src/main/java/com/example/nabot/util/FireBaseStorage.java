package com.example.nabot.util;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class FireBaseStorage {
    Uri filepath=null;//처음 단계에서 진행
    List<Uri> multifilepath=null;
    String fulluri_single= null;
    List<String> fulluri_multi=new ArrayList<String>();
    int writingid=0;

    public Uri  SingleUploadFile(Uri filepath , int writingid){
       this.filepath=filepath;
       this.writingid=writingid;
       if(filepath != null) {
           double d_randomValue=Math.random();
           int  randomValue=(int)(d_randomValue*10000000)+1;
           FirebaseStorage storage = FirebaseStorage.getInstance();
           String filename = Integer.toString(writingid)+"_"+Integer.toString(randomValue);
           final StorageReference storageReference = storage.getReferenceFromUrl("gs://nabot-application.appspot.com"
           ).child("board/" + filename);
            Log.e("fulluri",fulluri_single);
           storageReference.putFile(filepath)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fulluri_single=String.valueOf(storageReference.getDownloadUrl());
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
           storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
               }
           });
       }
       return filepath;
   }
   public  void MultiUploadFile(List<Uri>multifilepath ,int writingid){
    this.multifilepath=multifilepath;
    this.writingid=writingid;
       Log.e("asd", String.valueOf(writingid));
       for(int i=0; i<multifilepath.size(); i++){
        if(multifilepath != null) {
            double d_randomValue=Math.random();
            int  randomValue=(int)(d_randomValue*10000000)+1;
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            String filename = Integer.toString(writingid)+"_"+Integer.toString(randomValue);
             final StorageReference storageReference = storage.getReferenceFromUrl("gs://nabot-application.appspot.com"
            ).child("board/" + filename);
             UploadTask uploadTask=storageReference.putFile(multifilepath.get(i));
             Task<Uri> uriTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                 @Override
                 public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                     if(!task.isSuccessful()){
                         throw task.getException();
                     }
                     return storageReference.getDownloadUrl();
                 }
             }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                 @Override
                 public void onComplete(@NonNull Task<Uri> task) {
                     if(task.isSuccessful()){
                         fulluri_multi.add(String.valueOf(task.getResult()));
                         Log.e("fufff", String.valueOf(fulluri_multi));
                     }
                 }
             });
        }
    }
    }

    public String Firebase_SingleUri(){
        return  fulluri_single;
    }
    public List<String> Firebase_MultiUri(){
        return fulluri_multi;
    }

   }
