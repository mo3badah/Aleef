package sample.controller;

import com.jfoenix.controls.JFXBadge;
import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import sample.controller.selling;


public class userdata implements Initializable {
    @FXML
    private AnchorPane userdata;

    @FXML
    private TextField setNow;

    @FXML
    private TextField setId;

    @FXML
    private TextField TuserName;

    @FXML
    private TextField Tphone;

    @FXML
    private TextField Tlocation;

    @FXML
    private TextField Tcomment;

    @FXML
    private TextField Tprice;

    @FXML
    private TextField Tdelivery;

    @FXML
    private TextField TtotDisc;

    @FXML
    private TextField TtotNetPrice;

    @FXML
    private TextField Tprint;
    @FXML
    private static TextArea textArea;

    public static int idgenerate;
    public static Timestamp orderTime;
    public static String username;
    public static int userphone;
    public static String userlocation;
    public static String comment;
    public static int delivery;
    public static double price;
    public static double totdisc;
    public static int totnetprice;

    private selling selling;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getGenerated(getOurId());
        setNow.setText(String.valueOf(orderTime));
        setId.setText(String.valueOf(idgenerate));
        TuserName.setText(String.valueOf(username));
        Tphone.setText(String.valueOf(userphone));
        Tlocation.setText(String.valueOf(userlocation));
        Tcomment.setText(String.valueOf(comment));
        Tprice.setText(String.valueOf(price));
        Tdelivery.setText(String.valueOf(delivery));
        TtotDisc.setText(String.valueOf(totdisc));
        TtotNetPrice.setText(String.valueOf(totnetprice));


    }

    public String  timeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public int getOurId(){
        int id;
        if (sample.controller.selling.isIsmod()){
            id= sample.controller.selling.getIdmod();
        }
        else {
            id = sample.controller.selling.getIdgenerate();
        }
        return id;
    }

    public static void getGenerated(int id) {

        // coping data to another Field
        String sqlscript = "SELECT * FROM orderdetails where  orderNo = "+id+";";
        try {
            ResultSet dbResGetId = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResGetId.next()){
                idgenerate = dbResGetId.getInt("orderNo");
                orderTime = dbResGetId.getTimestamp("orderTime");
                username = dbResGetId.getString("clientName");
                userphone = dbResGetId.getInt("clientPhone");
                userlocation = dbResGetId.getString("clientLocation");
                comment = dbResGetId.getString("comment");
                price = dbResGetId.getInt("price");
                totdisc = dbResGetId.getDouble("totDisc");
                delivery = dbResGetId.getInt("delivery");
                totnetprice = dbResGetId.getInt("totNetPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void savingNewData(){
        try {
            delivery = Integer.parseInt(Tdelivery.getText());
            username = TuserName.getText();
            userphone = Integer.parseInt((Tphone.getText()));
            userlocation = Tlocation.getText();
            comment = Tcomment.getText();
        }catch (Exception e){
            e.getCause();
        }
        // coping data to another Field
        String sendOrderDetails = "UPDATE orderdetails set clientName = '"+username+"',clientPhone = "+userphone+",clientLocation = '"+userlocation+"',comment = '"+comment+"', delivery = "+delivery+", totNetPrice = totPrice+delivery where orderNo = "+idgenerate+";";

        try {
            initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true","moreda","moreda2021").executeUpdate(sendOrderDetails);

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

    }



    public static Statement initializeDB(String dburl, String dbuser, String dbpass) throws SQLException {
        // DB parameters
        Connection dbconn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbconn = DriverManager.getConnection(dburl, dbuser, dbpass);
        } catch (ClassNotFoundException | SQLException var2) {
            System.err.println(var2.getMessage());
        }
        return dbconn.createStatement();

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

    public  void gotoselling(javafx.event.ActionEvent actionEvent){
        try {
            sample.controller.selling.setIsmod(false);
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
    public static void printNode(Node node) {

        Printer printer = Printer.getDefaultPrinter();

        PrinterJob printerJob = PrinterJob.createPrinterJob(printer);

        Paper paper = PrintHelper.createPaper("Roll80", 80, 590, Units.MM);

        PageLayout pageLayout = printerJob.getPrinter().createPageLayout(paper, PageOrientation.PORTRAIT, 0, 0, 0, 0);

        double scale = 0.791;

        node.getTransforms().add(new Scale(scale, scale));

        boolean success = printerJob.printPage(pageLayout, node);
        if (success) {
            printerJob.endJob();
        }

    }

    //*** prepare text for printing
    public static Node getPrintableText(){

        Label text = new Label();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("                    كنافة هت                          \n");
        stringBuilder.append("                       01006318817                     \n");
        stringBuilder.append("-------------------------------------------------" + "\n");
        stringBuilder.append("          رقم الاوردر   :                    "+idgenerate+" \n");
        stringBuilder.append("    الوفت والتاريخ:  "+orderTime+" \n");
        stringBuilder.append("-------------------------------------------------" + "\n");
        stringBuilder.append("اسم العميل   :    "+username+"\n");
        stringBuilder.append("         رقم التليفون   :    "+userphone+"\n");
        stringBuilder.append("العنوان  : "+userlocation+" \n");
        stringBuilder.append( comment+"  \n");
        stringBuilder.append("-------------------------------------------------" + "\n");
        stringBuilder.append("     الصنف        الكمية    الخصم الإجمالي" + "\n");
        String sqlscript = "SELECT * FROM ordersdata where orderNo = "+idgenerate+";";
        try {
            ResultSet dbResGetId = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResGetId.next()){
                String type = dbResGetId.getString("type");
                String name = dbResGetId.getString("name");
                double no = dbResGetId.getDouble("no");
                String quantity = dbResGetId.getString("quantity");
                double discount = dbResGetId.getDouble("disc");
                double total = dbResGetId.getDouble("netPrice");
                stringBuilder.append("===========================\n");
                stringBuilder.append( type+" "+name+" \t "+no+" "+quantity+" \t "+discount+" \t "+total+" * \n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sqlscript2 = "SELECT * FROM orderdetails  where orderNo = "+idgenerate+";";
        try {
            ResultSet dbResGetId = (ResultSet) initializeDB("jdbc:mysql://localhost:3306/KunafaHut?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript2);
            while (dbResGetId.next()){

                price = dbResGetId.getDouble("price");
                totdisc = dbResGetId.getDouble("totDisc");
                delivery = dbResGetId.getInt("delivery");
                totnetprice = dbResGetId.getInt("totNetPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stringBuilder.append("============================\n");
        stringBuilder.append("السعر قبل الخصم :  "+"\t\t\t\t"+price+"\n");
        stringBuilder.append("  إجمالي الخصم  :  "+"\t\t\t\t"+totdisc+"\n");
        stringBuilder.append("      الدليفري  :    "+"\t\t\t\t"+delivery+"\n");
        stringBuilder.append("       الإجمالي  :    "+"\t\t\t\t"+totnetprice+"\n");
        stringBuilder.append("--------------------------------------------\n");
        stringBuilder.append( "        سهرتك تحلي في رمضان مع كنافة هت               "+"\n");
        stringBuilder.append( "            المجاورة السابعة-بجوار عالم أليف                "+"\n");
        text.setText(stringBuilder.toString());
        return text;
    }
    //*** issue print command
    public void printReceipt(javafx.event.ActionEvent actionEvent){
        savingNewData();
        printNode(getPrintableText());
    }
    public static void outprint(int id){
        getGenerated(id);
        printNode(getPrintableText());
        sample.controller.selling.setIsmod(false);
    }
}
