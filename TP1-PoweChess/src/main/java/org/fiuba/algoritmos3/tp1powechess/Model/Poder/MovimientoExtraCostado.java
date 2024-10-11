public class MovimientoExtraAlCostado extends Poder {

    public MovimientoExtraAlCostado() {
        super("Movimiento Extra Lateral", -1, CategoriaPoder.EVOLUCION);  // Poder permanente
    }

    @Override
    public void aplicar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            pieza.agregarMovimiento(new int[]{0, 1});   // Movimiento hacia la derecha
            pieza.agregarMovimiento(new int[]{0, -1});  // Movimiento hacia la izquierda
            pieza.agregarMovimiento(new int[]{1, 0});   // Movimiento hacia abajo
            pieza.agregarMovimiento(new int[]{-1, 0});  // Movimiento hacia arriba
        }
    }

    public void desactivar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            pieza.eliminarMovimiento(new int[]{0, 1});
            pieza.eliminarMovimiento(new int[]{0, -1});
            pieza.eliminarMovimiento(new int[]{1, 0});
            pieza.eliminarMovimiento(new int[]{-1, 0});
        }
    }
}
