package sample;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Player extends BorderPane {

    Media media;
    MediaPlayer player;
    MediaView view;
    Pane mpane;

    public Player(String filePath){
        media = new Media(new File(filePath).toURI().toString());
        player = new MediaPlayer(media);
        view = new MediaView(player);
        mpane = new Pane();

        mpane.getChildren().add(view);

        setCenter(mpane);

        player.play();

    }

}
