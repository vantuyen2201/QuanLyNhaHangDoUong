package com.donghh.quanlynhahangdouong.ui.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {
    private int id;
    private String studentCode;
    private String studentName;
    private String studentBirthday;
    private String studentPhone;

    public Employee(int id, String studentCode, String studentName, String studentBirthday, String studentPhone) {
        this.id = id;
        this.studentName = studentName;
        this.studentCode = studentCode;
        this.studentBirthday = studentBirthday;
        this.studentPhone = studentPhone;
    }

    public Employee(String studentCode, String studentName, String studentBirthday, String studentPhone) {
        this.studentName = studentName;
        this.studentCode = studentCode;
        this.studentBirthday = studentBirthday;
        this.studentPhone = studentPhone;
    }


    protected Employee(Parcel in) {
        id = in.readInt();
        studentCode = in.readString();
        studentName = in.readString();
        studentBirthday = in.readString();
        studentPhone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(studentCode);
        dest.writeString(studentName);
        dest.writeString(studentBirthday);
        dest.writeString(studentPhone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(String studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }
}
