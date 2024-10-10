public class Escudo extends Poder {

    public Escudo(int duracion) {
        super("Escudo", duracion, CategoriaPoder.DURACION);
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.setInmune(true); // Hacerla inmune a capturas
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.setInmune(false);
    }
}
