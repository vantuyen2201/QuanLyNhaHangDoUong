package com.donghh.quanlynhahangdouong.ui.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Book  implements Parcelable {
   private int id;//mã bàn
   private String listDrink;//danh sách đồ uống cho bàn
   private boolean isSelect;

   public Book() {
   }

   public Book(String listDrink) {
      this.listDrink = listDrink;
   }

   public Book(int id, String listDrink) {
      this.id = id;
      this.listDrink = listDrink;
   }

   protected Book(Parcel in) {
      id = in.readInt();
      listDrink = in.readString();
   }

   public boolean isSelect() {
      return isSelect;
   }

   public void setSelect(boolean select) {
      isSelect = select;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(id);
      dest.writeString(listDrink);
   }

   @Override
   public int describeContents() {
      return 0;
   }

   public static final Creator<Book> CREATOR = new Creator<Book>() {
      @Override
      public Book createFromParcel(Parcel in) {
         return new Book(in);
      }

      @Override
      public Book[] newArray(int size) {
         return new Book[size];
      }
   };

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
}
