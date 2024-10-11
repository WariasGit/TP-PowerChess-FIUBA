public class MovimientoExtraDiagonal extends Poder {

    public MovimientoExtraDiagonal() {
        super("Movimiento Extra Diagonal", -1, CategoriaPoder.EVOLUCION);  // Poder permanente
    }

    @Override
    public void aplicar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            ((Alfil) pieza).agregarMovimiento(new int[]{1, 1});   // Diagonal hacia arriba a la derecha
            ((Alfil) pieza).agregarMovimiento(new int[]{1, -1});  // Diagonal hacia abajo a la derecha
            ((Alfil) pieza).agregarMovimiento(new int[]{-1, 1});  // Diagonal hacia arriba a la izquierda
            ((Alfil) pieza).agregarMovimiento(new int[]{-1, -1}); // Diagonal hacia abajo a la izquierda
        }
    }

    public void desactivar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            ((Alfil) pieza).eliminarMovimiento(new int[]{1, 1});
            ((Alfil) pieza).eliminarMovimiento(new int[]{1, -1});
            ((Alfil) pieza).eliminarMovimiento(new int[]{-1, 1});
            ((Alfil) pieza).eliminarMovimiento(new int[]{-1, -1});
        }
    }
}
