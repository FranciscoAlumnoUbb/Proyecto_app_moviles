package cl.grupo6.proyecto_app_moviles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private FirebaseAuth mAuth;
    private EditText userCapturado, passCapturada;
    private Button boton_inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inicializa Auth
        mAuth = FirebaseAuth.getInstance();
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userCapturado = findViewById(R.id.usercorreo);
        passCapturada = findViewById(R.id.userpass);
        boton_inicio = findViewById(R.id.btninicio);

        boton_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userCapturado.getText().toString().trim();
                String pass = passCapturada.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                irAlMenuPrincipal();
                            } else {
                                Toast.makeText(context, "Error: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            irAlMenuPrincipal();
        }
    }

    private void irAlMenuPrincipal() {
        Intent i = new Intent(this, Menu_principal_activity.class);
        startActivity(i);
        finish();
    }
}
