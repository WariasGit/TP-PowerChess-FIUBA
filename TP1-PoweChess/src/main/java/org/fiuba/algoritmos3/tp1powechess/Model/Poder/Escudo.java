public class Escudo extends Poder {

    public Escudo(int duracion) {
        super("Escudo", duracion, CategoriaPoder.DURACION);
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.aplicarPoder(this);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);
}
