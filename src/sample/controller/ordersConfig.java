package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ordersConfig implements Initializable {

    @FXML
    private TableView<ordersTable> ordersTable;

    @FXML
    private TableColumn<ordersTable, Timestamp> Ttime;

    @FXML
    private TableColumn<ordersTable, String> TcashierName;

    @FXML
    private TableColumn<ordersTable, Integer> TnetTotal;

    @FXML
    private TableColumn<ordersTable, Integer> Tdelivery;

    @FXML
    private TableColumn<ordersTable, Double> Tnetprice;

    @FXML
    private TableColumn<ordersTable, Double> Tdisc;

    @FXML
    private TableColumn<ordersTable, Double> Tprice;

    @FXML
    private TableColumn<ordersTable, String > Tlocation;

    @FXML
    private TableColumn<ordersTable, Integer> Tphone;

    @FXML
    private TableColumn<ordersTable, String > TuserName;

    @FXML
    private TableColumn<ordersTable, Integer> TorderNo;

    ObservableList<ordersTable> oblist = FXCollections.observableArrayList();
    public void setOrdersTable(){
        ResultSet dbResAllTotal;
        try {
            String sqlscript = "SELECT * from kunafahut.orderdetails ORDER BY orderTime DESC";
            dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
            while (dbResAllTotal.next()) {
                oblist.add(new ordersTable(dbResAllTotal.getInt("orderNo"),dbResAllTotal.getInt("clientPhone"), dbResAllTotal.getInt("delivery"),dbResAllTotal.getInt("totNetPrice"),dbResAllTotal.getDouble("price"),dbResAllTotal.getDouble("totDisc"),dbResAllTotal.getDouble("totPrice"),dbResAllTotal.getTimestamp("orderTime"),dbResAllTotal.getString("clientName"),dbResAllTotal.getString("clientLocation"),dbResAllTotal.getString("cachierName")));
            }
        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        TorderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
        TuserName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        Tphone.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));
        Tlocation.setCellValueFactory(new PropertyValueFactory<>("clientLocation"));
        Tprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        Tdisc.setCellValueFactory(new PropertyValueFactory<>("totDisc"));
        Tnetprice.setCellValueFactory(new PropertyValueFactory<>("totPrice"));
        Tdelivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        TnetTotal.setCellValueFactory(new PropertyValueFactory<>("totNetPrice"));
        TcashierName.setCellValueFactory(new PropertyValueFactory<>("cachierName"));
        Ttime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        ordersTable.setItems(oblist);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setOrdersTable();
    }
    public int idnoofselected(){
        int tableRowId = -1;
        int typed = 0;
        tableRowId = ordersTable.getSelectionModel().getSelectedIndex();
        if (!(tableRowId <= -1)) {
            typed = ordersTable.getSelectionModel().getSelectedItem().orderNo;
        }
        return typed;
    }
    public void droprowwithid(int typed){
        String sqlscript = "DELETE FROM `kunafahut`.`orderdetails` WHERE `orderNo` ="+typed+"";
        String sqlscript2 = "DELETE FROM `kunafahut`.`ordersdata` WHERE `orderNo` ="+typed+"";
        try {
            selling.initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sqlscript);
            selling.initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sqlscript2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ordersTable.getItems().clear();
        setOrdersTable();
    }
    public void dropTableRow(javafx.event.ActionEvent actionEvent){

        if (idnoofselected()!=0){
            droprowwithid(idnoofselected());
        }else {
            String selection = "من فضلك حدد الصف المراد حذفة ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void preEditing(int typed){
        String sqlscript1 = "DELETE FROM `kunafahut`.`preorder`;";
        String sendOrderData = "insert into preorder ( type, name, no, quantity, price, disc, netPrice)\n" +
                "select type, name, no, quantity, price, disc, netPrice from ordersdata WHERE `orderNo` ="+typed+";";
        String sqlscript2 = "DELETE FROM `kunafahut`.`ordersdata` WHERE `orderNo` ="+typed+";";
        try {
            selling.initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sqlscript1);
            selling.initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderData);
            selling.initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sqlscript2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void editOrder(javafx.event.ActionEvent actionEvent){
        if (idnoofselected()!=0){
            selling.setIsmod(true);
            int id = idnoofselected();
            selling.setIdmod(id);
            preEditing(id);
            Parent userview = null;
            try {
                userview = FXMLLoader.load(getClass().getResource("../fxml/selling.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                String selection = "خطأ في التعديل!!!! ";
                Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                alert.showAndWait();
            }
            Scene userscene = new Scene(userview);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(userscene);
            window.show();
        }else {
            String selection = "من فضلك حدد الصف المراد تعديلة ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void fastPrint(javafx.event.ActionEvent actionEvent){
        int id = idnoofselected();
        if (id!=0){
            userdata.outprint(id);
        }else {
            String selection = "من فضلك حدد الصف المراد تعديلة ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }

}
