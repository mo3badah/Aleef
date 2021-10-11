package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EventObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {
    Stage primarystage;
    static Parent root;

    public static void setConsoleVisible() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primarystage = primaryStage;
        root = FXMLLoader.load(getClass().getResource("fxml/itemConfig.fxml"));
        primarystage.setTitle("عالم أليف");
        primaryStage.setMaximized(true);
        primarystage.setScene(new Scene(root));
        primarystage.show();

    }

    public static void main(String[] args) {
        launch(args);


    }

    public static void showuserdata() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/dashboard.fxml"));
        BorderPane userdata = loader.load();
        Stage userdatastage;


    }

    public static String getValidation() {
        final String[] x = {""};
        // create a text input dialog
        TextInputDialog td = new TextInputDialog();
        td.showAndWait();
        x[0] = td.getEditor().getText();
        return x[0];
    }
    public static boolean isEnter(){
        final String pass = "عالم اليف";
        String x = Main.getValidation();
        if (x.equals(pass)){
            return true;
        }
        else {
            return false;
        }
    }
}
