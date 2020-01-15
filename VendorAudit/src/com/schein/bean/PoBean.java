package com.schein.bean;

/**
 * @author Digvijay.Katoch
 *
 */

public class PoBean {

  private String ponumber;
  private java.math.BigDecimal division;
  private java.math.BigDecimal warehouse;
  private String supplier;
  private Integer ordernumber;
  private String ordertype;
  private Integer jdeinvoice;

  public String getPonumber() {
    return ponumber;
  }

  public void setPonumber(String ponumber) {
    this.ponumber = ponumber;
  }

  public java.math.BigDecimal getDivision() {
    return division;
  }

  public void setDivision(java.math.BigDecimal division) {
    this.division = division;
  }

  public java.math.BigDecimal getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(java.math.BigDecimal warehouse) {
    this.warehouse = warehouse;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public Integer getOrdernumber() {
    return ordernumber;
  }

  public void setOrdernumber(Integer ordernumber) {
    this.ordernumber = ordernumber;
  }

  public String getOrdertype() {
    return ordertype;
  }

  public void setOrdertype(String ordertype) {
    this.ordertype = ordertype;
  }

  public Integer getJdeinvoice() {
    return jdeinvoice;
  }

  public void setJdeinvoice(Integer jdeinvoice) {
    this.jdeinvoice = jdeinvoice;
  }

}
