package org.fiuba.algoritmos3.tp1powechess.Vista;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.io.InputStream;
import java.util.Map;


public class GeneradorVistaPieza {
    public ImageView crearVistaPieza(Pieza pieza) {
        Map<String,String> piezasEnColor = Configuracion.pathPiezas.get(pieza.getColor());
        String path = piezasEnColor.get(pieza.getTipoDePieza());
        InputStream inputStream = getClass().getResourceAsStream("/org/fiuba/algoritmos3/tp1powechess/" + path);
        if (inputStream == null) {
            throw new RuntimeException("Ruta del recurso: " + "/org/fiuba/algoritmos3/tp1powechess/" + path);
        }
        Image piezaImage = new Image(inputStream);
        ImageView vistaImagen = new ImageView();
        vistaImagen.setImage(piezaImage);
        return vistaImagen;
    }
}

