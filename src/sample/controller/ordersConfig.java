package sample.controller;

import io.github.escposjava.PrinterService;
import io.github.escposjava.print.NetworkPrinter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    @FXML
    private TextField ifilter;

    ObservableList<ordersTable> oblist = FXCollections.observableArrayList();

    public void setOrdersTable(){
        ResultSet dbResAllTotal;
        try {
            String sqlscript = "SELECT * from aleef.orderdetails ORDER BY orderTime DESC";
            dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
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

        // filtered list
        FilteredList<ordersTable> filterData = new FilteredList<>(oblist, b -> true);
        ifilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(ordersTable1 -> {
                if (newValue.isEmpty() || newValue==null){
                    return true;
                }
                String searchKeyWord = newValue.toLowerCase();
                if (ordersTable1.getClientName().toLowerCase().contains(searchKeyWord)){
                    return true;
                }
                else if (ordersTable1.getClientLocation().toLowerCase().contains(searchKeyWord)){
                    return true;
                }
                else if (String.valueOf(ordersTable1.getOrderNo()).contains(searchKeyWord)){
                    return true;
                }
                else if (String.valueOf(ordersTable1.getClientPhone()).contains(searchKeyWord)){
                    return true;
                }
                else {
                    return false;
                }
            });
        } );
        SortedList<ordersTable> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(ordersTable.comparatorProperty());
        ordersTable.setItems(sortedData);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setOrdersTable();
//        NetworkPrinter printer = new NetworkPrinter("192.168.0.100", 9100);
////        PrinterService printerService = new PrinterService(printer);
////        printerService.print("                    عالم أليف                     ");
////        printerService.cutFull();
////        printerService.close();
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

    public static void droprowwithid(int typed){
        returnToStock(typed);
        String sqlscript = "DELETE FROM `aleef`.`orderdetails` WHERE `orderNo` ="+typed+"";
        String sqlscript2 = "DELETE FROM `aleef`.`orders` WHERE `orderNo` ="+typed+"";
        try {
            selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sqlscript);
            selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sqlscript2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void droprowwithid2(){
        ordersTable.getItems().clear();
        setOrdersTable();
    }

    public void dropTableRow(javafx.event.ActionEvent actionEvent){

        if (idnoofselected()!=0){
            droprowwithid(idnoofselected());
            droprowwithid2();
        }else {
            String selection = "من فضلك حدد الصف المراد حذفة ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public static void returnToStock(int id){
        try {
            String sqlscript3 = "SELECT * FROM `aleef`.`orders` WHERE `orderNo` ="+id+";";
            // for return the value of the elements to the stock
            ResultSet dbResGetId = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript3);
            while (dbResGetId.next()){
                Long barcode = dbResGetId.getLong("barcode");
                double no = dbResGetId.getDouble("no");
                String quantity = dbResGetId.getString("quantity");
                sample.controller.selling.sync(barcode,quantity,no, true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preEditing(int typed){
        String sqlscript1 = "DELETE FROM `aleef`.`preorder`;";
        String sendOrderData = "insert into preorder (barcode, type, name, no, quantity, price, disc, netPrice)\n" +
                "select barcode, type, name, no, quantity, price, disc, netPrice from orders WHERE `orderNo` ="+typed+";";
        returnToStock(typed);
        String sqlscript2 = "DELETE FROM `aleef`.`orders` WHERE `orderNo` ="+typed+";";
        try {
            selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sqlscript1);
            selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderData);
            selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sqlscript2);
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
