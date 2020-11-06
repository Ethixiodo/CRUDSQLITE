package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.controllers.BotiquinesControlador;
import me.parzibyte.crudsqlite.modelos.Botiquin;

public class AgregarBotiquinActivity extends AppCompatActivity {
    private Button btnAgregarBotiquin, btnCancelarNuevaBotiquin;
    private EditText etNombre, etCantidad, etFechav, etMg, etPresentacion, etDescripcion;
    private BotiquinesControlador botiquinesControlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_remedio);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);
        etCantidad = findViewById(R.id.etCantidad);
        etFechav = findViewById(R.id.etFechav);
        etMg = findViewById(R.id.etMg);
        etPresentacion = findViewById(R.id.etPresentacion);
        etDescripcion = findViewById(R.id.etDescripcion);

        btnAgregarBotiquin = findViewById(R.id.btnAgregarBotiquin);
        btnCancelarNuevaBotiquin = findViewById(R.id.btnCancelarNuevaBotiquin);
        // Crear el controlador
        botiquinesControlador = new BotiquinesControlador(AgregarBotiquinActivity.this);

        // Agregar listener del botón de guardar
        btnAgregarBotiquin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                etNombre.setError(null);
                etCantidad.setError(null);
                etFechav.setError(null);
                etMg.setError(null);
                etPresentacion.setError(null);
                etDescripcion.setError(null);

                 String nombre = etNombre.getText().toString(),
                        cantidadComoCadena = etCantidad.getText().toString(),
                        fechavComoCadena = etFechav.getText().toString(),
                        mgComoCadena = etMg.getText().toString();

                 String presentacion = etPresentacion.getText().toString();
                 String descripcion = etDescripcion.getText().toString();

                if ("".equals(nombre)) {
                    etNombre.setError("Escribe el nombre");
                    etNombre.requestFocus();
                    return;
                }

                if ("".equals(cantidadComoCadena)) {
                    etCantidad.setError("Escribe la cantidad");
                    etCantidad.requestFocus();
                    return;
                }

                if ("".equals(fechavComoCadena)) {
                    etFechav.setError("Escribe la fecha");
                    etFechav.requestFocus();
                    return;
                }
                if ("".equals(mgComoCadena)) {
                    etMg.setError("Escribe el mg");
                    etMg.requestFocus();
                    return;
                }
                if ("".equals(presentacion)) {
                    etPresentacion.setError("Escribe la presentacion");
                    etPresentacion.requestFocus();
                    return;
                }
                if ("".equals(descripcion)) {
                    etDescripcion.setError("Escribe la descripcion");
                    etDescripcion.requestFocus();
                    return;
                }

                // Ver si es un entero
                int cantidad;
                try {
                    cantidad = Integer.parseInt(etCantidad.getText().toString());
                } catch (NumberFormatException e) {
                    etCantidad.setError("Escribe un número");
                    etCantidad.requestFocus();
                    return;
                }

                int fechav;
                try {
                    fechav = Integer.parseInt(etFechav.getText().toString());
                } catch (NumberFormatException e) {
                    etFechav.setError("Escribe un número");
                    etFechav.requestFocus();
                    return;
                }

                int mg;
                try {
                    mg = Integer.parseInt(etMg.getText().toString());
                } catch (NumberFormatException e) {
                    etMg.setError("Escribe un número");
                    etMg.requestFocus();
                    return;
                }


                // Ya pasó la validación
                Botiquin nuevaBotiquin = new Botiquin(nombre, cantidad, fechav, mg, presentacion, descripcion);
                long id = botiquinesControlador.nuevaBotiquin(nuevaBotiquin);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarBotiquinActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    Toast.makeText(AgregarBotiquinActivity.this, "Producto Guardado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaBotiquin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
