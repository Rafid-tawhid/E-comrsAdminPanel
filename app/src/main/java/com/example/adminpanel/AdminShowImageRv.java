package com.example.adminpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminShowImageRv extends AppCompatActivity {
    RecyclerView recyclerView2;
    ImageAdapter2 imageAdapter2;
    DatabaseReference databaseReference;
    SearchView searchView;
    TextView back;
    List<Upload> mUploadS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_image_rv);


        searchView=findViewById(R.id.searchViewId);
        //back btn
        back=findViewById(R.id.back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminShowImageRv.this,AdminShowImageRv.class);
                startActivity(intent);
            }
        });
        back.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(AdminShowImageRv.this,UploadImageActivity.class);
                startActivity(intent);
                return false;
            }
        });



        recyclerView2=findViewById(R.id.rv);
//        recyclerView2.setHasFixedSize(false);

        recyclerView2.setLayoutManager(new GridLayoutManager(this,2));

        mUploadS =new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mUploadS.clear();
                for (DataSnapshot postSnap:snapshot.getChildren())
                {
                    Upload uploadS =postSnap.getValue(Upload.class);
                    uploadS.setKey(postSnap.getKey());
                    mUploadS.add(uploadS);

                }

                imageAdapter2=new ImageAdapter2(AdminShowImageRv.this, mUploadS);
                recyclerView2.setAdapter(imageAdapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(AdminShowImageRv.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //filter search

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                imageAdapter2.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        //bottom nav



    }





    public void SearchBtn(View view) {

        back.setVisibility(View.GONE);
        searchView.setLayoutParams(new GridLayoutManager.LayoutParams(350,30));
    }
}