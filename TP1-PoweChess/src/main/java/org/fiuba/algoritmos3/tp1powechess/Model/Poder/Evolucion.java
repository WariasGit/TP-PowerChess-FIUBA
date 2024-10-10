public class Evolucion extends Poder {
    private int[] movimientoExtra;

    public Evolucion(int[] movimientoExtra) {
        super("Evolucion", 0, CategoriaPoder.EVOLUCION); // Dura por el resto de la partida
        this.movimientoExtra = movimientoExtra;
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.aplicarPoder(this);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);  // Elimina el poder Freeze de la pieza
    }
}
