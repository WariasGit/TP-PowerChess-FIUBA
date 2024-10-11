public class Vuelo extends Poder {

    public Vuelo() {
        super("Vuelo", 0, CategoriaPoder.ACCION); // Solo dura un turno
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.aplicarPoder(this);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);
}
