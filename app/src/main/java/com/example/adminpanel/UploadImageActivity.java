package com.example.adminpanel;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadImageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button choseImg,upImage;
    EditText name,type,details,price;
    ImageView imageView;
    ProgressBar progressBar;
    TextView showMsg,countOrder;
    Uri imgUri;
    String selectSpinnerItem;
    StorageReference storageReference;
    private FirebaseStorage mStorage;
    DatabaseReference databaseReference,databaseReference2;
    TextView orderActivity;
    public  String[] catagorys = { "Men", "Women", "Children", "Shirt", "Pant","Gadgets"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        choseImg=findViewById(R.id.pimg);
        upImage=findViewById(R.id.pUpload);
        name=findViewById(R.id.pName);
        orderActivity=findViewById(R.id.orderId);
//        type=findViewById(R.id.pType);
        details=findViewById(R.id.pDetails);
        countOrder=findViewById(R.id.orderCount);
        price=findViewById(R.id.pPrice);
        imageView=findViewById(R.id.showImg);
        progressBar=findViewById(R.id.progressbarid);
        showMsg=findViewById(R.id.pMsg);
        mStorage=FirebaseStorage.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference2= FirebaseDatabase.getInstance().getReference("orders");


        //total order count
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countOrder.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        choseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImageFile();
            }
        });

        upImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        showMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UploadImageActivity.this, AdminShowImageRv.class);
                startActivity(intent);
            }
        });

        //goto order activity
        orderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(UploadImageActivity.this,OrderLists.class);
                startActivity(intent);

            }
        });

        //spinner set

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,catagorys);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


    }
    private String getFileExt(Uri uri)
    {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadImage() {
        if(imgUri!=null)
        {
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference fileRef=storageReference.child(System.currentTimeMillis()+"."+getFileExt(imgUri));
            fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },3000);
                    progressDialog.dismiss();
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
//                            Upload upload = new Upload(et_localization, url);
//                            String uploadId = mDataBaseRef.push().getKey();
//                            mDataBaseRef.child(uploadId).setValue(upload);
                            Toast.makeText(UploadImageActivity.this, "Upload Succesfully", Toast.LENGTH_SHORT).show();
                            Upload uploadS =new Upload(name.getText().toString(),selectSpinnerItem,details.getText().toString(),price.getText().toString(),url,"null",1);
                            String uploadId=databaseReference.push().getKey();

                            databaseReference.child(uploadId).setValue(uploadS);



                        }
                    });


//                    Toast.makeText(MainActivity.this, "Upload Succesfully", Toast.LENGTH_SHORT).show();
//                    Upload upload=new Upload(name.getText().toString(),type.getText().toString(),details.getText().toString(),price.getText().toString(),taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
//                    String uploadId=databaseReference.push().getKey();
//                    databaseReference.child(uploadId).setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UploadImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(
                                UploadTask.TaskSnapshot taskSnapshot)
                        {


                            double progress
                                    = (100.0
                                    * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(
                                    "Product Uploaded "
                                            + (int)progress + "%");
                            progressBar.setProgress((int) progress);
                        }
                    });


        }
        else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }



    }

    private void choseImageFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                1);

    }


    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == 1
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            imgUri = data.getData();
//            try {
//
////                 Setting image on image view using Bitmap
//                Bitmap bitmap = MediaStore
//                        .Images
//                        .Media
//                        .getBitmap(
//                                getContentResolver(),
//                                imgUri);
//                imageView.setImageBitmap(bitmap);
//
//
//            }
//
//            catch (IOException e) {
//                // Log the exception
//                e.printStackTrace();
//            }
            Picasso.get().load(imgUri).into(imageView);
        }
    }


    //spinner methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(),catagorys[position] , Toast.LENGTH_LONG).show();
        selectSpinnerItem=catagorys[position];
        Toast.makeText(getApplicationContext(),selectSpinnerItem , Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}