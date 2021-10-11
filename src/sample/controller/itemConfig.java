package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.controller.userdata.userphone;

public class itemConfig implements Initializable {

    @FXML
    private TextField ifilter;

    @FXML
    private TableView<detailsTabel> detailsTabel;

    @FXML
    private TableColumn<detailsTabel, Double> tbfa;

    @FXML
    private TableColumn<detailsTabel, Double> tbno;

    @FXML
    private TableColumn<detailsTabel, Double> tbpr;

    @FXML
    private TableColumn<detailsTabel, String> tbnm;

    @FXML
    private TableColumn<detailsTabel, Double> tano;

    @FXML
    private TableColumn<detailsTabel, Double> tapr;

    @FXML
    private TableColumn<detailsTabel, String> tanm;

    @FXML
    private TableColumn<detailsTabel, String> tnm;

    @FXML
    private TableColumn<detailsTabel, String> tty;

    @FXML
    private TableColumn<detailsTabel, Long> tbr;

    @FXML
    private TextField ibr;

    @FXML
    private TextField inm;

    @FXML
    private TextField ibnm;

    @FXML
    private TextField ibpr;

    @FXML
    private TextField ibno;

    @FXML
    private TextField ity;

    @FXML
    private TextField ianm;

    @FXML
    private TextField iapr;

    @FXML
    private TextField iano;

    @FXML
    private TextField ibfa;


    ObservableList<detailsTabel> oblist2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUsersTable();
        ibr.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    fetchItem(Long.parseLong(ibr.getText()));
                }
            }
        });
    }

    public void menuPage(javafx.event.ActionEvent actionEvent){

        try {
            Parent userview = FXMLLoader.load(menuPage.class.getResource("../fxml/menuPage.fxml"));
            Scene userscene = new Scene(userview);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(userscene);
            window.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void update(javafx.event.ActionEvent actionEvent){
        try {
            Long bar = Long.parseLong(ibr.getText());
            String type =ity.getText();
            String name =inm.getText();
            String Aname =ianm.getText();
            Double Aprice =Double.valueOf(iapr.getText());
            Double Ano =Double.valueOf(iano.getText());
            String Bname =ibnm.getText();
            Double Bprice =Double.valueOf(ibpr.getText());
            Double Bno =Double.valueOf(ibno.getText());
            Double BfromA =Double.valueOf(ibfa.getText());
            if (type.isEmpty() || name.isEmpty() || Aprice.isNaN() || Ano.isNaN() || Bprice.isNaN() || Bno.isNaN()) {
                String selection = "من فضلك ادخل كل الخانات صحيحة اولاً ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }
            else {
                // coping data to another Field
                String sendOrderDetails = "UPDATE  aleef.details SET barcode = "+bar+",type =  '"+type+"',name = '"+name+"',Aname = '"+Aname+"',Aprice = "+Aprice+",Ano = "+Ano+",Bname = '"+Bname+"',Bprice = "+Bprice+",Bno = "+Bno+",BfromA = "+BfromA+" WHERE barcode = "+bar+";";
                try {
                    selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderDetails);
                } catch (SQLException e) {
                    String selection = "من فضلك ادخل كل الخانات صحيحة اولاً وتأكد من ادخال الباركود صحيح ";
                    Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                    alert.showAndWait();
                }
                detailsTabel.getItems().clear();
                setUsersTable();
            }
        }catch (Exception e){
            e.printStackTrace();
            String selection = "من فضلك ادخل كل الخانات صحيحة اولاً وتأكد من ادخال الباركود صحيح ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void delte(javafx.event.ActionEvent actionEvent){
        try {
            Long bar = Long.parseLong(ibr.getText());
            if (bar == null) {
                String selection = "من فضلك ادخل الباركود للسلعة المراد حذفها او تحديدها من الجدول ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }
            else {
                // coping data to another Field
                String sendOrderDetails = "DELETE FROM details WHERE barcode ="+bar+";";
                try {
                    selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderDetails);
                } catch (SQLException e) {
                    String selection = "من فضلك تاكد من ادخال الباركود للسلعة صحيحاً ";
                    Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                    alert.showAndWait();
                }
                detailsTabel.getItems().clear();
                setUsersTable();
            }
        }catch (Exception e){
            String selection = "من فضلك تاكد من ادخال الباركود للسلعة صحيحاً";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void add(javafx.event.ActionEvent actionEvent){
        try {
            Long bar = Long.parseLong(ibr.getText());
            String type =ity.getText();
            String name =inm.getText();
            String Aname =ianm.getText();
            Double Aprice =Double.valueOf(iapr.getText());
            Double Ano =Double.valueOf(iano.getText());
            String Bname =ibnm.getText();
            Double Bprice =Double.valueOf(ibpr.getText());
            Double Bno =Double.valueOf(ibno.getText());
            Double BfromA =Double.valueOf(ibfa.getText());
            if (type.isEmpty() || name.isEmpty() || Aprice.isNaN() || Ano.isNaN() || Bprice.isNaN() || Bno.isNaN()) {
                String selection = "من فضلك ادخل كل الخانات صحيحة اولاً ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }
            else {
                // coping data to another Field
                String sendOrderDetails = "INSERT INTO details(barcode,type,name,Aname,Aprice,Ano,Bname,Bprice,Bno,BfromA) values("+bar+",'"+type+"','"+name+"','"+Aname+"',"+Aprice+","+Ano+",'"+Bname+"',"+Bprice+","+Bno+","+BfromA+");";
                try {
                    selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderDetails);
                } catch (SQLException e) {
                    System.out.println(bar);
                    String selection = "من فضلك لا تدخل قيم مكررة ً ";
                    Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                    alert.showAndWait();
                }
                detailsTabel.getItems().clear();
                setUsersTable();
            }
        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
            String selection = "من فضلك ادخل كل الخانات صحيحة اولاً وتأكد من ادخال الباركود صحيح ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void fetchItem2(MouseEvent actionEvent){
        int tableRowId = -1;
        long bar = 0;
        tableRowId = detailsTabel.getSelectionModel().getSelectedIndex();
        if (!(tableRowId <= -1)) {
            bar = detailsTabel.getSelectionModel().getSelectedItem().barcode;
            fetchItem(bar);
        }
    }
    public void fetchItem(Long bar){
        ResultSet dbResAllTotal;
        try {
            String sqlscript = "SELECT * from aleef.details where barcode = "+bar+";";

            dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);

            while (dbResAllTotal.next()) {
                ibr.setText(String.valueOf(dbResAllTotal.getLong("barcode")));
                ity.setText(dbResAllTotal.getString("type"));
                inm.setText(dbResAllTotal.getString("name"));
                ianm.setText(dbResAllTotal.getString("Aname"));
                iapr.setText(String.valueOf(dbResAllTotal.getDouble("Aprice")));
                iano.setText(String.valueOf(dbResAllTotal.getDouble("Ano")));
                ibnm.setText(dbResAllTotal.getString("Bname"));
                ibpr.setText(String.valueOf(dbResAllTotal.getDouble("Bprice")));
                ibno.setText(String.valueOf(dbResAllTotal.getDouble("Bno")));
                ibfa.setText(String.valueOf(dbResAllTotal.getDouble("BfromA")));
            }

        }catch (Exception e){
            e.getCause();
        }
    }

    public void setUsersTable(){
        ResultSet dbResAllTotal2;
        try {
            String sqlscript2 = "SELECT * from aleef.details";
            dbResAllTotal2 = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript2);

            while (dbResAllTotal2.next()) {
                oblist2.add(new detailsTabel(dbResAllTotal2.getLong("barcode"),dbResAllTotal2.getString("type"),dbResAllTotal2.getString("name"),dbResAllTotal2.getString("Aname"), dbResAllTotal2.getDouble("Aprice"),dbResAllTotal2.getDouble("Ano"), dbResAllTotal2.getString("Bname"),dbResAllTotal2.getDouble("Bprice"),dbResAllTotal2.getDouble("Bno"),dbResAllTotal2.getDouble("BfromA")));
        }
        }catch (Exception e){
            e.getCause();
        }
        tbr.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        tty.setCellValueFactory(new PropertyValueFactory<>("type"));
        tnm.setCellValueFactory(new PropertyValueFactory<>("name"));
        tanm.setCellValueFactory(new PropertyValueFactory<>("Aname"));
        tapr.setCellValueFactory(new PropertyValueFactory<>("Aprice"));
        tano.setCellValueFactory(new PropertyValueFactory<>("Ano"));
        tbnm.setCellValueFactory(new PropertyValueFactory<>("Bname"));
        tbpr.setCellValueFactory(new PropertyValueFactory<>("Bprice"));
        tbno.setCellValueFactory(new PropertyValueFactory<>("Bno"));
        tbfa.setCellValueFactory(new PropertyValueFactory<>("BfromA"));
        detailsTabel.setItems(oblist2);
    }
}
