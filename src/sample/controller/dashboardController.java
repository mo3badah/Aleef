package sample.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class dashboardController implements Initializable{
    @FXML
    private LineChart<String, Integer> firstPane;
    @FXML
    private BarChart<String, Integer> barchart;
    @FXML
    private BorderPane border_pane;
    @FXML
    private Pane pieview;
    @FXML
    private Label nMonth;

    @FXML
    private Label nWeek;

    @FXML
    private Label nDay;

    @FXML
    private Label tMonth;

    @FXML
    private Label tWeek;

    @FXML
    private Label tDay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMainData();
        setLineChart();
        setLineChart1();
        loadData();

    }
    private void setLineChart() {
        firstPane.getData().clear();
        XYChart.Series series = new XYChart.Series();
        try{
            String sqlscript = "SELECT    DATE(orderTime) as DATE, SUM(totPrice) as price\n" +
                    "FROM      aleef.orderdetails\n" +
                    "GROUP BY  DATE(orderTime) order by DATE(orderTime) desc limit 7;";

            ResultSet dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResAllTotal.next()) {
                series.getData().add(new XYChart.Data(dbResAllTotal.getString("DATE"),dbResAllTotal.getInt("price")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        series.setName("اجمالي المبيعات خلال اسبوع");
        firstPane.getData().addAll(series);
        firstPane.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }
    private void setLineChart1() {
        barchart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        try{
            String sqlscript = "SELECT    type, name, Bno/BfromA+Ano  as allInA , Aname\n" +
                    "FROM      aleef.details\n" +
                    "GROUP BY  barcode \n" +
                    "order by allInA limit 8;";

            ResultSet dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResAllTotal.next()) {
                series.getData().add(new XYChart.Data(dbResAllTotal.getString("type")+" "+dbResAllTotal.getString("name")+"\n"+dbResAllTotal.getString("Aname"),dbResAllTotal.getInt("allInA")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        series.setName("المتبقي من السلع المنخفضة");
        barchart.getData().addAll(series);
        barchart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }
    private void  loadData(){
        pieview.getChildren().clear();
        ObservableList<PieChart.Data>list= FXCollections.observableArrayList();
        try{
            String sqlscript = "SELECT    type, name, SUM(netPrice) as price\n" +
                    "FROM      aleef.orders\n" +
                    "GROUP BY  barcode;";

            ResultSet dbResAllTotal = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscript);
            while (dbResAllTotal.next()) {
                list.add(new PieChart.Data(dbResAllTotal.getString("type")+" "+dbResAllTotal.getString("name"),dbResAllTotal.getDouble("price")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        PieChart The_Owners = new PieChart(list);
        The_Owners.setTitle("مبيعات السلع");
        pieview.getChildren().add(The_Owners);
    }
    private void loadMainData(){
        try{
            String sqlscriptDay = "select count(orderNo) as num, sum(totPrice) as price from aleef.orderdetails where orderTime >= now() - INTERVAL 1 DAY;";
            String sqlscriptWeek = "select count(orderNo) as num, sum(totPrice) as price from aleef.orderdetails where orderTime >= now() - INTERVAL 1 WEEK;";
            String sqlscriptMonth = "select count(orderNo) as num, sum(totPrice) as price from aleef.orderdetails where orderTime >= now() - INTERVAL 1 MONTH;";

            ResultSet dbResAllTotalDay = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscriptDay);
            ResultSet dbResAllTotalWeek = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscriptWeek);
            ResultSet dbResAllTotalMonth = (ResultSet) selling.initializeDB("jdbc:mysql://localhost:3306/aleef?verifyServerCertificate=false&useSSL=true", "moreda", "moreda2021").executeQuery(sqlscriptMonth);
            while (dbResAllTotalDay.next()) {
                nDay.setText(dbResAllTotalDay.getString("num"));
                tDay.setText(String.valueOf(dbResAllTotalDay.getInt("price")));
            }
            while (dbResAllTotalWeek.next()) {
                nWeek.setText(dbResAllTotalWeek.getString("num"));
                tWeek.setText(String.valueOf(dbResAllTotalWeek.getInt("price")));
            }
            while (dbResAllTotalMonth.next()) {
                nMonth.setText(dbResAllTotalMonth.getString("num"));
                tMonth.setText(String.valueOf(dbResAllTotalMonth.getInt("price")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public  void menuPage(javafx.event.ActionEvent actionEvent){
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

}
