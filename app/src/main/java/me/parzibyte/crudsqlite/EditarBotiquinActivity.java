package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.controllers.BotiquinesControlador;
import me.parzibyte.crudsqlite.modelos.Botiquin;

public class EditarBotiquinActivity extends AppCompatActivity {
    private EditText etEditarNombre, etEditarCantidad, etEditarFechav, etEditarMg, etEditarPresentacion, etEditarDescripcion;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Botiquin botiquin;//El botiquin que vamos a estar editando
    private BotiquinesControlador botiquinesControlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_remedio);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        botiquinesControlador = new BotiquinesControlador(EditarBotiquinActivity.this);

        // Rearmar el botiquin
        long idBotiquin = extras.getLong("idBotiquin");
        String nombreBotiquin = extras.getString("nombreBotiquin");
        int cantidadBotiquin = extras.getInt("cantidadBotiquin");
        int fechavBotiquin = extras.getInt("fechavBotiquin");
        int mgBotiquin = extras.getInt("mgBotiquin");
        String presentacionBotiquin = extras.getString("presentacionBotiquin");
        String descripcionBotiquin = extras.getString("descripcionBotiquin");

        botiquin = new Botiquin(nombreBotiquin, cantidadBotiquin, fechavBotiquin, mgBotiquin, presentacionBotiquin, descripcionBotiquin, idBotiquin);


        // Ahora declaramos las vistas
        etEditarNombre = findViewById(R.id.etEditarNombre);
        etEditarCantidad = findViewById(R.id.etEditarCantidad);
        etEditarFechav = findViewById(R.id.etEditarFechav);
        etEditarMg = findViewById(R.id.etEditarMg);
        etEditarPresentacion = findViewById(R.id.etEditarPresentacion);
        etEditarDescripcion = findViewById(R.id.etEditarDescripcion);

        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionMascota);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosMascota);


        // Rellenar los EditText con los datos del botiquin
        etEditarNombre.setText(botiquin.getNombre());
        etEditarCantidad.setText(String.valueOf(botiquin.getCantidad()));
        etEditarFechav.setText(String.valueOf(botiquin.getFechav()));
        etEditarMg.setText(String.valueOf(botiquin.getMg()));
        etEditarPresentacion.setText(String.valueOf(botiquin.getPresentacion()));
        etEditarDescripcion.setText(String.valueOf(botiquin.getDescripcion()));


        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etEditarNombre.setError(null);
                etEditarCantidad.setError(null);
                etEditarFechav.setError(null);
                etEditarMg.setError(null);
                etEditarPresentacion.setError(null);
                etEditarDescripcion.setError(null);

                // Crear el botiquin con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoNombre = etEditarNombre.getText().toString();
                String posibleNuevaCantidad = etEditarCantidad.getText().toString();
                String posibleNuevaFechav = etEditarFechav.getText().toString();
                String posibleNuevaMg = etEditarMg.getText().toString();
                String nuevoPresentacion = etEditarPresentacion.getText().toString();
                String nuevoDescripcion = etEditarDescripcion.getText().toString();

                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Escribe el nombre");
                    etEditarNombre.requestFocus();
                    return;
                }
                if (posibleNuevaCantidad.isEmpty()) {
                    etEditarCantidad.setError("Escribe la cantidad");
                    etEditarCantidad.requestFocus();
                    return;
                }
                if (posibleNuevaFechav.isEmpty()) {
                    etEditarFechav.setError("Escribe la fecha");
                    etEditarFechav.requestFocus();
                    return;
                }
                if (posibleNuevaMg.isEmpty()) {
                    etEditarMg.setError("Escribe el mg");
                    etEditarMg.requestFocus();
                    return;
                }
                if (nuevoPresentacion.isEmpty()) {
                    etEditarPresentacion.setError("Escribe la presentacion");
                    etEditarPresentacion.requestFocus();
                    return;
                }
                if (nuevoDescripcion.isEmpty()) {
                    etEditarDescripcion.setError("Escribe la descripcion");
                    etEditarDescripcion.requestFocus();
                    return;
                }

                // Si no es entero, igualmente marcar error
                int nuevaCantidad;
                try {
                    nuevaCantidad = Integer.parseInt(posibleNuevaCantidad);
                } catch (NumberFormatException e) {
                    etEditarCantidad.setError("Escribe un número");
                    etEditarCantidad.requestFocus();
                    return;
                }


                int nuevaFechav;
                try {
                    nuevaFechav = Integer.parseInt(posibleNuevaFechav);
                } catch (NumberFormatException e) {
                    etEditarFechav.setError("Escribe un número");
                    etEditarFechav.requestFocus();
                    return;
                }


                int nuevaMg;
                try {
                    nuevaMg = Integer.parseInt(posibleNuevaMg);
                } catch (NumberFormatException e) {
                    etEditarMg.setError("Escribe un número");
                    etEditarMg.requestFocus();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                Botiquin botiquinConNuevosCambios = new Botiquin(nuevoNombre, nuevaCantidad, nuevaFechav, nuevaMg, nuevoPresentacion, nuevoDescripcion, botiquin.getId());
                int filasModificadas = botiquinesControlador.guardarCambios(botiquinConNuevosCambios);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarBotiquinActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }
}
