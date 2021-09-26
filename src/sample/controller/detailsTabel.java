package sample.controller;

import java.math.BigInteger;

public class detailsTabel {
    String name,type,Aname,Bname;
    Double Aprice,Ano,Bprice,Bno,BfromA;
    long barcode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAname() {
        return Aname;
    }

    public void setAname(String aname) {
        Aname = aname;
    }

    public String getBname() {
        return Bname;
    }

    public void setBname(String bname) {
        Bname = bname;
    }

    public Double getAprice() {
        return Aprice;
    }

    public void setAprice(Double aprice) {
        Aprice = aprice;
    }

    public Double getAno() {
        return Ano;
    }

    public void setAno(Double ano) {
        Ano = ano;
    }

    public Double getBprice() {
        return Bprice;
    }

    public void setBprice(Double bprice) {
        Bprice = bprice;
    }

    public Double getBno() {
        return Bno;
    }

    public void setBno(Double bno) {
        Bno = bno;
    }

    public Double getBfromA() {
        return BfromA;
    }

    public void setBfromA(Double bfromA) {
        BfromA = bfromA;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public detailsTabel(long barcode, String type, String name, String Aname, Double Aprice, Double Ano, String Bname, Double Bprice, Double Bno, Double BfromA) {
        this.barcode = barcode;
        this.type = type;
        this.name = name;
        this.Aname = Aname;
        this.Aprice = Aprice;
        this.Ano = Ano;
        this.Bname = Bname;
        this.Bprice = Bprice;
        this.Bno = Bno;
        this.BfromA = BfromA;
    }
}
