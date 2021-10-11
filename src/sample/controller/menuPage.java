package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class menuPage implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public  void gotoselling(javafx.event.ActionEvent actionEvent){
        try {
            Parent userview = FXMLLoader.load(menuPage.class.getResource("../fxml/selling.fxml"));
            Scene userscene = new Scene(userview);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(userscene);
            window.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public  void gotoordersConfig(javafx.event.ActionEvent actionEvent){
        try {
            Parent userview = FXMLLoader.load(menuPage.class.getResource("../fxml/ordersConfig.fxml"));
            Scene userscene = new Scene(userview);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(userscene);
            window.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public  void gotoDashboard(javafx.event.ActionEvent actionEvent){
        try {
            if (Main.isEnter()){
                Parent userview = FXMLLoader.load(menuPage.class.getResource("../fxml/dashboard.fxml"));
                Scene userscene = new Scene(userview);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(userscene);
                window.show();
            }else {
                String selection = "من فضلك ادخل الرقم السري الرئيسي ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public  void gotoitemConfig(javafx.event.ActionEvent actionEvent){
        try {
            if (Main.isEnter()){
                Parent userview = FXMLLoader.load(menuPage.class.getResource("../fxml/itemConfig.fxml"));
                Scene userscene = new Scene(userview);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(userscene);
                window.show();
            }else {
                String selection = "من فضلك ادخل الرقم السري الرئيسي ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }
    public  void gotouserConfig(javafx.event.ActionEvent actionEvent){
        try {
            if (Main.isEnter()){
                Parent userview = FXMLLoader.load(menuPage.class.getResource("../fxml/userConfig.fxml"));
                Scene userscene = new Scene(userview);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(userscene);
                window.show();
            }else {
                String selection = "من فضلك ادخل الرقم السري الرئيسي ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}
