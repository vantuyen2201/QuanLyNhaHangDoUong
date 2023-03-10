package com.donghh.quanlynhahangdouong.ui.entity;

import java.io.Serializable;

public class Bill implements Serializable {
   private int id;//mã hóa đơn
   private String listDrink;//danh sách đồ uống của hóa đơn
   private double totalMoney;//tổng tiền hóa đơn
   private String dateBill;//ngày in hóa đơn định dạng năm tháng ngày: ví dụ (20201205 là ngày 05 tháng 12 năm 2022)

   public Bill() {
   }

   public Bill(String listDrink, double totalMoney, String dateBill) {
      this.listDrink = listDrink;
      this.totalMoney = totalMoney;
      this.dateBill = dateBill;
   }

   public Bill(int id, String listDrink, double totalMoney, String dateBill) {
      this.id = id;
      this.listDrink = listDrink;
      this.totalMoney = totalMoney;
      this.dateBill = dateBill;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getListDrink() {
      return listDrink;
   }

   public void setListDrink(String listDrink) {
      this.listDrink = listDrink;
   }

   public double getTotalMoney() {
      return totalMoney;
   }

   public void setTotalMoney(double totalMoney) {
      this.totalMoney = totalMoney;
   }

   public String getDateBill() {
      return dateBill;
   }

   public void setDateBill(String dateBill) {
      this.dateBill = dateBill;
   }
}
