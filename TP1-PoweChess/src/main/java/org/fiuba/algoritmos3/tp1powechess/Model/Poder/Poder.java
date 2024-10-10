public abstract class Poder {
    protected String nombre;
    protected int duracion; 
    protected CategoriaPoder categoria;

    public Poder(String nombre, int duracion, CategoriaPoder categoria) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.categoria = categoria;
    }

    public abstract void aplicar(Pieza pieza);

    public abstract void desactivar(Pieza pieza);

    public boolean esDeDuracion() {
        return this.categoria == CategoriaPoder.DURACION;
    }

    public boolean esDeAccion() {
        return this.categoria == CategoriaPoder.ACCION;
    }

    public boolean esDeEvolucion() {
        return this.categoria == CategoriaPoder.EVOLUCION;
    }

    public void reducirDuracion() {
        if (this.categoria == CategoriaPoder.DURACION && this.duracion > 0) {
            this.duracion--;
        }
    }

    public boolean estaActivo() {
        return this.duracion > 0 || this.categoria == CategoriaPoder.ACCION;
    }

    public CategoriaPoder getCategoria() {
        return this.categoria;
    }

    public boolean esRey(Pieza pieza) {
        if (pieza.getTipoDePieza == 'Rey') {
            return true;
        }
        return false;
    }
}
