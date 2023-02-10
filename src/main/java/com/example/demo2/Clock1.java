package com.example.demo2;

import com.example.demo2.HelloApplication;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Clock1{

    Line seconds_hand = new Line();
    Line minutes_hand = new Line();
    Line hours_hand = new Line();

    @FXML private Pane pane;

    Circle DOT = new Circle();

    @FXML
    private ChoiceBox hoursChoice = new ChoiceBox();

    @FXML
    private ChoiceBox minutesChoice = new ChoiceBox();

    private static MediaPlayer tiktik;

    public void initialize(){

        Media lyd = new Media(String.valueOf(getClass().getResource("ticking_sound.mp3")));
        tiktik = new MediaPlayer(lyd);

        tiktik.setAutoPlay(true);
        tiktik.setCycleCount(MediaPlayer.INDEFINITE);

        tiktik.setStartTime(Duration.seconds(0));
        tiktik.setStopTime(Duration.seconds(4));


        tiktik.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                tiktik.seek(Duration.ZERO);
                tiktik.play();
            }
        });

        setInChoices();

        for (int count = 0; count < 60; ++count){
            Line min_indicator = new Line();
            min_indicator.setStartX(300.0f);
            min_indicator.setEndX(300.0f);
            min_indicator.setStartY(48.0f);
            min_indicator.setEndY(60.0f);
            min_indicator.setStrokeWidth(3.0);
            min_indicator.setStroke(Color.WHITE);

            min_indicator.getTransforms().add(Transform.rotate(count * 6, 300, 200));
            pane.getChildren().add(min_indicator);



        }

        for (int count = 0; count < 12; ++count){
            Line hour_indicator = new Line();
            hour_indicator.setStartX(300.0f);
            hour_indicator.setEndX(300.0f);
            hour_indicator.setStartY(48.5f);
            hour_indicator.setEndY(68.0f);
            hour_indicator.setStrokeWidth(6.0);
            hour_indicator.setStroke(Color.WHITE);

            hour_indicator.getTransforms().add(Transform.rotate(count * 30, 300, 200));
            pane.getChildren().add(hour_indicator);
        }

        int hour = java.time.LocalTime.now().getHour();
        int minute = java.time.LocalTime.now().getMinute();
        int second = java.time.LocalTime.now().getSecond();

        System.out.println(java.time.LocalTime.now().getMinute());

        double seconds_degree = second * 6;
        double minutes_degree = minute * 6;
        double hours_degree = hour * 30 + minute * .5;

        Rotate rotate_seconds = new Rotate(seconds_degree, 300.0f, 200.0f);
        Rotate rotate_minutes = new Rotate(minutes_degree, 300.0f, 200.0f);
        Rotate rotate_hours = new Rotate(hours_degree, 300.0f, 200.0f);

        seconds_hand.getTransforms().add(rotate_seconds);
        minutes_hand.getTransforms().add(rotate_minutes);
        hours_hand.getTransforms().add(rotate_hours);

        start();
    }

    class Operate extends TimerTask {

        public void run() {

            Rotate rotate = new Rotate(6, 300.0f, 200.0f);
            Rotate rotate_hours = new Rotate(.5, 300.0f, 200.0f);
            seconds_hand.getTransforms().add(rotate);

            if (java.time.LocalTime.now().getSecond() == 59) {
                minutes_hand.getTransforms().add(rotate);
                hours_hand.getTransforms().add(rotate_hours);
            }
        }
    }

    public void start() {

        Timer timer = new Timer(true);

        DOT.setCenterX(300.0f);
        DOT.setCenterY(200.0f);
        DOT.setRadius(2.0f);
        DOT.setStroke(Color.RED);
        DOT.setFill(Color.RED);
        DOT.setStrokeWidth(4);

        seconds_hand.setStartX(300.0f);
        seconds_hand.setStartY(250.0f);
        seconds_hand.setEndX(300.0f);
        seconds_hand.setEndY(50.0f);
        seconds_hand.setStroke(Color.RED);
        seconds_hand.setBlendMode(BlendMode.SRC_OVER);

        minutes_hand.setStartX(300.0f);
        minutes_hand.setStartY(220.0f);
        minutes_hand.setEndX(300.0f);
        minutes_hand.setEndY(58.0f);
        minutes_hand.setStroke(Color.WHITE);
        minutes_hand.setStrokeWidth(6);
        minutes_hand.setBlendMode(BlendMode.SRC_OVER);

        hours_hand.setStartX(300.0f);
        hours_hand.setStartY(220.0f);
        hours_hand.setEndX(300.0f);
        hours_hand.setEndY(110.0f);
        hours_hand.setStroke(Color.WHITE);
        hours_hand.setStrokeWidth(6);
        hours_hand.setBlendMode(BlendMode.SRC_OVER);

        Group root = new Group();

        root.getChildren().add(minutes_hand);
        root.getChildren().add(hours_hand);
        root.getChildren().add(seconds_hand);
        root.getChildren().add(DOT);

        pane.getChildren().add(root);

        timer.scheduleAtFixedRate(new Operate (), 0, 1000);

    }

    public void setInChoices()
    {
        minutesChoice.getItems().addAll("00","05", "10","15","20","25","30","35","40","45","50","55");
        hoursChoice.getItems().addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
    }


}