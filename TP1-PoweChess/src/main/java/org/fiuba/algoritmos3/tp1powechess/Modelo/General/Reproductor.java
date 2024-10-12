package org.fiuba.algoritmos3.tp1powechess.Modelo.General;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

public class Reproductor {
    private MediaPlayer reproductorMenu;
    private MediaPlayer reproductorJuego;

    public Reproductor() {
        String musicaMenu = getClass().getResource(Constantes.RUTA_MUSICA_MENU).toExternalForm();
        Media menuMedia = new Media(musicaMenu);
        reproductorMenu = new MediaPlayer(menuMedia);
        String musicaJuego = getClass().getResource(Constantes.RUTA_MUSICA_JUEGO).toExternalForm();
        Media gameMedia = new Media(musicaJuego);
        reproductorJuego = new MediaPlayer(gameMedia);

        reproductorMenu.setCycleCount(MediaPlayer.INDEFINITE);
        reproductorJuego.setCycleCount(MediaPlayer.INDEFINITE);
        reproductorMenu.setVolume(0.5);
        reproductorJuego.setVolume(0.5);
    }

    public void reproducirMusicaMenu() {
        detenerMusicaJuego();  // Detener la música del juego si está sonando
        reproductorMenu.play();
    }

    public void reproducirMusicaJuego() {
        detenerMusicaMenu();
        reproductorJuego.play();
    }

    public void detenerMusicaMenu() {
        reproductorMenu.stop();
    }

    public void detenerMusicaJuego() {
        reproductorJuego.stop();
    }
}
