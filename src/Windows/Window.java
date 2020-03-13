package Windows;

import DataStructures.Course;
import Pages.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*
 * This class is for the main window that will be displayed on screen at all times.
 * When a button is pressed the window its does not change but the center of the border pane is changed to the page
 * corresponding to the button
 */
public class Window extends Application implements Runnable {
    public static Course[] courses;

    public Window(Course[] c) {
        courses = c;
    }

    @Override
    public void run() {
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        //window setup
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        BorderPane mainPane = new BorderPane();
        HBox topPane = new HBox();

        Button b1 = new Button("Home");
        Button b2 = new Button("My Courses");
        Button b3 = new Button("Schedule");
//        Button b4 = new Button("To Do");
        Button b5 = new Button("My Grades");
//        Button b6 = new Button("Assignments");
//        Button b7 = new Button("Midterms");
//        Button b8 = new Button("Final Exams");
        MenuButton b9 = new MenuButton("Add Info");

        b1.setPrefSize(screen.getWidth() / 5, screen.getHeight() * 0.05);
        b1.setOnMouseClicked(e -> changePage(primaryStage, mainPane, "Home"));

        b2.setPrefSize(screen.getWidth() / 5, screen.getHeight() * 0.05);
        b2.setOnMouseClicked(e -> changePage(primaryStage, mainPane, "Courses"));

        b3.setPrefSize(screen.getWidth() / 5, screen.getHeight() * 0.05);
        b3.setOnMouseClicked(e -> changePage(primaryStage, mainPane, "Schedule"));

        b5.setPrefSize(screen.getWidth() / 5, screen.getHeight() * 0.05);
        b5.setOnMouseClicked(e -> changePage(primaryStage, mainPane, "Grades"));

        b9.setPrefSize(screen.getWidth() / 5, screen.getHeight() * 0.05);
        MenuItem course = new MenuItem("Add Course");
        course.setOnAction(e -> {
            //start new thread to open window
            Runnable addInfo = new AddCoursePage();
            Thread addInfoThread = new Thread(addInfo);
            addInfoThread.run();
        });
        MenuItem assignment = new MenuItem("Add Assignment");
        MenuItem midterm = new MenuItem("Add Midterm");
        MenuItem exam = new MenuItem("Add Exam");

        b9.getItems().addAll(course, assignment, midterm, exam);

        //add buttons
        topPane.getChildren().addAll(b1, b2, b3, b5, b9);
        topPane.setAlignment(Pos.CENTER);
        mainPane.setTop(topPane);
        HomePage home = new HomePage();
        mainPane.setCenter(home.getMainPane());

        //final window setup
        primaryStage.setTitle("Course Content");
        primaryStage.setMinWidth(screen.getWidth() / 2);
        primaryStage.setMinHeight(screen.getHeight() / 2);
        primaryStage.setScene(new Scene(mainPane, screen.getWidth() * 0.66, screen.getHeight() * 0.66));
        primaryStage.show();

    }

    public void changePage(Stage stage, BorderPane pane, String page) {
        switch (page) {
            case "Home":
                stage.setTitle("Home");
                HomePage home = new HomePage();
                pane.setCenter(home.getMainPane());

                break;
            case "Courses":
                stage.setTitle("My Courses");
                CoursesPage cPage = new CoursesPage();
                pane.setCenter(cPage.getMainPane());
                break;
            case "Schedule":
                stage.setTitle("Schedule");
                //
                pane.setCenter(new TextArea());

                break;
            case "Grades":
                stage.setTitle("Grades");
                GradesPage gPage = new GradesPage();
                pane.setCenter(gPage.getMainPane());

                break;

        }
    }
}
