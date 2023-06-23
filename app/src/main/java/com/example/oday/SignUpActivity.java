package com.example.oday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.oday.databinding.ActivityMainBinding;
import com.example.oday.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding= ActivitySignUpBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        binding.signUpBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaail = binding.emaildET.getText().toString(), password=binding.passwordET.getText().toString();

                auth.createUserWithEmailAndPassword(emaail,password  )
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user= new User( binding.nameET.getText().toString(), binding.professionET.getText().toString(),emaail,password );
                            String id =task.getResult().getUser().getUid();
                            database.getReference().child( "Users" ).child( id ).setValue( user );
                            Toast.makeText( SignUpActivity.this, "User Data saved", Toast.LENGTH_SHORT ).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity( intent );

                        }
                        else {
                            Toast.makeText( SignUpActivity.this, "Error", Toast.LENGTH_SHORT ).show();
                        }

                    }
                } );

            }
        } );

        binding.goToLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity( intent );
            }
        } );

    }
}