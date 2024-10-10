public class Freeze extends Poder {

    public Freeze(int duracion) {
        super("Freeze", duracion, CategoriaPoder.DURACION);
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.setCongelada(true); // Deshabilitar los movimientos
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.setCongelada(false);
    }
}