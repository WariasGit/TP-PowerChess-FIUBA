public class DobleJuego extends Poder {

    public DobleJuego() {
        super("Doble-juego", 0, CategoriaPoder.ACCION); // Solo dura un turno
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.setMovimientoDoble(true); // Permitir un movimiento adicional
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.setMovimientoDoble(false);
    }
}
