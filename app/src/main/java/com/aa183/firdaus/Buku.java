package com.aa183.firdaus;

import java.util.Date;

public class Buku {
    private int idBuku;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String caption;
    private String penulis;
    private String isiBuku;
    private String link;

    public Buku(int idBuku, String judul, Date tanggal, String gambar, String caption, String penulis, String isiBuku, String link) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.caption = caption;
        this.penulis = penulis;
        this.isiBuku = isiBuku;
        this.link = link;
    }

    public Buku(int idBuku, String resep_makanan_indonesia, Date tempDate, String storeImagesFiles, String indosiar, String s, String s1) {

    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getIsiBuku() {
        return isiBuku;
    }

    public void setIsiBuku(String isiBuku) {
        this.isiBuku = isiBuku;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
