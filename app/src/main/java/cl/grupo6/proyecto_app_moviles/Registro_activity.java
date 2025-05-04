package cl.grupo6.proyecto_app_moviles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance(); // 🔧 Muy importante

        EditText correo_capturado = findViewById(R.id.correo_reg);
        EditText password_capturado = findViewById(R.id.pass_reg);
        EditText password2_capturado = findViewById(R.id.passreg2);
        Button boton_registro = findViewById(R.id.btnreg);
        TextView go_login = findViewById(R.id.go_login);

        boton_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = correo_capturado.getText().toString().trim();
                String password = password_capturado.getText().toString().trim();
                String password2 = password2_capturado.getText().toString().trim();

                if (correo.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(Registro_activity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(password2)) {
                    Toast.makeText(Registro_activity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Registro_activity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro_activity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(Registro_activity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro_activity.this, MainActivity.class));
                finish();
            }
        });


    }
}
