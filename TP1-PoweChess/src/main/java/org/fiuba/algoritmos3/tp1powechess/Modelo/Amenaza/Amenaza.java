package org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Amenaza {
    private Configuracion.ColoresJugadores color;
    private int[] direccion;
    private int cantidadCasilleros;
    private Coordenada2D posicionOrigenAmenaza;

    public Amenaza(Configuracion.ColoresJugadores color, int[] direccion, int cantidadCasilleros, Coordenada2D posicionOrigenAmenaza) {
        this.color = color;
        this.direccion = direccion;
        this.cantidadCasilleros = cantidadCasilleros;
        this.posicionOrigenAmenaza = posicionOrigenAmenaza;
    }

    public Configuracion.ColoresJugadores getColor() {
        return color;
    }

    public int[] getDireccion() {
        return direccion;
    }

    public int getCantidadCasilleros() {
        return cantidadCasilleros;
    }

    public Coordenada2D getPosicion() {return posicionOrigenAmenaza;}

    public boolean coordenadasEnDireccionAmenazada(int inicioX, int inicioY, int casillaX, int casillaY) {
        // Calcular las diferencias en las coordenadas
        int difX = casillaX - inicioX;
        int difY = casillaY - inicioY;

        // Verificar si las coordenadas están en la dirección especificada
        if (direccion[0] != 0 && direccion[1] != 0) { // Para movimientos diagonales
            return difX * direccion[1] == difY * direccion[0]; // Ambas diferencias deben ser proporcionales
        } else if (direccion[0] != 0) { // Movimiento horizontal
            return difY == 0 && difX * direccion[0] > 0; // Debe estar en la misma línea horizontal y moverse en la dirección correcta
        } else if (direccion[1] != 0) { // Movimiento vertical
            return difX == 0 && difY * direccion[1] > 0; // Debe estar en la misma línea vertical y moverse en la dirección correcta
        }

        return false; // Si la dirección no es válida
    }

    public boolean esDeColor(Configuracion.ColoresJugadores color) {
        return color.equals(this.color);
    }

    public boolean esIgualA(Amenaza otraAmenaza) {
        if (this == otraAmenaza) return true;
        if (otraAmenaza == null) return false;

        // Comparar color, dirección y cantidad de casilleros
        return this.color.equals(otraAmenaza.color) &&
                this.cantidadCasilleros == otraAmenaza.cantidadCasilleros &&
                this.direccion[0] == otraAmenaza.direccion[0] &&
                this.direccion[1] == otraAmenaza.direccion[1];
    }

    public boolean seExtiendeMasAllaDe(int numeroDeCasilleros) {
        return this.cantidadCasilleros > numeroDeCasilleros;
    }

    public boolean seExtiendeMenosOIgualA(int numeroDeCasilleros) {
        return this.cantidadCasilleros <= numeroDeCasilleros;
    }
}
