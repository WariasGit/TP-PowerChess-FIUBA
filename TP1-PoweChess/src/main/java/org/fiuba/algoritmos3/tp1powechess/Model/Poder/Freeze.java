public class Freeze extends Poder {

    public Freeze(int duracion) {
        super("Freeze", duracion, CategoriaPoder.DURACION);
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.aplicarPoder(this);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);  // Elimina el poder Freeze de la pieza
    }
}