public class RobarPoder extends Poder {

    public RobarPoder() {
        super("Robar poder", 1, CategoriaPoder.ACCION);
    }

    @Override
    public void aplicar(Jugador jugador, Jugador oponente) {
        Poder poderRobado = oponente.robarPoderDisponible();
        jugador.agregarPoder(poderRobado);
    }

}
