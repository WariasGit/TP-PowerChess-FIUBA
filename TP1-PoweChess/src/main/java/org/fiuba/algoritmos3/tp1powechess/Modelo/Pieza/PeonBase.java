package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public abstract class PeonBase extends Pieza {

    public PeonBase(Configuracion.ColoresJugadores color) {
        super(color);
        this.tipoDePieza = Constantes.PEON;
        this.movimientosPosibles = new ArrayList<>();
        this.maxDistanciaDeAmenaza = Constantes.MINIMA_DISTANCIA;

        // Definimos las direcciones de movimiento del peón
        direccionesDeMovimiento = new ArrayList<>();
        direccionesDeMovimiento.add(new int[]{getDireccion(), Constantes.CERO_EN_COLUMNA});   // Movimiento hacia adelante
        direccionesDeMovimiento.add(new int[]{Constantes.DOS_EN_FILA * getDireccion(), Constantes.CERO_EN_COLUMNA}); // Movimiento inicial doble
    }

    public String getTipoDePieza() {
        return "Peon";
    }

    public boolean esMovimientoValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado) {
        Coordenada2D diferenciasCoordenadas = coordenadaFinal.calcularDiferenciaCon(coordenadaInicial);

        Casillero casilleroFinal = tableroCuadrado.getCasillero(coordenadaFinal.getX(),coordenadaFinal.getY());

        if (!casilleroFinal.estaOcupado() && tableroCuadrado.caminoEstaDesocupado(coordenadaInicial.getX(),coordenadaInicial.getY(),coordenadaFinal.getX(),coordenadaFinal.getY())){
            if(esDireccionDeMovimientoValida(diferenciasCoordenadas.getX(), diferenciasCoordenadas.getY())){
                this.seHaMovido = true;  // Si el movimiento es válido, marcamos que el peón se ha movido
                return true;
            };
        } else if (!casilleroFinal.getPieza().esDelMismoColorQue(this) && tableroCuadrado.caminoEstaDesocupado(coordenadaInicial.getX(),coordenadaInicial.getY(),coordenadaFinal.getX(),coordenadaFinal.getY())) {
            if(esCapturaValida(coordenadaInicial.getX(),coordenadaInicial.getY(),coordenadaFinal.getX(),coordenadaFinal.getY())){
                this.seHaMovido = true;  // Si el movimiento es válido, marcamos que el peón se ha movido
                return true;
            };
        }
        return false;
    }

    public boolean esCapturaValida(int inicioX, int inicioY, int finX, int finY) {
        for (int[] direccion : direccionesDeAmenaza) {
            Amenaza amenaza = new Amenaza(color, direccion, getMaxDistanciaDeAmenaza());
            if (amenaza.coordenadasEnDireccionAmenazada(inicioX, inicioY, finX, finY)) {
                return true;
            }
        }
        return false;
    }

    // Meetodo para verificar si una dirección está en las direcciones de movimiento permitidas
    protected boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : direccionesDeMovimiento) {
            // Solo debe moverse hacia adelante (sin cambiar la X)
            if (direccion[0] == difX && direccion[1] == difY) {
                // El peón puede moverse 1 o 2 casillas adelante solo si no ha sido movido
                return (!this.seHaMovido || direccion[1] != 2 * getDireccion());
            }
        }
        return false;
    }

    // Metodo abstracto para obtener la dirección de movimiento del peón (positivo o negativo según el color)
    protected abstract int getDireccion();
}