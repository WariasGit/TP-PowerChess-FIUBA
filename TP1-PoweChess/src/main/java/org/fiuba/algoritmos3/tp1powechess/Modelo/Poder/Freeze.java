public class Freeze extends Poder {

    public Freeze(int duracion) {
        super("Freeze", duracion, CategoriaPoder.DURACION);
    }

    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this); }
}