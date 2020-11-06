package me.parzibyte.crudsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.parzibyte.crudsqlite.controllers.BotiquinesControlador;
import me.parzibyte.crudsqlite.modelos.Botiquin;

public class MainActivity extends AppCompatActivity {
    private List<Botiquin> listaDeBotiquins;
    private RecyclerView recyclerView;
    private AdaptadorBotiquines adaptadorBotiquines;
    private BotiquinesControlador botiquinesControlador;
    private FloatingActionButton fabAgregarMascota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Definir nuestro controlador
        botiquinesControlador = new BotiquinesControlador(MainActivity.this);

        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewMascotas);
        fabAgregarMascota = findViewById(R.id.fabAgregarMascota);


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeBotiquins = new ArrayList<>();
        adaptadorBotiquines = new AdaptadorBotiquines(listaDeBotiquins);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorBotiquines);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeBotiquines();

        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarBotiquinActivity.java
                Botiquin botiquinSeleccionada = listaDeBotiquins.get(position);
                Intent intent = new Intent(MainActivity.this, EditarBotiquinActivity.class);
                intent.putExtra("idBotiquin", botiquinSeleccionada.getId());
                intent.putExtra("nombreBotiquin", botiquinSeleccionada.getNombre());
                intent.putExtra("cantidadBotiquin", botiquinSeleccionada.getCantidad());
                intent.putExtra("fechavBotiquin", botiquinSeleccionada.getFechav());
                intent.putExtra("mgBotiquin", botiquinSeleccionada.getMg());
                intent.putExtra("presentacionBotiquin", botiquinSeleccionada.getPresentacion());
                intent.putExtra("descripcionBotiquin", botiquinSeleccionada.getDescripcion());

                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Botiquin botiquinParaEliminar = listaDeBotiquins.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                botiquinesControlador.eliminarBotiquin(botiquinParaEliminar);
                                refrescarListaDeBotiquines();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar a la mascota " + botiquinParaEliminar.getNombre() + "?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        fabAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this, AgregarBotiquinActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeBotiquines();
    }

    public void refrescarListaDeBotiquines() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorBotiquines == null) return;
        listaDeBotiquins = botiquinesControlador.obtenerBotiquines();
        adaptadorBotiquines.setListaDeBotiquins(listaDeBotiquins);
        adaptadorBotiquines.notifyDataSetChanged();
    }
}
