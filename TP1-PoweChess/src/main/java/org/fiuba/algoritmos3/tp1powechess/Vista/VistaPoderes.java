package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.io.InputStream;

public class VistaPoderes {
    private final StackPane[][] posiciones;

    public VistaPoderes(StackPane[][] posiciones) {
        this.posiciones = posiciones;
    }

    private void agregarSticker(int fila, int columna, String rutaSticker) {
        StackPane celdaPrincipal = this.posiciones[fila][columna];
        StackPane celdaSecundaria = (StackPane) celdaPrincipal.getChildren().get(1);
        // Crear el ImageView para el sticker
        InputStream inputStream = getClass().getResourceAsStream(rutaSticker);
        ImageView sticker = new ImageView(new Image(inputStream));
        sticker.setFitWidth(20);
        sticker.setFitHeight(20);
        sticker.setPreserveRatio(true);
        sticker.setId("sticker");
        StackPane.setAlignment(sticker, Pos.TOP_RIGHT);
        celdaSecundaria.getChildren().add(sticker);
    }

    public void quitarSticker(int fila, int columna) {
        StackPane celdaPrincipal = this.posiciones[fila][columna];
        StackPane celdaSecundaria = (StackPane) celdaPrincipal.getChildren().get(1);
        // Buscar el ImageView con el ID "sticker" y eliminarlo
        celdaSecundaria.getChildren().removeIf(node -> node instanceof ImageView && "sticker".equals(node.getId()));
    }

    public void setEscudo(int fila, int columna) {
        agregarSticker(fila, columna, Constantes.RUTA_STICKER_ESCUDO);
    }

    public void setAlas(int fila, int columna) {
        agregarSticker(fila, columna, Constantes.RUTA_STICKER_ALAS);
    }

    public void setMovimientoDoble(int fila, int columna) {
        agregarSticker(fila, columna, Constantes.RUTA_STICKER_DOBLE);
    }

    public void setCongelado(int fila, int columna) {
        agregarSticker(fila, columna, Constantes.RUTA_STICKER_CONGELADO);
    }

    public void setEvolucion(int fila, int columna) {
        agregarSticker(fila, columna, Constantes.RUTA_STICKER_EVOLUCION);
    }

    public void quitarPoder(int fila, int columna) {quitarSticker(fila, columna);}
}
