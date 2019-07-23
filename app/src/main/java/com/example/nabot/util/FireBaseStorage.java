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
    List<Uri> multifilepath = new ArrayList<Uri>();
    int writingid = 0;

   public  List<String> UploadFile(List<Uri>multifilepath ,int writingid){
        this.multifilepath.clear();
        this.multifilepath=multifilepath;
        this.writingid=writingid;
        List<String> downloadUri_multi=new ArrayList<String>();
        for(int i=0; i<multifilepath.size(); i++){
            if(multifilepath != null) {
                double d_randomValue=Math.random();
                String str = "";
                for(int j = 0; j < 8; j++)
                    str +=  "" + (int) (Math.random() * 10);
                final FirebaseStorage storage = FirebaseStorage.getInstance();
                String filename = Integer.toString(writingid)+"_"+str;
                Log.e("filename222222",filename);
                downloadUri_multi.add("board/" + filename);
                final StorageReference storageReference = storage.getReferenceFromUrl("gs://nabot-application.appspot.com"
                ).child(downloadUri_multi.get(i));
                Log.e("업", multifilepath.get(i).toString());
                storageReference.putFile(multifilepath.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.e("success", String.valueOf(taskSnapshot));
                    }
                });
            }
        }
        return downloadUri_multi;
    }

}
