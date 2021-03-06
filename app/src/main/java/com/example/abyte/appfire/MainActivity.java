package com.example.abyte.appfire;


import android.content.Intent;

        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    TextView email1, password1;
    Button login, register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email);
        password =(EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.login);
        register =(Button) findViewById(R.id.register);
        email1 =(TextView) findViewById(R.id.email1);
        password1 =(TextView) findViewById(R.id.password1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userE = email.getText().toString();
                String passE = password.getText().toString();
                if (TextUtils.isEmpty(userE)){
                    Toast.makeText(getApplicationContext(),"Coloca un usuario", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(passE)){
                    Toast.makeText(getApplicationContext(),"Coloca una contraseña", Toast.LENGTH_SHORT).show();
                }

                auth.signInWithEmailAndPassword(userE,passE)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Correo o Contraseña incorrecta",Toast.LENGTH_SHORT).show();

                                }else {
                                    Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                                    startActivity(intent);


                                }
                            }
                        });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

}
