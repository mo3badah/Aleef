package sample.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class dashboardController implements Initializable{
    @FXML
    private LineChart<String, Integer> firstPane;
    @FXML
    private BorderPane border_pane;
    @FXML
    private Pane pieview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLineChart();
        loadData();

    }
    private void setLineChart() {
        firstPane.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("monday",80));
        series.getData().add(new XYChart.Data("tuesday",90));
        series.getData().add(new XYChart.Data("wednesday",85));
        series.getData().add(new XYChart.Data("thursday",110));
        series.getData().add(new XYChart.Data("friday",130));
        series.getData().add(new XYChart.Data("saturday",80));
        series.getData().add(new XYChart.Data("sunday",50));

        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data("monday",22));
        series1.getData().add(new XYChart.Data("tuesday",23));
        series1.getData().add(new XYChart.Data("wednesday",24));
        series1.getData().add(new XYChart.Data("thursday",25));
        series1.getData().add(new XYChart.Data("friday",26));
        series1.getData().add(new XYChart.Data("saturday",27));
        series1.getData().add(new XYChart.Data("sunday",28));

        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data("monday",18));
        series2.getData().add(new XYChart.Data("tuesday",17));
        series2.getData().add(new XYChart.Data("wednesday",16));
        series2.getData().add(new XYChart.Data("thursday",15));
        series2.getData().add(new XYChart.Data("friday",14));
        series2.getData().add(new XYChart.Data("saturday",13));
        series2.getData().add(new XYChart.Data("sunday",12));

        XYChart.Series series3 = new XYChart.Series();
        series3.getData().add(new XYChart.Data("monday",7));
        series3.getData().add(new XYChart.Data("tuesday",6));
        series3.getData().add(new XYChart.Data("wednesday",5));
        series3.getData().add(new XYChart.Data("thursday",4));
        series3.getData().add(new XYChart.Data("friday",3));
        series3.getData().add(new XYChart.Data("saturday",2));
        series3.getData().add(new XYChart.Data("sunday",1));
        firstPane.getData().addAll(series,series1,series2,series3);
        firstPane.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");


    }
    private void showdashboard(MouseEvent event){
        try {
            Parent dashbord = FXMLLoader.load(getClass().getResource("sample/fxml/dashboard.fxml"));
            border_pane.setCenter(dashbord);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void  loadData(){
        pieview.getChildren().clear();
        ObservableList<PieChart.Data>list= FXCollections.observableArrayList();
        list.add(new PieChart.Data("Tarek",50000));
        list.add(new PieChart.Data("Khaled",75000));
        list.add(new PieChart.Data("Habib",40000));
        list.add(new PieChart.Data("Shrief",30000));
        PieChart The_Owners = new PieChart(list);
        The_Owners.setTitle("The Owners Of KunafaHut");
        pieview.getChildren().add(The_Owners);
    }
}
