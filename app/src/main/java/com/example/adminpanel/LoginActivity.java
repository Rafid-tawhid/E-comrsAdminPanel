package com.example.adminpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText e1,e2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.home_ed1);
        e2=findViewById(R.id.home_ed2);
        btn=findViewById(R.id.homebtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=e1.getText().toString().trim();
                String pass=e2.getText().toString().trim();

                if (user.equals("rafid")&&pass.equals("123"))
                {
                    Intent intent=new Intent(LoginActivity.this,UploadImageActivity.class);
                    startActivity(intent);
                    e1.setText("");
                    e2.setText("");

                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setIcon(R.drawable.error);
                    alertDialogBuilder.setTitle("Incorrect Password");
                    alertDialogBuilder.setMessage("Check Admin & Password Again");
                    alertDialogBuilder.show();
                    e1.setText("");
                    e2.setText("");

                }
            }
        });
    }
}