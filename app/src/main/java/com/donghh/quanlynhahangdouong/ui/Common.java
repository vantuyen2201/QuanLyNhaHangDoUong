package com.donghh.quanlynhahangdouong.ui;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {
   public static String SHARED_PREFERENCES_KEY="SHARED_PREFERENCES_KEY";
   public static String CREATE_DATABASE="CREATE_DATABASE";
   public static String IS_LOGIN="IS_LOGIN";
   public static String IS_MANAGER="IS_MANAGER";

   public static void writeString(Context context, final String KEY, String value) {
      SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).edit();
      editor.putString(KEY, value);
      editor.commit();
   }

   public static String readString(Context context, final String KEY) {
      return context.getSharedPreferences(SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(KEY, null);
   }

   public static void putBoolean(Context context, final String KEY, boolean value) {
      SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).edit();
      editor.putBoolean(KEY, value);
      editor.commit();
   }

   public static boolean getBoolean(Context context, final String KEY) {
      return context.getSharedPreferences(SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getBoolean(KEY, false);
   }

   public static String formatDateToString(Date date){
      String pattern = "yyyyMMdd";
      DateFormat df = new SimpleDateFormat(pattern);

      return df.format(date);
   }
}
