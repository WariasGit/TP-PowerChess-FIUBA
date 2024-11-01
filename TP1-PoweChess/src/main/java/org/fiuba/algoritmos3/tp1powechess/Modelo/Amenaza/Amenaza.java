package org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

/**
 * La clase Amenaza representa una amenaza generada por una pieza en el tablero.
 * Incluyendo el color de la pieza que genera la amenaza, la dirección en la que se extiende la amenaza,
 * la cantidad de casilleros alcanzados y la posición de origen de la amenaza.
 */
public class Amenaza {
    private Configuracion.ColoresJugadores color;
    private int[] direccion;
    private int cantidadCasilleros;
    private Coordenada2D posicionOrigenAmenaza;

    /**
     * @param color El color de la pieza que genera la amenaza.
     * @param direccion La dirección en la que se extiende la amenaza.
     * @param cantidadCasilleros  La cantidad de casilleros alcanzados por la amenaza.
     * @param posicionOrigenAmenaza La posición de origen de la amenaza en el tablero.
     */
    public Amenaza(Configuracion.ColoresJugadores color, int[] direccion, int cantidadCasilleros, Coordenada2D posicionOrigenAmenaza) {
        this.color = color;
        this.direccion = direccion;
        this.cantidadCasilleros = cantidadCasilleros;
        this.posicionOrigenAmenaza = posicionOrigenAmenaza;
    }

    /**
     * Obtiene la posición de origen de la amenaza en coordenadas 2D.
     * @return La posición de origen de la amenaza.
     */
    public Coordenada2D getPosicion() {return posicionOrigenAmenaza;}

    /**
     * Verifica si la amenaza es del color especificado.
     * @param color El color a verificar.
     * @return `true` si la amenaza es del color especificado; `false` en caso contrario.
     */
    public boolean esDeColor(Configuracion.ColoresJugadores color) {
        return color.equals(this.color);
    }

    /**
     * Compara esta amenaza con otra para verificar si son iguales.
     * @param otraAmenaza La amenaza con la cual se va a comparar.
     * @return `true` si las amenazas son iguales en color, dirección y cantidad de casilleros; `false` en caso contrario.
     */
    public boolean esIgualA(Amenaza otraAmenaza) {
        if (this == otraAmenaza) return true;
        if (otraAmenaza == null) return false;
        // Comparar color, dirección y cantidad de casilleros
        return this.color.equals(otraAmenaza.color) &&
                this.cantidadCasilleros == otraAmenaza.cantidadCasilleros &&
                this.direccion[0] == otraAmenaza.direccion[0] &&
                this.direccion[1] == otraAmenaza.direccion[1];
    }

    /**
     * Verifica si la amenaza se extiende más allá de un número especificado de casilleros.
     *
     * @param numeroDeCasilleros El número de casilleros límite.
     * @return `true` si la amenaza abarca más casilleros que el número especificado; `false` en caso contrario.
     */
    public boolean seExtiendeMasAllaDe(int numeroDeCasilleros) {
        return this.cantidadCasilleros > numeroDeCasilleros;
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

}
