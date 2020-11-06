package me.parzibyte.crudsqlite.modelos;

public class Botiquin {

    private String nombre;
    private int cantidad;
    private int fechav;
    private int mg;
    private String presentacion;
    private String descripcion;
    private long id; // El ID de la BD


    public Botiquin(String nombre, int cantidad, int fechav, int mg, String presentacion, String descripcion) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechav = fechav;
        this.mg = mg;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getFechav() {
        return fechav;
    }

    public void setFechav(int fechav) {
        this.fechav = fechav;
    }

    public int getMg() {
        return mg;
    }

    public void setMg(int mg) {
        this.mg = mg;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Constructor para cuando instanciamos desde la BD
    public Botiquin(String nombre, int cantidad, int fechav, int mg, String presentacion, String descripcion, long id) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechav = fechav;
        this.mg = mg;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", fechav=" + fechav +
                ", mg=" + mg +
                ", presentacion='" + presentacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }
}
