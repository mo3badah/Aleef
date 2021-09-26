package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;



public class userConfig implements Initializable {
    private String adminPass  = "عالم اليف";
    @FXML
    private TableView<userTabel> usersTable;

    @FXML
    private TableColumn<userTabel, Timestamp> Ttime;

    @FXML
    private TableColumn<userTabel, Integer> Tphone;

    @FXML
    private TableColumn<userTabel, String> Tname;


    @FXML
    private TextField TuserName;

    @FXML
    private TextField Tuserphone;

    @FXML
    private TextField Tuserpass;
    ObservableList<userTabel> oblist = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        setUsersTable();

    }
    public void setUsersTable(){
        ResultSet dbResAllTotal;
        try {
            String sqlscript = "SELECT * from aleef.user";
            dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
            while (dbResAllTotal.next()) {
                oblist.add(new userTabel(dbResAllTotal.getString("username"),dbResAllTotal.getString("password"), dbResAllTotal.getInt("phone"),dbResAllTotal.getTimestamp("create_time")));
            }

        }catch (Exception e){
            e.getCause();
        }
        Tname.setCellValueFactory(new PropertyValueFactory<>("username"));
        Tphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Ttime.setCellValueFactory(new PropertyValueFactory<>("create_time"));
        usersTable.setItems(oblist);
    }
    public void menuPage(javafx.event.ActionEvent actionEvent){

        try {
            Parent userview = FXMLLoader.load(menuPage.class.getResource("../fxml/menuPage.fxml"));
            Scene userscene = new Scene(userview);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(userscene);
            window.setFullScreen(true);
            window.setFullScreenExitHint("");
            window.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void fetchItem1(MouseEvent actionEvent){
        int tableRowId = -1;
        String userName = "";
        tableRowId = usersTable.getSelectionModel().getSelectedIndex();
        if (!(tableRowId <= -1)) {
            userName = usersTable.getSelectionModel().getSelectedItem().username;
            ResultSet dbResAllTotal;
            try {
                String sqlscript = "SELECT * from aleef.user where  username='"+userName+"';";

                dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);

                while (dbResAllTotal.next()) {
                    TuserName.setText(dbResAllTotal.getString("username"));
                    Tuserphone.setText(dbResAllTotal.getString("phone"));
                    Tuserpass.setText(dbResAllTotal.getString("password"));
                }

            }catch (Exception e){
                e.getCause();
            }
        }
    }
    public void update1(javafx.event.ActionEvent actionEvent){
        try {
            String userName =TuserName.getText();
            String password =Tuserpass.getText();
            int phone = Integer.parseInt(Tuserphone.getText());
            if (userName.isEmpty() || password.isEmpty()) {
                String selection = "من فضلك ادخل  اسم المستحدم و الرقم السري للتعديلً ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }
            else {
                // coping data to another Field
                String sendOrderDetails = "UPDATE aleef.user SET username = '"+userName+"',password = '"+password+"',phone = "+phone+", create_time = current_timestamp WHERE username='"+userName+"';";
                try {
                    selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderDetails);
                } catch (SQLException e) {
                    e.printStackTrace();
                    e.getCause();
                }
                usersTable.getItems().clear();
                setUsersTable();
            }
        }catch (Exception e){
            String selection = "من فضلك ادخل كل الخانات صحيحة اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void add2(javafx.event.ActionEvent actionEvent){
        try {
            String userName =TuserName.getText();
            String password =Tuserpass.getText();
            int phone = Integer.parseInt(Tuserphone.getText());
            if (userName.isEmpty() || password.isEmpty()) {
                String selection = "من فضلك ادخل  اسم المستحدم و الرقم السري المراد اضافته اولاً ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }
            else {
                // coping data to another Field
                String sendOrderDetails = "INSERT INTO user (username,phone, password, create_time) values ('"+userName+"',"+phone+",'"+password+"',current_timestamp );";
                try {
                    selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderDetails);
                } catch (SQLException e) {
                    String selection = "من فضلك لا تدخل قيم مكررة ً ";
                    Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                    alert.showAndWait();
                }
                usersTable.getItems().clear();
                setUsersTable();
            }
        }catch (Exception e){
            String selection = "من فضلك ادخل كل الخانات صحيحة اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }


    public void delte2(javafx.event.ActionEvent actionEvent){
        try {
            String userName =TuserName.getText();
            String password =Tuserpass.getText();
            if (userName.isEmpty() || password.isEmpty()) {
                String selection = "من فضلك ادخل اسم المستخدم و الرقم السري صحيح اولا";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }
            else {
                // coping data to another Field
                String sendOrderDetails = "DELETE FROM aleef.user WHERE username='"+userName+"';";
                try {
                    selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderDetails);
                } catch (SQLException e) {
                    e.printStackTrace();
                    e.getCause();
                }
                usersTable.getItems().clear();
                setUsersTable();
            }
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع والاسم المراد حذفة ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }



}
