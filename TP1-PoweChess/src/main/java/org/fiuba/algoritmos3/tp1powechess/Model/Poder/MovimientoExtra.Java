public class MovimientoExtra extends Poder {

    private String tipoMovimiento;

    public MovimientoExtra(String tipoMovimiento) {
        super("Movimiento Extra", -1, CategoriaPoder.EVOLUCION);  // -1 =  es permanente
        this.tipoMovimiento = tipoMovimiento;  //lateral, diagonal, etc
    }

    @Override
    public void aplicar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            //????
        }
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.eliminarMovimiento(tipoMovimiento);  ///???
    }
}
