package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import java.sql.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class signIn implements Initializable {
    public Statement dbstm;
    private ResultSet dbres;
    private ResultSet dbresLog;


    public String userdata;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button signButton;
    @FXML
    private Label loginText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void buttonAlert(javafx.event.ActionEvent actionEvent) throws SQLException {

        initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021");
        if (userName.getText().isEmpty() == true) {
            loginText.setText("            من فضلك قم بادخال اسم المستحدم وكلمة المرور            ");
        } else try {
            try {
                dbresLog = dbstm.executeQuery("SELECT * from aleef.user where username = '" + userName.getText() + "' AND password = '" + password.getText() + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String databaseUsername = "";
            String databasePassword = "";
            while (dbresLog.next()) {
                databaseUsername = dbresLog.getString("username");
                databasePassword = dbresLog.getString("password");
            }
            System.out.println(databaseUsername + "\n" + databasePassword + "\n" + password);


            if (userName.getText().equals(databaseUsername) && password.getText().equals(databasePassword)) {
                loginText.setText("             مبروك لقد تم تسجيل الدخول بنجاح           ");
                loginText.setTextFill(Color.GREEN);
                selling.setCashierName(databaseUsername);
                try {
                    Parent userview = FXMLLoader.load(getClass().getResource("../fxml/selling.fxml"));
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

            } else {
                loginText.setText("          من فضلك اعد المحاولة مرة اخري مع التاكد من المعلومات          ");
                loginText.setTextFill(Color.RED);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void initializeDB(String dburl,String dbuser,String dbpass) {
        // DB parameters
        Connection dbconn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
            dbconn = DriverManager.getConnection(dburl, dbuser, dbpass);
            System.out.println("DataBase connected");
            dbstm = dbconn.createStatement();
        } catch (ClassNotFoundException | SQLException var2) {
            System.err.println(var2.getMessage());
        }

    }
}

