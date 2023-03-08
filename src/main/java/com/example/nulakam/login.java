package com.example.nulakam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    TextView createnewacc;

    EditText logregnum, logpwd, logemail;

    Button btnlog;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        createnewacc = findViewById(R.id.createnewacc);

        logregnum = findViewById(R.id.logregnum);
        logemail = findViewById(R.id.logem);
        logpwd = findViewById(R.id.logpwd);
        btnlog = findViewById(R.id.btnlog);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        createnewacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, registation.class));
            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performlogin();

            }
        });


    }

    private void performlogin() {
        String logregisnum = logregnum.getText().toString();
        String logpassword = logpwd.getText().toString();
        String logmail = logemail.getText().toString();


        if (!logmail.matches(emailPattern)){
            logemail.setError("Enter Correct Email");
        }
        else if (logpassword.isEmpty() || logpassword.length() < 6) {
            logpwd.setError("Enter Proper Password");
        } else {
            progressDialog.setMessage("Please Wait while Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(logmail,logpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(login.this,"Login Succussful...",Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(login.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(login.this,home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}