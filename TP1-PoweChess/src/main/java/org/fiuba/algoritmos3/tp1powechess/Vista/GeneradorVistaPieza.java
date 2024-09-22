package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza;

import java.util.Map;

import static org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion.ColoresJugadores;
import static org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion.ColoresJugadores.*;

public class GeneradorVistaPieza {

    final static Map<String, String> piezasNegras = Map.ofEntries(
            Map.entry("Caballo", "imagenes/caballo_negro.png"),
            Map.entry("Alfil", "imagenes/alfil_negro.png"),
            Map.entry("Torre", "imagenes/torre_negra.png"),
            Map.entry("Rey", "imagenes/rey_negro.png"),
            Map.entry("Reina", "imagenes/reina_negra.png"),
            Map.entry("Peon", "imagenes/peon_negro.png")
    );

    final static Map<String, String> piezasBlancas = Map.ofEntries(
            Map.entry("Caballo", "imagenes/caballo_blanco.png"),
            Map.entry("Alfil", "imagenes/alfil_blanco.png"),
            Map.entry("Torre", "imagenes/torre_blanca.png"),
            Map.entry("Rey", "imagenes/rey_blanco.png"),
            Map.entry("Reina", "imagenes/reina_blanca.png"),
            Map.entry("Peon", "imagenes/peon_blanco.png")
    );

    final static Map<ColoresJugadores, Map<String, String>> piezas = Map.ofEntries(
            Map.entry(BLANCO, piezasBlancas),
            Map.entry(NEGRO, piezasNegras)
    );

    public ImageView crearVistaPieza(Pieza pieza) {
        Map<String,String> piezasEnColor = piezas.get(pieza.getColor());
        String path = piezasEnColor.get(pieza.getPieceType());
        Image piezaImage = new Image(getClass().getResourceAsStream(path));
        ImageView vistaImagen = new ImageView();
        vistaImagen.setImage(piezaImage);
        return vistaImagen;
    }
}

