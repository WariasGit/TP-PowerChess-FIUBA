public class Limpieza extends Poder {

    public Limpieza() {
        super("Limpieza", 1, CategoriaPoder.ACCION);
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.desactivarPoder();
    }
}
