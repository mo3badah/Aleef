package sample.controller;

import java.sql.Timestamp;

public class ordersTable {
    int orderNo,clientPhone,delivery,totNetPrice;
    Double price,totDisc,totPrice;
    Timestamp orderTime;

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(int clientPhone) {
        this.clientPhone = clientPhone;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public int getTotNetPrice() {
        return totNetPrice;
    }

    public void setTotNetPrice(int totNetPrice) {
        this.totNetPrice = totNetPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotDisc() {
        return totDisc;
    }

    public void setTotDisc(Double totDisc) {
        this.totDisc = totDisc;
    }

    public Double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(Double totPrice) {
        this.totPrice = totPrice;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public String getCachierName() {
        return cachierName;
    }

    public void setCachierName(String cachierName) {
        this.cachierName = cachierName;
    }

    String clientName;

    public ordersTable(int orderNo, int clientPhone, int delivery, int totNetPrice, Double price, Double totDisc, Double totPrice, Timestamp orderTime, String clientName, String clientLocation, String cachierName) {
        this.orderNo = orderNo;
        this.clientPhone = clientPhone;
        this.delivery = delivery;
        this.totNetPrice = totNetPrice;
        this.price = price;
        this.totDisc = totDisc;
        this.totPrice = totPrice;
        this.orderTime = orderTime;
        this.clientName = clientName;
        this.clientLocation = clientLocation;
        this.cachierName = cachierName;
    }

    String clientLocation;
    String cachierName;
}
