public class Escudo extends Poder {

    public Escudo(int duracion) {
        super("Escudo", duracion, CategoriaPoder.DURACION);
    }

    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);
}
