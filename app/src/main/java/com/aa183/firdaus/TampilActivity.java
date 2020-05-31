package com.aa183.firdaus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class TampilActivity extends AppCompatActivity {
    private ImageView imgBuku;
    private TextView tvJudul, tvTanggal,tvCaption, tvPenulis, tvIsiBuku;
    private String linkBuku;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyy hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgBuku = findViewById(R.id.iv_buku);
        tvJudul = findViewById(R.id.tv_judul);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvCaption = findViewById(R.id.tv_caption);
        tvPenulis = findViewById(R.id.tv_penulis);
        tvIsiBuku = findViewById(R.id.tv_isi_buku);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvCaption.setText(terimaData.getStringExtra("CAPTION"));
        tvPenulis.setText(terimaData.getStringExtra("PENULIS"));
        tvIsiBuku.setText(terimaData.getStringExtra("ISI_BUKU"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");


        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgBuku.setImageBitmap(bitmap);
            imgBuku.setContentDescription(imgLocation);
        }catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

        linkBuku = terimaData.getStringExtra("LINK");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_bagikan){
            Intent bagikanBuku = new Intent(Intent.ACTION_SEND);
            bagikanBuku.putExtra(Intent.EXTRA_SUBJECT, tvJudul.getText().toString());
            bagikanBuku.putExtra(Intent.EXTRA_TEXT, linkBuku);
            bagikanBuku.setType("text/pain");
            startActivity(Intent.createChooser(bagikanBuku, "Bagikan buku"));
        }
        return  super.onOptionsItemSelected(item);
    }
}