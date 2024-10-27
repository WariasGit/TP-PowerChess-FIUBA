package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.InputStream;

public class VistaPoderes {
    private final StackPane[][] posiciones;

    public VistaPoderes(StackPane[][] posiciones) {
        this.posiciones = posiciones;
    }

    public void setEscudo(int fila, int columna) {
        StackPane celdaPrincipal = this.posiciones[fila][columna];
        StackPane celdaSecundaria = (StackPane) celdaPrincipal.getChildren().get(1);
        // Crear el ImageView para el sticker de escudo
        InputStream inputStream = getClass().getResourceAsStream("/org/fiuba/algoritmos3/tp1powechess/StickersPoderes/escudo.png");
        ImageView stickerEscudo = new ImageView(new Image(inputStream));
        stickerEscudo.setFitWidth(20);  // Ajusta el tamaño según sea necesario
        stickerEscudo.setFitHeight(20);
        stickerEscudo.setPreserveRatio(true);
        // Establecer un ID para identificar el sticker
        stickerEscudo.setId("stickerEscudo");
        // Posiciona el sticker (opcional: puedes cambiar la posición en el StackPane)
        StackPane.setAlignment(stickerEscudo, Pos.TOP_RIGHT);
        // Agregar el sticker a la celda
        celdaSecundaria.getChildren().add(stickerEscudo);
    }

    public void setAlas(int fila, int columna) {
        StackPane celdaPrincipal = this.posiciones[fila][columna];
        StackPane celdaSecundaria = (StackPane) celdaPrincipal.getChildren().get(1);
        // Crear el ImageView para el sticker de escudo
        InputStream inputStream = getClass().getResourceAsStream("/org/fiuba/algoritmos3/tp1powechess/StickersPoderes/alas.png");
        ImageView stickerEscudo = new ImageView(new Image(inputStream));
        stickerEscudo.setFitWidth(20);  // Ajusta el tamaño según sea necesario
        stickerEscudo.setFitHeight(20);
        stickerEscudo.setPreserveRatio(true);
        // Establecer un ID para identificar el sticker
        stickerEscudo.setId("stickerEscudo");
        // Posiciona el sticker (opcional: puedes cambiar la posición en el StackPane)
        StackPane.setAlignment(stickerEscudo, Pos.TOP_RIGHT);
        // Agregar el sticker a la celda
        celdaSecundaria.getChildren().add(stickerEscudo);
    }

    public void setMovimientoDoble(int fila, int columna){
        StackPane celdaPrincipal = this.posiciones[fila][columna];
        StackPane celdaSecundaria = (StackPane) celdaPrincipal.getChildren().get(1);
        // Crear el ImageView para el sticker de escudo
        InputStream inputStream = getClass().getResourceAsStream("/org/fiuba/algoritmos3/tp1powechess/StickersPoderes/doble.png");
        ImageView stickerEscudo = new ImageView(new Image(inputStream));
        stickerEscudo.setFitWidth(20);  // Ajusta el tamaño según sea necesario
        stickerEscudo.setFitHeight(20);
        stickerEscudo.setPreserveRatio(true);
        // Establecer un ID para identificar el sticker
        stickerEscudo.setId("stickerEscudo");
        // Posiciona el sticker (opcional: puedes cambiar la posición en el StackPane)
        StackPane.setAlignment(stickerEscudo, Pos.TOP_RIGHT);
        // Agregar el sticker a la celda
        celdaSecundaria.getChildren().add(stickerEscudo);
    }

    public void setCongelado(int fila, int columna) {
        StackPane celdaPrincipal = this.posiciones[fila][columna];
        StackPane celdaSecundaria = (StackPane) celdaPrincipal.getChildren().get(1);
        // Crear el ImageView para el sticker de escudo
        InputStream inputStream = getClass().getResourceAsStream("/org/fiuba/algoritmos3/tp1powechess/StickersPoderes/congelado.png");
        ImageView stickerEscudo = new ImageView(new Image(inputStream));
        stickerEscudo.setFitWidth(20);  // Ajusta el tamaño según sea necesario
        stickerEscudo.setFitHeight(20);
        stickerEscudo.setPreserveRatio(true);
        // Establecer un ID para identificar el sticker
        stickerEscudo.setId("stickerEscudo");
        // Posiciona el sticker (opcional: puedes cambiar la posición en el StackPane)
        StackPane.setAlignment(stickerEscudo, Pos.TOP_RIGHT);
        // Agregar el sticker a la celda
        celdaSecundaria.getChildren().add(stickerEscudo);
    }

    public void setEvolucion(int fila, int columna) {
        StackPane celdaPrincipal = this.posiciones[fila][columna];
        StackPane celdaSecundaria = (StackPane) celdaPrincipal.getChildren().get(1);
        // Crear el ImageView para el sticker de escudo
        InputStream inputStream = getClass().getResourceAsStream("/org/fiuba/algoritmos3/tp1powechess/StickersPoderes/evolucion.png");
        ImageView stickerEscudo = new ImageView(new Image(inputStream));
        stickerEscudo.setFitWidth(20);  // Ajusta el tamaño según sea necesario
        stickerEscudo.setFitHeight(20);
        stickerEscudo.setPreserveRatio(true);
        // Establecer un ID para identificar el sticker
        stickerEscudo.setId("stickerEscudo");
        // Posiciona el sticker (opcional: puedes cambiar la posición en el StackPane)
        StackPane.setAlignment(stickerEscudo, Pos.TOP_RIGHT);
        // Agregar el sticker a la celda
        celdaSecundaria.getChildren().add(stickerEscudo);
    }
}
