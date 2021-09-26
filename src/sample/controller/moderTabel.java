package sample.controller;

public class moderTabel {
    String type,name,quantity;
    Double no,price,disc,netprice;
    int rowNo ;
    Long bar;

    public moderTabel(int rowNo) {
        this.rowNo = rowNo;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }


    public moderTabel( int rowNo,Long bar, String type, String name, Double no, String quantity, Double price, Double disc, Double netprice) {
        this.bar = bar;
        this.rowNo = rowNo;
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.no = no;
        this.price = price;
        this.disc = disc;
        this.netprice = netprice;
    }

    public Long getBar() {
        return bar;
    }

    public void setBar(Long bar) {
        this.bar = bar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getNo() {
        return no;
    }

    public void setNo(Double no) {
        this.no = no;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDisc() {
        return disc;
    }

    public void setDisc(Double disc) {
        this.disc = disc;
    }

    public Double getNetprice() {
        return netprice;
    }

    public void setNetprice(Double netprice) {
        this.netprice = netprice;
    }
}
