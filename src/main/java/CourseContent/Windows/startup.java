package main.java.CourseContent.Windows;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.CourseContent.CSV.CSVChanger;
import main.java.CourseContent.DataStructures.Course;

import java.util.List;

/*
 * The main.java.CourseContent.Windows.startup class is for loading all the required information for when the window is displayed.
 * This class will open a loading window and create multiple threads to retrieve each category of information
 * Then it will send the information to window where the rest of the program can use it
 */
public class startup extends Application {
    @Override
    public void start(Stage primaryStage) {
        //class to handle main.java.CourseContent.Windows.startup window before homepage window appears
        VBox titleBox = new VBox();
        Text banner = new Text("Click To\nStart");
        banner.setTextAlignment(TextAlignment.CENTER);
        Image bannerImage = new Image("file:DataFiles/CourseContent.png");
        ImageView imageView1 = new ImageView(bannerImage);
        titleBox.getChildren().add(imageView1);
        titleBox.getChildren().add(banner);
        titleBox.setAlignment(Pos.CENTER);
        banner.setFont(Font.font("URWGothicLSemi-Bold", FontWeight.BOLD, FontPosture.REGULAR, 35));
        banner.setFill(new Color(0.427, 0.51, 0.792, 1.0));

        primaryStage.setTitle("Course Content");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(titleBox, 300, 300, Color.BLACK));
        primaryStage.show();
        primaryStage.centerOnScreen();

        //text fade in then out
        FadeTransition ft = new FadeTransition(Duration.millis(3000), banner);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        //initialize courses from csv
        List<String[]> data = CSVChanger.read("courses.csv", 6);

        Course[] courses = new Course[data.size()];

        for (int i = 0; i < data.size(); i++) {
            courses[i] = new Course(data.get(i));
        }


        //multithreading
        Runnable win = new Window(courses);
        Thread window = new Thread(win);

        //when scene is pressed run window and close startup
        titleBox.setOnMouseClicked(e -> {
            window.run();
            primaryStage.close();
        });

    }
}