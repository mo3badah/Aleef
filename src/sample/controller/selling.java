package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import sample.Main;

import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.util.Date;

public class selling implements Initializable {
    public static int getIdgenerate() {
        return idgenerate;
    }

    public static void setIdgenerate(int idgenerate) {
        selling.idgenerate = idgenerate;
    }

    public static int idgenerate;

    public static int getIdmod() {
        return idmod;
    }

    public static void setIdmod(int idmod1) {
        idmod = idmod1;
    }

    private static int idmod;

    public static boolean isIsmod() {
        return ismod;
    }

    public static void setIsmod(boolean ismod1) {
        ismod = ismod1;
    }

    private static boolean ismod;

    public static String getCashierName() {
        return cashierName;
    }

    public static void setCashierName(String cashierName) {
        selling.cashierName = cashierName;
    }

    public static String cashierName = "admin";

    @FXML
    private Button addOne;

    @FXML
    private ToggleGroup type7;

    @FXML
    private RadioButton Cdiscratio;

    @FXML
    private RadioButton Free;

    @FXML
    private Button change;

    @FXML
    private Button clearAll;

    @FXML
    private Button printing2;

    @FXML
    private TextField TuserName;

    @FXML
    private TextField Tphone;

    @FXML
    private TextField Tlocation;

    @FXML
    private TextField Tnote;

    @FXML
    private TextField setNow;

    @FXML
    private TextField setId;

    @FXML
    private TextField ibr;

    @FXML
    private TextField ity;

    @FXML
    private TextField inm;

    @FXML
    private ChoiceBox iqun;


    @FXML
    private TextField ino;

    @FXML
    private TextField iprice;


    @FXML
    private ToggleGroup type;
    @FXML
    private ToggleGroup type3;
    @FXML
    private TextField onePrice;
    @FXML
    private TextField gramno;
    @FXML
    private TextField piecesno;
    @FXML
    private TextField midno;
    @FXML
    private TextField bigno;
    @FXML
    private TextField newno;
    @FXML
    private TextField Ediscratio;
    @FXML
    private TextField Ediscno;
    @FXML
    private CheckBox Orderdisc;
    @FXML
    private TableView<moderTabel> sellingTable;
    @FXML
    private TableColumn<moderTabel, Long> Tbar;
    @FXML
    private TableColumn<moderTabel, String> Ttype;
    @FXML
    private TableColumn<moderTabel, String> Tname;
    @FXML
    private TableColumn<moderTabel, Double> Tno;
    @FXML
    private TableColumn<moderTabel, String> Tquantity;
    @FXML
    private TableColumn<moderTabel, Double> Tprice;
    @FXML
    private TableColumn<moderTabel, Double> Tdisc;
    @FXML
    private TableColumn<moderTabel, Double> Tnetprice;
    ObservableList<moderTabel> oblist = FXCollections.observableArrayList();
    @FXML
    private TextField TallTotal;
    @FXML
    private TextField TallDisc;
    @FXML
    private ChoiceBox comboNew;
    @FXML
    private TextField itotal;
    @FXML
    private RadioButton Cdiscno;
    @FXML
    private TextField idelivery;


    // The main data variables
    public Long Obar;
    public String Otype;
    public String Oname;
    public Double Ono;
    public String Oquantity;
    public Double Oprice;
    public Double Odisc;
    public Double Onetprice;
    public Double allTotal;
    public Double allDisc;
    public Double plusDisc = 0.0; // for the additional total discount
    public String username;
    public int userphone;
    public String userlocation;
    public String comment;
    public int delivery;
    public double price;
    public double totdisc;
    public int totnetprice;

    // Initialize the value of the variables
    public void initValues() {
        Obar = Long.parseLong(ibr.getText());
        Otype = ity.getText();
        Oname = inm.getText();
        Ono = Double.parseDouble(ino.getText());
        Oquantity = (String) iqun.getValue();
    }

    private ResultSet dbResSell;
    private Main main;

    public void initialize(URL location, ResourceBundle resources) {
        setSellingTable();
        System.out.println(timeNow());
//        initializeCombo();
        iqun.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateUnitPrice();
        });
        ibr.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    fetchData();
                }
            }
        });
        ino.textProperty().addListener((observable, oldValue, newValue) -> {
            updateUnitPrice();
        });
    }

    public String checkKlasicTypes(ToggleGroup x) {
        RadioButton selectedRadioButton = (RadioButton) x.getSelectedToggle();
        return selectedRadioButton.getId();
    }
    /*
    // Classic Buttons

    public void buttonQuar(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 0.0 ;
        String typeId = null;
        try {
            typeId = checkKlasicTypes(type);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("big"));
        }
        Ono = .25;
        Oquantity = "كيلو";
        Oprice = kilo/4;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttonHalf(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 0.0 ;
        String typeId = null;
        try {
            typeId = checkKlasicTypes(type);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("big"));
        }
        Ono = .5;
        Oquantity = "كيلو";
        Oprice = kilo/2;
        onePrice.setText(String.valueOf(Oprice));


    }

    public void buttonKilo(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 0.0 ;
        String typeId = null;
        try {
            typeId = checkKlasicTypes(type);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("big"));
        }
        Ono = 1.0;
        Oquantity = "كيلو";
        Oprice = kilo;
        onePrice.setText(String.valueOf(Oprice));


    }

    public void buttonKiloEnter(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 0.0 ;
        Double gramenter = 1000.0 ;
        String typeId = null;
        try {
            gramenter = Double.valueOf(gramno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد الجرامات صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }

        try {
            typeId = checkKlasicTypes(type);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("big"));
        }
        Ono = gramenter/1000;
        Oquantity = "كيلو";
        Oprice = kilo*gramenter/1000;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttonOnePiece(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double piece = 0.0 ;
        String typeId = null;
        try {
            typeId = checkKlasicTypes(type);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            piece = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = 1.0;
        Oquantity = "قطعة";
        Oprice = piece;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttonTwoPieces(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double piece = 0.0 ;
        String typeId = null;
        try {
            typeId = checkKlasicTypes(type);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            piece = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = 2.0;
        Oquantity = "قطعة";
        Oprice = piece*2;
        onePrice.setText(String.valueOf(Oprice));


    }

    public void buttonThreePieces(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double piece = 0.0 ;
        String typeId = null;
        try {
            typeId = checkKlasicTypes(type);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            piece = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = 3.0;
        Oquantity = "قطعة";
        Oprice = piece*3;
        onePrice.setText(String.valueOf(Oprice));


    }

    public void buttonPiecesEnter(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double piece = 0.0 ;
        Double piecesenter = 1.0 ;
        String typeId = null;
        try {
            piecesenter = Double.valueOf(piecesno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }

        try {
            typeId = checkKlasicTypes(type);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            piece = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = piecesenter;
        Oquantity = "قطعة";
        Oprice = piece*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }

    // Form, Cups and Mix Buttons
    public void buttonMediumEnter(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 0.0;
        Double piecesenter = 1.0;
        String typeId = null;
        try {
            piecesenter = Double.valueOf(midno.getText());
        } catch (Exception e) {
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }

        try {
            typeId = checkKlasicTypes(type3);
        } catch (Exception e) {
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        if (typeId.equals("added")) {
            Oname = (String) comboNew.getValue();
            String sqlscript = "SELECT * from kunafahut.added where name = '" + Oname + "'";
            dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResSell.next()) {
                Otype = dbResSell.getString("type");
                Oquantity = dbResSell.getString("mediumName");
                kilo = Double.valueOf(dbResSell.getString("mediumPrice"));
            }


        } else {

            String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
            dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResSell.next()) {
                Otype = dbResSell.getString("type");
                Oname = dbResSell.getString("name");
                kilo = Double.valueOf(dbResSell.getString("medium"));
            }

            if (Otype.equals("كوب")) {
                Oquantity = "صغير";
            } else {
                Oquantity = "وسط";
            }
        }
            Ono = piecesenter;
            Oprice = kilo * piecesenter;
            onePrice.setText(String.valueOf(Oprice));
        }


    public void buttonBigEnter(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 0.0 ;
        Double piecesenter = 1.0 ;
        String typeId = null;
        try {
            piecesenter = Double.valueOf(bigno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }

        try {
            typeId = checkKlasicTypes(type3);
        }catch (Exception e){
            String selection = "من فضلك ادخل النوع اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        if (typeId.equals("added")) {
            Oname = (String) comboNew.getValue();
            String sqlscript = "SELECT * from kunafahut.added where name = '" + Oname + "'";
            dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResSell.next()) {
                Otype = dbResSell.getString("type");
                Oquantity = dbResSell.getString("bigName");
                kilo = Double.valueOf(dbResSell.getString("bigPrice"));
            }
        }
        else {
            String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
            dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResSell.next()) {
                Otype = dbResSell.getString("type");
                Oname = dbResSell.getString("name");
                kilo = Double.valueOf(dbResSell.getString("big"));
            }
            Ono = piecesenter;
            if (Otype.equals("كوب")) {
                Oquantity = "وسط";
            } else {
                Oquantity = "كبير";
            }
        }
        Ono = piecesenter;
        Oprice = kilo*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }

    // New Buttons
    public void buttonOrise(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 5.0 ;
        Double piecesenter = 1.0 ;
        String typeId = "Orise";
        try {
            piecesenter = Double.valueOf(newno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = piecesenter;
        Oquantity = "";
        Oprice = kilo*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttonFrise(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 6.0 ;
        Double piecesenter = 1.0 ;
        String typeId = "Frise";
        try {
            piecesenter = Double.valueOf(newno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = piecesenter;
        Oquantity = "";
        Oprice = kilo*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttonDislut(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 30.0 ;
        Double piecesenter = 1.0 ;
        String typeId = "Dislut";
        try {
            piecesenter = Double.valueOf(newno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = piecesenter;
        Oquantity = "";
        Oprice = kilo*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttonDisnut(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 25.0 ;
        Double piecesenter = 1.0 ;
        String typeId = "Disnut";
        try {
            piecesenter = Double.valueOf(newno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = piecesenter;
        Oquantity = "";
        Oprice = kilo*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttoncofee(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 8.0 ;
        Double piecesenter = 1.0 ;
        String typeId = "cofee";
        try {
            piecesenter = Double.valueOf(newno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = piecesenter;
        Oquantity = "";
        Oprice = kilo*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttonwater(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 4.0 ;
        Double piecesenter = 1.0 ;
        String typeId = "water";
        try {
            piecesenter = Double.valueOf(newno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = piecesenter;
        Oquantity = "";
        Oprice = kilo*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }

    public void buttonpepsi(javafx.event.ActionEvent actionEvent) throws SQLException {
        Double kilo = 6.0 ;
        Double piecesenter = 1.0 ;
        String typeId = "pepsi";
        try {
            piecesenter = Double.valueOf(newno.getText());
        }
        catch (Exception e){
            String selection = "من فضلك ادخل عدد القطع صحيح اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
        String sqlscript = "SELECT * from kunafahut.types where id = '" + typeId + "'";
        dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeQuery(sqlscript);
        while (dbResSell.next()) {
            Otype = dbResSell.getString("type");
            Oname = dbResSell.getString("name");
            kilo = Double.valueOf(dbResSell.getString("medium"));
        }
        Ono = piecesenter;
        Oquantity = "";
        Oprice = kilo*piecesenter;
        onePrice.setText(String.valueOf(Oprice));
    }
    */

    public void addItem(javafx.event.ActionEvent actionEvent) {
        try {
            Oprice = Double.parseDouble(itotal.getText());
            Onetprice = returnDisc(Oprice);
            Odisc = Oprice - Onetprice;
            initValues();
            // save ItemData in the preorder table
            String sqlscript = "INSERT INTO aleef.preorder (`barcode`, `type`, `name`, `no`, `quantity`, `price`, `disc`, `netPrice`) VALUES (" + Obar + ",'" + Otype + "','" + Oname + "'," + Ono + ",'" + Oquantity + "'," + Oprice + "," + Odisc + "," + Onetprice + ")";
            try {
                initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeUpdate(sqlscript);
                System.out.println("updated successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("dosen't updated!");
            }
            sellingTable.getItems().clear();
            setSellingTable();
            clearAllOthers();
        } catch (Exception e) {
            String selection = "من فضلك ادخل ادخل طلب اولاً ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public double returnDisc(double price) {
        String typeId;
        double afterDisc = price;
        try {
            typeId = checkKlasicTypes(type7);
            if (typeId.equals("Cdiscno")) {
                try {
                    afterDisc = price - Double.valueOf(Ediscno.getText());
                } catch (Exception e) {
                    String selection = "من فضلك ادخل رقم الخصم اولاً ";
                    Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                    alert.showAndWait();
                }
            } else if (typeId.equals("Cdiscratio")) {
                try {
                    if ((!Double.valueOf(Ediscratio.getText()).equals(0.0))) {
                        afterDisc = price * (1 - Double.valueOf(Ediscratio.getText()) / 100);
                    }
                } catch (Exception e) {
                    String selection = "من فضلك ادخل نسبة الخصم اولاً ";
                    Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
                    alert.showAndWait();
                }
            } else if (typeId.equals("Free")) {
                afterDisc = 0.0;
            }
        } catch (Exception e) {
            return afterDisc;
        }
        return afterDisc;
    }

    public void initPlusDisc(javafx.event.ActionEvent actionEvent) {
        if (Orderdisc.isSelected()) {
            plusDisc = allTotal - returnDisc(allTotal);
            sellingTable.getItems().clear();
            setSellingTable();
        } else {
            plusDisc = 0.0;
            sellingTable.getItems().clear();
            setSellingTable();
        }
    }

    public void clearAllOthers() {
        for (Toggle t : type7.getToggles()) {
            if (t instanceof RadioButton) {
                ((RadioButton) t).setSelected(false);
            }
        }
        Ediscno.setText("");
        Ediscratio.setText("");
        ibr.setText("");
        inm.setText("");
        ity.setText("");
        ino.setText("");
        iprice.setText("");
        itotal.setText("");
        iqun.getItems().clear();

    }

    public void dropTableRow(javafx.event.ActionEvent actionEvent) {
        int tableRowId = -1;
        int typed = 0;
        tableRowId = sellingTable.getSelectionModel().getSelectedIndex();
        if (!(tableRowId <= -1)) {
            typed = sellingTable.getSelectionModel().getSelectedItem().rowNo;
            String sqlscript = "DELETE FROM aleef.preorder WHERE `rowNo` =" + typed + "";
            try {
                initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeUpdate(sqlscript);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sellingTable.getItems().clear();
            setSellingTable();
        } else {
            String selection = "من فضلك حدد الصف المراد حذفة ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void clearAllData(javafx.event.ActionEvent actionEvent) {
        clearAllData();
    }

    public void clearAllData() {
        // clear table data
        String sqlscript = "DELETE FROM aleef.preorder";
        try {
            initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeUpdate(sqlscript);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sellingTable.getItems().clear();
        setSellingTable();
        // clear other data
        clearAllOthers();
    }

    public void setSellingTable() {
        // DB functions
        ResultSet dbResAllTotal;
        ResultSet dbResAllDisc;
        try {
            String sqlscript = "SELECT * from aleef.preorder";
            String sqlAllTotal = "SELECT SUM(netPrice) as totalNetPrice from aleef.preorder";
            String sqlAllDisc = "SELECT SUM(disc) AS totalDisc from aleef.preorder";
            dbResSell = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            dbResAllTotal = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlAllTotal);
            dbResAllDisc = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlAllDisc);
            while (dbResAllTotal.next()) {
                allTotal = (Double) dbResAllTotal.getDouble("totalNetPrice");
            }
            while (dbResAllDisc.next()) {
                allDisc = (Double) dbResAllDisc.getDouble("totalDisc");
            }
            while (dbResSell.next()) {
                oblist.add(new moderTabel(dbResSell.getInt("rowNo"), dbResSell.getLong("barcode"), dbResSell.getString("type"), dbResSell.getString("name"), dbResSell.getDouble("no"), dbResSell.getString("quantity"), dbResSell.getDouble("price"), dbResSell.getDouble("disc"), dbResSell.getDouble("netprice")));
            }
            TallDisc.setText(String.valueOf(allDisc + plusDisc));
            TallTotal.setText(String.valueOf(allTotal - plusDisc));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Tbar.setCellValueFactory(new PropertyValueFactory<>("bar"));
        Ttype.setCellValueFactory(new PropertyValueFactory<>("type"));
        Tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        Tno.setCellValueFactory(new PropertyValueFactory<>("no"));
        Tquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        Tprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        Tdisc.setCellValueFactory(new PropertyValueFactory<>("disc"));
        Tnetprice.setCellValueFactory(new PropertyValueFactory<>("netprice"));
        sellingTable.setItems(oblist);
    }

    public void fetchData() {
        Long bar = Long.parseLong(ibr.getText());
        ResultSet dbResAllTotal;
        iqun.getItems().clear();
        try {
            String sqlscript = "SELECT * from aleef.details where barcode = " + bar + ";";
            dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResAllTotal.next()) {
                ity.setText(dbResAllTotal.getString("type"));
                inm.setText(dbResAllTotal.getString("name"));
                ino.setText("1");
                iqun.getItems().addAll(dbResAllTotal.getString("Bname"), dbResAllTotal.getString("Aname"));
                iqun.setValue(dbResAllTotal.getString("Bname"));
                iprice.setText(String.valueOf(dbResAllTotal.getDouble("Bprice")));
            }
            updatePrice();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public void updatePrice() {
        itotal.setText(String.valueOf((Double.valueOf(iprice.getText()) * (Double.valueOf(ino.getText())))));
    }

    public void updateUnitPrice() {
        Long bar = Long.parseLong(ibr.getText());
        String quantity = (String) iqun.getValue();
        ResultSet dbResAllTotal;
        try {
            String sqlscript = "SELECT * from aleef.details where barcode = " + bar + ";";

            dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);

            while (dbResAllTotal.next()) {
                if (quantity.equals(dbResAllTotal.getString("Bname"))) {
                    iprice.setText(String.valueOf(dbResAllTotal.getDouble("Bprice")));
                } else if (quantity.equals(dbResAllTotal.getString("Aname"))) {
                    iprice.setText(String.valueOf(dbResAllTotal.getDouble("Aprice")));
                }
            }
            updatePrice();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public void fetchItem2(MouseEvent actionEvent) {
        int tableRowId = -1;
        long bar = 0;
        String quantity;
        Double no = 0.0;
        tableRowId = sellingTable.getSelectionModel().getSelectedIndex();
        if (!(tableRowId <= -1)) {
            ibr.setText(String.valueOf(sellingTable.getSelectionModel().getSelectedItem().bar));
            fetchData();
            ino.setText(String.valueOf(sellingTable.getSelectionModel().getSelectedItem().no));
            iqun.setValue(sellingTable.getSelectionModel().getSelectedItem().quantity);
            Cdiscno.setSelected(true);
            Ediscno.setText(String.valueOf(sellingTable.getSelectionModel().getSelectedItem().disc));
            updatePrice();
        }
    }


    public static Statement initializeDB(String dburl, String dbuser, String dbpass) throws SQLException {
        // DB parameters
        Connection dbconn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
            dbconn = DriverManager.getConnection(dburl, dbuser, dbpass);
            System.out.println("DataBase connected");
        } catch (ClassNotFoundException | SQLException var2) {
            System.err.println(var2.getMessage());
        }
        return dbconn.createStatement();

    }

    public String timeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    // this function for making new id unique and save them to variable called idgenerate
    public void setIdgenerate(){
        // coping data to another Field
        String sqlscript = "SELECT orderNo FROM orderdetails ORDER BY orderNo DESC LIMIT 1;";
        int getId=0;
        try {
            ResultSet dbResGetId = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResGetId.next()){
                getId = (int) dbResGetId.getDouble("orderNo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String lastGetId;
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMdd");
        String datetime = ft.format(dNow);
        lastGetId = String.valueOf(getId).substring(0,6);
        if (lastGetId.equals(datetime)){
            idgenerate = getId+1;
        }else {
            idgenerate = Integer.valueOf(datetime+"001");
        }
    }
    // this function for check if found data to make a new order ok and make it then print or not to do anything and out a message as alert
    public void printing1(javafx.event.ActionEvent actionEvent){
        if (checkEmpty()==1){
            printing();
            userdata.outprint(idgenerate);
            //userdata.bill();
        }else {
            String selection = "من فضلك ادخل بيانات الاوردر ";
            Alert alert = new Alert(Alert.AlertType.ERROR, " " + selection + " !!!", ButtonType.OK);
            alert.showAndWait();
        }

    }
    public void menuPage(javafx.event.ActionEvent actionEvent){
        try {
            Parent userview = FXMLLoader.load(menuPage.class.getResource("../fxml/menuPage.fxml"));
            Scene userscene = new Scene(userview);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(userscene);
            window.setFullScreen(true);
            window.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    // this function is set to check if the preorder is saved and there's no data or not to make an order if not one return (0)
    public int checkEmpty(){
        int check = 0;
        try {
            String sendOrderDetails = "SELECT EXISTS (SELECT 1 FROM preorder);";
            ResultSet dbResGetId = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sendOrderDetails);
            while (dbResGetId.next()){
               check = dbResGetId.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return check;
    }
    // this is the main function for saving data and printing order
    public void printing(){
        int id;
        String sendOrderDetails;
        try{
            delivery = Integer.parseInt(idelivery.getText());
        }catch(Exception e ){
            delivery = 0;
        }
        try{
            userphone = Integer.parseInt(Tphone.getText());
        }catch(Exception e ){
            userphone = 0;
        }
        username = Tname.getText();
        userlocation = Tlocation.getText();
        comment = Tnote.getText();
        // if the mode is for modifying or fast print
        if (ismod){
            id=idmod;
        }
        // if this is new order so after saving it come here to be printed
        else {
            setIdgenerate();
            id =idgenerate;
        }
        // coping data to another Field
        String sendOrderData = "insert into orders (orderNo, barcode, type, name, no, quantity, price, disc, netPrice)\n" +
                "select ("+id+"),barcode , type, name, no, quantity, price, disc, netPrice from preorder;";
        if (ismod){
             sendOrderDetails = "UPDATE orderdetails set cachierName = '"+cashierName+"',orderTime = CURRENT_TIMESTAMP,price = "+(allTotal+allDisc)+", totDisc = "+allDisc+", totPrice = "+allTotal+",totNetPrice = totPrice+delivery where orderNo = "+id+";";

        }else {
             sendOrderDetails = "insert into orderdetails (orderNo, orderTime, cachierName, price, totDisc,totPrice, delivery, totNetPrice,clientName,clientPhone,clientLocation,comment )\n" +
                    "value ("+idgenerate+", CURRENT_TIMESTAMP, '"+cashierName+"',"+(allTotal+allDisc)+", "+allDisc+", "+allTotal+","+delivery+",totPrice+delivery,'"+username+"',"+userphone+",'"+userlocation+"','"+comment+"');";
        }

        try {
            initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderData);
            initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderDetails);

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        clearAllData();
    }
}
