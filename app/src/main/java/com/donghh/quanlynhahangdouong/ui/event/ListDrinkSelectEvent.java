package com.donghh.quanlynhahangdouong.ui.event;

import com.donghh.quanlynhahangdouong.ui.entity.Drink;

import java.util.ArrayList;

public class ListDrinkSelectEvent {
   private ArrayList<Drink> list=new ArrayList<>();

   public ListDrinkSelectEvent(ArrayList<Drink> list) {
      this.list = list;
   }

   public ArrayList<Drink> getList() {
      return list;
   }

   public void setList(ArrayList<Drink> list) {
      this.list = list;
   }
}
