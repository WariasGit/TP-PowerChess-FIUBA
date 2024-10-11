public class DobleJuego extends Poder {

    public DobleJuego() {
        super("Doble-juego", 0, CategoriaPoder.ACCION);
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.setMovimientoDoble(true);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.setMovimientoDoble(false);
    }
}
