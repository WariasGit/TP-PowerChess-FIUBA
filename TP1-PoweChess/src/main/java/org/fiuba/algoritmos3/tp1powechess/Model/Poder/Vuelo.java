public class Vuelo extends Poder {

    public Vuelo() {
        super("Vuelo", 0, CategoriaPoder.ACCION); // Solo dura un turno
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.setPuedeVolar(true); // Permitir pasar por encima de otras piezas
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.setPuedeVolar(false);
    }
}
