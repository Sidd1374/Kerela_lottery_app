package com.example.keralalottery;

import com.google.firebase.database.FirebaseDatabase;

public class setData {
    private FirebaseDatabase dbs;

    public static int num10 , num50, num70, num80, num500;
    public int getNum10() {
        return num10;
    }

    public int getNum50() {
        return num50;
    }

    public int getNum70() {
        return num70;
    }

    public int getNum80() {
        return num80;
    }

    public int getNum500() {
        return num500;
    }

    public void arrayNum(){
        System.out.println("data ");
    }
}
