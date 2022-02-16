package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.models;// Created by FebinRukfan on 15-02-2022.

public class Nearby {

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public int getnImg() {
        return nImg;
    }

    public void setnImg(int nImg) {
        this.nImg = nImg;
    }

    public Nearby(String nName, int nImg) {
        this.nName = nName;
        this.nImg = nImg;
    }

    private String nName;
    private int nImg;

}
