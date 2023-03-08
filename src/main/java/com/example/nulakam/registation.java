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

public class registation extends AppCompatActivity {

    TextView alrhaveacc;

    EditText regnum, regmail, regpwd, regconpwd;

    Button btnreg;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        alrhaveacc = findViewById(R.id.alrhaveacc);
        regnum = findViewById(R.id.regregnum);
        regmail = findViewById(R.id.regregmail);
        regpwd = findViewById(R.id.regpwd);
        btnreg = findViewById(R.id.btnreg);
        regconpwd = findViewById(R.id.regconpwd);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alrhaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registation.this,login.class));
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
    }

    private void performAuth() {

        String regnumber = regnum.getText().toString();
        String regemail = regmail.getText().toString();
        String regpassword = regpwd.getText().toString();
        String regconfirmpwd = regconpwd.getText().toString();

        if (!regemail.matches(emailPattern)){
            regmail.setError("Enter Correct Email");
        } else if(regpassword.isEmpty() || regpassword.length()<6){
            regpwd.setError("Enter Proper Password");
        } else if (!regpassword.equals(regconfirmpwd)) {
            regconpwd.setError("Password Doesn't Match");
            
        } else {
            progressDialog.setMessage("Please Wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(regemail,regpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(registation.this,"Registration Succussful...",Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(registation.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(registation.this,login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}