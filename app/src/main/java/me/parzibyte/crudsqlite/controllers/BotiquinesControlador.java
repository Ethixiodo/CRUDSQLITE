package me.parzibyte.crudsqlite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.parzibyte.crudsqlite.AyudanteBaseDeDatos;
import me.parzibyte.crudsqlite.modelos.Botiquin;


public class BotiquinesControlador {
    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "remedios";

    public BotiquinesControlador(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }


    public int eliminarBotiquin(Botiquin botiquin) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(botiquin.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaBotiquin(Botiquin botiquin) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", botiquin.getNombre());
        valoresParaInsertar.put("cantidad", botiquin.getCantidad());
        valoresParaInsertar.put("fechav", botiquin.getFechav());
        valoresParaInsertar.put("mg", botiquin.getMg());
        valoresParaInsertar.put("presentacion", botiquin.getPresentacion());
        valoresParaInsertar.put("descripcion", botiquin.getDescripcion());


        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Botiquin botiquinEditada) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", botiquinEditada.getNombre());
        valoresParaActualizar.put("cantidad", botiquinEditada.getCantidad());
        valoresParaActualizar.put("fechav", botiquinEditada.getFechav());
        valoresParaActualizar.put("mg", botiquinEditada.getMg());
        valoresParaActualizar.put("presentacion", botiquinEditada.getPresentacion());
        valoresParaActualizar.put("descripcion", botiquinEditada.getDescripcion());

        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idBotiquin
        String[] argumentosParaActualizar = {String.valueOf(botiquinEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Botiquin> obtenerBotiquines() {
        ArrayList<Botiquin> botiquins = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // SELECT argumentos
        String[] columnasAConsultar = {"nombre", "cantidad", "fechav", "mg", "presentacion", "descripcion", "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from remedios
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return botiquins;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return botiquins;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de remedios
        do {
            // El 0 es el número de la columna, como seleccionamos
            // argumentos por indice
            String nombreObtenidoDeBD = cursor.getString(0);
            int cantidadObtenidaDeBD = cursor.getInt(1);
            int fechavObtenidaDeBD = cursor.getInt(2);
            int mgObtenidaDeBD = cursor.getInt(3);
            String presentacionObtenidaDeBD = cursor.getString(4);
            String descripcionObtenidaDeBD = cursor.getString(5);
            long idBotiquin = cursor.getLong(6);
            Botiquin botiquinObtenidaDeBD = new Botiquin(nombreObtenidoDeBD, cantidadObtenidaDeBD, fechavObtenidaDeBD, mgObtenidaDeBD, presentacionObtenidaDeBD, descripcionObtenidaDeBD, idBotiquin);
            botiquins.add(botiquinObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista
        cursor.close();
        return botiquins;
    }
}