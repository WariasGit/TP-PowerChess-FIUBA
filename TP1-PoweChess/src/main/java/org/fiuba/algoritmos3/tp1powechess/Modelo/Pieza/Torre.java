package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable.Enrocable;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoNormal;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;


import java.util.ArrayList;

public class Torre extends Pieza implements Enrocable {
    protected boolean enrocado = false;

    public Torre(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.TORRE;
        this.maxDistanciaDeAmenaza = Constantes.MAXIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();
        this.valor = Configuracion.ValorPiezas.VALOR_TORRE;
        asignarCaracterFEN(Configuracion.CaracterFenParaPiezas.TORRE_BLANCA, Configuracion.CaracterFenParaPiezas.TORRE_NEGRA);
        // Inicializamos las direcciones de movimiento
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{Constantes.CERO_EN_FILA, Constantes.UNO_EN_COLUMNA});   // Derecha
        this.direccionesDeMovimiento.add(new int[]{Constantes.CERO_EN_FILA, -Constantes.UNO_EN_COLUMNA});  // Izquierda
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA});   // Arriba
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA});  // Abajo

        // Para la Torre, las direcciones de movimiento y de amenaza son las mismas
        this.direccionesDeAmenaza = new ArrayList<>(this.direccionesDeMovimiento);

        // Al crear una Torre le asignamos su estrategia de enroque
        this.estrategiaDeMovimiento = new MovimientoNormal();
    }

    public void enrocarSegunEnroqueDerecho(TableroCuadrado tableroCuadrado, int row) {
        tableroCuadrado.removePieza(posicionActual);
        Coordenada2D nuevaPosicion = new Coordenada2D(row,5);
        tableroCuadrado.setPieza(nuevaPosicion,this);
        System.out.println("Actualizo la posicion de la torre");
        System.out.println("Posicion actual: " + posicionActual.getRow() + "," + posicionActual.getCol());
        System.out.println("Posicion nueva: " + nuevaPosicion.getRow() + nuevaPosicion.getCol() + "\n");
        enrocado = true;
    }

    public void enrocarSegunEnroqueIzquierdo(TableroCuadrado tableroCuadrado, int row) {
        tableroCuadrado.removePieza(posicionActual);
        Coordenada2D nuevaPosicion = new Coordenada2D(row,3);
        tableroCuadrado.setPieza(nuevaPosicion,this);
        System.out.println("Actualizo la posicion de la torre");
        System.out.println("Posicion actual: " + posicionActual.getRow() + "," + posicionActual.getCol());
        System.out.println("Posicion nueva: " + nuevaPosicion.getRow() + nuevaPosicion.getCol() + "\n");
        enrocado = true;
    }

    public boolean seHaEnrocado(){return enrocado;}
}