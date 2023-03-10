package com.donghh.quanlynhahangdouong.ui.entity;

public class Drink {
   private int id;//mã đồ uống
   private String drinkName;//tên đồ uống
   private int amount;//số lượng
   private double price;

   public Drink(int id, String drinkName, int amount, double price) {
      this.id = id;
      this.drinkName = drinkName;
      this.amount = amount;
      this.price = price;
   }

   public Drink(String drinkName, int amount, double price) {
      this.drinkName = drinkName;
      this.amount = amount;
      this.price = price;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getDrinkName() {
      return drinkName;
   }

   public void setDrinkName(String drinkName) {
      this.drinkName = drinkName;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }
}
