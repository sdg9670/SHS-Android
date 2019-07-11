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
    Uri filepath = null;
    List<Uri> multifilepath = null;
    int writingid = 0;

    public String SingleUploadFile(Uri filepath, int writingid) {
        this.filepath = filepath;
        this.writingid = writingid;
        String downloadUri_single = null;
        if (filepath != null) {
            double d_randomValue = Math.random();
            int randomValue = (int) (d_randomValue * 10000000) + 1;
            FirebaseStorage storage = FirebaseStorage.getInstance();
            String filename = Integer.toString(writingid) + "_" + Integer.toString(randomValue);
            downloadUri_single = "board/" + filename;
            final StorageReference storageReference = storage.getReferenceFromUrl("gs://nabot-application.appspot.com"
            ).child(downloadUri_single);
            storageReference.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        }
        return downloadUri_single;
    }
   public  List<String> MultiUploadFile(List<Uri>multifilepath ,int writingid){
    this.multifilepath=multifilepath;
    this.writingid=writingid;
       List<String> downloadUri_multi=new ArrayList<String>();
       Log.e("asd", String.valueOf(writingid));
       for(int i=0; i<multifilepath.size(); i++){
        if(multifilepath != null) {
            double d_randomValue=Math.random();
            int  randomValue=(int)(d_randomValue*10000000)+1;
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            String filename = Integer.toString(writingid)+"_"+Integer.toString(randomValue);
            downloadUri_multi.add("board/" + filename);
            final StorageReference storageReference = storage.getReferenceFromUrl("gs://nabot-application.appspot.com"
            ).child(downloadUri_multi.get(i));
            storageReference.putFile(multifilepath.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.e("asd", String.valueOf(taskSnapshot));
                }
            });
        }
    }
       return downloadUri_multi;
    }
   }
