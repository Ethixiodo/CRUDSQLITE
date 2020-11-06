package me.parzibyte.crudsqlite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.parzibyte.crudsqlite.modelos.Botiquin;

public class AdaptadorBotiquines extends RecyclerView.Adapter<AdaptadorBotiquines.MyViewHolder> {

    private List<Botiquin> listaDeBotiquins;

    public void setListaDeBotiquins(List<Botiquin> listaDeBotiquins) {
        this.listaDeBotiquins = listaDeBotiquins;
    }

    public AdaptadorBotiquines(List<Botiquin> botiquins) {
        this.listaDeBotiquins = botiquins;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaBotiquin = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_botiquin, viewGroup, false);
        return new MyViewHolder(filaBotiquin);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener el remedio de nuestra lista gracias al índice i
        Botiquin botiquin = listaDeBotiquins.get(i);

        // Obtener los datos de la lista
        String nombreRemedio = botiquin.getNombre();
        int cantidadRemedio = botiquin.getCantidad();
        int fechavRemedio = botiquin.getFechav();
        int mgRemedio = botiquin.getMg();
        String presentacionRemedio = botiquin.getPresentacion();
        String descripcionRemedio = botiquin.getDescripcion();

        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombreRemedio);
        myViewHolder.cantidad.setText(String.valueOf(cantidadRemedio));
        myViewHolder.fechav.setText(String.valueOf(fechavRemedio));
        myViewHolder.mg.setText(String.valueOf(mgRemedio));
        myViewHolder.presentacion.setText(presentacionRemedio);
        myViewHolder.descripcion.setText(descripcionRemedio);

    }

    @Override
    public int getItemCount() {
        return listaDeBotiquins.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, cantidad, fechav, mg, presentacion, descripcion;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.cantidad = itemView.findViewById(R.id.tvCantidad);
            this.fechav = itemView.findViewById(R.id.tvFechav);
            this.mg = itemView.findViewById(R.id.tvMg);
            this.presentacion = itemView.findViewById(R.id.tvPresentacion);
            this.descripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}
