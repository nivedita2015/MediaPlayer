package sample;


import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;

public class MediaBar extends HBox {

    Slider time = new Slider();
    Slider vol = new Slider();

    Button play = new Button("||");

    Label volume = new Label("Volume: ");
    MediaPlayer player;


    public MediaBar(MediaPlayer mediaPlayer){
        player = mediaPlayer;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(5,10,5,10));

        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);

        HBox.setHgrow(time, Priority.ALWAYS);

        play.setPrefWidth(30);

        getChildren().add(play);
        getChildren().add(time);
        getChildren().add(volume);
        getChildren().add(vol);

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MediaPlayer.Status status = player.getStatus();

                if(status == MediaPlayer.Status.PLAYING){
                    if(player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())){
                        player.seek(player.getStartTime());
                        player.play();
                    }
                    else {
                        player.pause();
                        play.setText(">");
                    }
                }

                if(status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.HALTED || status == MediaPlayer.Status.STOPPED){
                    player.play();
                    play.setText("||");
                }


            }
        });

        player.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateValues();
            }


        });

        time.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(time.isPressed()){
                    player.seek(player.getMedia().getDuration().multiply(time.getValue()/100));

                }
            }
        });

        vol.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(vol.isPressed()){
                    player.setVolume(vol.getValue()/100);
                }
            }
        });
    }

    protected void updateValues(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                time.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
            }
        });
    }
}
