package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.*;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Jugador {
    private final Configuracion.ColoresJugadores color;
    private Boolean jaque;
    private String nombre;
    ArrayList<Pieza> piezasEnJuego;
    private Map<String, Integer> listaPoderes;
    private Rey rey;

    public Jugador(Configuracion.ColoresJugadores color) {
        this.color = color;
        jaque = false;
        piezasEnJuego = new ArrayList<>();
        listaPoderes = new HashMap<>();
        cargarPoderes();
    }

    public void setNombre(String nombre) {this.nombre = nombre;}

    private void cargarPoderes() {
        listaPoderes.put("Escudo", 1);
        listaPoderes.put("Freeze", 1);
        listaPoderes.put("Limpieza", 1);
        listaPoderes.put("Robar", 1);
    }

    public void setRey(Rey rey) {this.rey = rey;}

    public Rey getRey() {return rey;}

    public void setPiezasEnJuego(Pieza pieza) { piezasEnJuego.add(pieza); }

    public void quitarPiezaEnJuego(Pieza pieza) { piezasEnJuego.remove(pieza); }

    public ArrayList<Pieza> getPiezasEnJuego() { return new ArrayList<>(piezasEnJuego); }

    public String getNombre() { return this.nombre; }

    public Configuracion.ColoresJugadores getColor() { return this.color; }

    public Boolean estaEnJaque() {
        return jaque;
    }

    public void establecerJaque(){jaque = true;}

    public void quitarJaque(){jaque = false;}

    public Boolean tieneMovimientosPosibles(){
        for (Pieza pieza : piezasEnJuego) {
            if (pieza.tieneMovimientosPosibles()) {
                return true;
            }
        }
        return false;
    }

    public boolean puedeOcuparEsteCasillero(int filaNueva, int columnaNueva) {
        for (Pieza pieza : piezasEnJuego) {
            ArrayList<int[]> posicionesPosibles = pieza.getMovimientosPosibles();
            for (int[] posicion : posicionesPosibles) {
                if(posicion[0] == filaNueva && posicion[1] == columnaNueva) {
                    System.out.println("Esta posicion puede ser defendida por el jugador actual: " + filaNueva + ", " + columnaNueva);
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean tieneMaterialSuficiente(){
        int valorTotal = 0;
        for (Pieza pieza : piezasEnJuego) {
            valorTotal += pieza.getValor();
        }
        if(valorTotal == Configuracion.ValorPiezas.REY_MAS_PEON){
            valorTotal += Configuracion.ValorPiezas.VALOR_MINIMO_PIEZAS;
        }
        System.out.println("Valor total de piezas: " + valorTotal);
        return valorTotal >= Configuracion.ValorPiezas.VALOR_MINIMO_PIEZAS;
    }

    public Map<String, Integer> getListaPoderes() {
        return this.listaPoderes;
    }


    public void eliminarPoderUsado(String nombrePoder) {
        System.out.println("ANTES: " + listaPoderes);

        Integer usos = listaPoderes.get(nombrePoder);

        if (usos != null && usos > 0) {
            listaPoderes.put(nombrePoder, usos - 1); // Decrementar el uso
            System.out.println("DESPUES: " + listaPoderes);

        }
        if (listaPoderes.get(nombrePoder) == 0){
            listaPoderes.remove(nombrePoder);
            System.out.println("DESPUES2: " + listaPoderes);

        }
    }
    public void agregarPoder(String nombrePoder) {
        listaPoderes.put(nombrePoder, listaPoderes.getOrDefault(nombrePoder, 0) + 1); // Incrementar el uso
    }

    public boolean puedeUsarPoder(String nombrePoder) {
        return listaPoderes.getOrDefault(nombrePoder, 0) > 0; // Verifica si se puede usar el poder
    }


}



