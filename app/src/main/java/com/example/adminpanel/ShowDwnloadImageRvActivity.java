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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowDwnloadImageRvActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageAdapterS imageAdapter;
    DatabaseReference databaseReference;
    SearchView searchView;
    TextView back;
    List<Upload> mUploadS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dwnload_image_rv);

        searchView=findViewById(R.id.searchViewId);
        //back btn
        back=findViewById(R.id.back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowDwnloadImageRvActivity.this,UploadImageActivity.class);
                startActivity(intent);
            }
        });




        recyclerView=findViewById(R.id.rv);
//        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager lm = new GridLayoutManager(this, 2,LinearLayoutManager.VERTICAL, true); // last argument (true) is flag for reverse layout
        recyclerView.setLayoutManager(lm);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

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

                imageAdapter=new ImageAdapterS(ShowDwnloadImageRvActivity.this, mUploadS);
                recyclerView.setAdapter(imageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ShowDwnloadImageRvActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //filter search

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                imageAdapter.getFilter().filter(query);
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