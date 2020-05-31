package com.aa183.firdaus;

import android.content.ContentValues;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_bukuku1";
    private final static String TABLE_BUKU = "t_buku";
    private final static String KEY_ID_BUKU = "ID_Buku";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_CAPTION = "Caption";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_ISI_BUKU = "Isi_Buku";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BUKU = "CREATE TABLE " + TABLE_BUKU
                + "(" + KEY_ID_BUKU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_CAPTION + " TEXT, "
                + KEY_PENULIS + " TEXT, " + KEY_ISI_BUKU + " TEXT, "
                + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_BUKU);
        inialisasiBukuAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUKU;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_CAPTION, dataBuku.getCaption());
        cv.put(KEY_PENULIS, dataBuku.getCaption());
        cv.put(KEY_ISI_BUKU, dataBuku.getIsiBuku());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.insert(TABLE_BUKU, null, cv);
        db.close();
    }

    public void tambahBuku(Buku dataBuku, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_CAPTION, dataBuku.getCaption());
        cv.put(KEY_PENULIS, dataBuku.getCaption());
        cv.put(KEY_ISI_BUKU, dataBuku.getIsiBuku());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.insert(TABLE_BUKU, null, cv);
    }

    public void editBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_CAPTION, dataBuku.getCaption());
        cv.put(KEY_PENULIS, dataBuku.getCaption());
        cv.put(KEY_ISI_BUKU, dataBuku.getIsiBuku());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.update(TABLE_BUKU, cv, KEY_ID_BUKU + "=?", new String[]{String.valueOf(dataBuku.getIdBuku())});
        db.close();
    }

    public void hapusBuku(int idBuku) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUKU, KEY_ID_BUKU + "=?", new String[]{String.valueOf(idBuku)});
        db.close();
    }

    public ArrayList<Buku> getAllBuku() {
        ArrayList<Buku> dataBuku = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BUKU;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()) {
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Buku tempBuku = new Buku(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataBuku.add(tempBuku);
            } while (csr.moveToNext());
        }
        return dataBuku;
    }

    private String storeImagesFiles(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inialisasiBukuAwal(SQLiteDatabase db) {
        int idBuku = 0;
        Date tempDate = new Date();

        try {
            tempDate = sdFormat.parse("13/03/2020 06:22");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku masakan1 = new Buku(
                idBuku,
                "Resep Makanan Indonesia",
                tempDate,
                storeImagesFiles(R.drawable.indonesia),
                "15 kategori kumpulan resep",
                "Indosiar",
                "Ayam Goreng Gurih. BAHAN: 1 ekor ayam, potong jadi 20 bagian 1 sdm air jeruk nipis dan 1 sdt garam 2 cm jahe, memarkan 2 lembar daun salam 1 liter air Haluskan: 10 siung bawang putih 8 butir kemiri, sangrai 2 sdm ketumbar, sangrai 1 sdm garam Bumbu gurih: 20 gr tepung sagu 1 sdt baking powder Minyak goreng secukupnya Pelengkap: lalap sayuran dan sambal bajak: 3 buah cabai merah besar, 8  buah cabai rawit merah, dan 1 buah tomat, goreng hingga setengah  matang, angkat, haluskan. Tambahkan 1 sdt terasi matang dan 1/4 sdt  garam, aduk rata.  \n" +
                        "CARA MEMBUAT: 1. Cuci bersih ayam, lumuri dengan air jeruk nipis dan 1 sdt garam, diamkan 15 menit, cuci bersih lagi, tiriskan.  2. Rebus ayam bersama jahe, daun salam, dan bumbu halus. Masak ayam sampai lunak, gunakan api sedang, angkat. Tiriskan ayamnya, sisihkan kuahnya.  3. Bumbu gurih: ambil kuah ayam sebanyak 500 ml, campur dengan tepung sagu, masak hingga mengental, angkat, dinginkan. Setelah dingin, tambahkan dengan baking powder, aduk rata, sisihkan.  4. Panaskan minyak, goreng ayam hingga cokelat keemasan, angkat. Tuangkan bumbu gurih di atas ayam sesendok-sendok, goreng hingga kuning dan bersarang, angkat.  5. Sajikan hangat dengan lalap sayuran dan sambal bajak.  \n" +
                        "Untuk: 6 orang….\"\n",
                "http://www.pusatgratis.com/ebook-gratis/hobby/kumpulan-resep-masakan.html"
        );

        tambahBuku(masakan1, db);
        idBuku++;

        try {
            tempDate = sdFormat.parse("13/03/2020 06:22");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku masakan2 = new Buku(
                idBuku,
                "Resep Dessert",
                tempDate,
                storeImagesFiles(R.drawable.dessert),
                "15 kategori kumpulan resep",
                "Indosiar",
                "ES PUDING TAPE HIJAU. Bahan: 1 bks Agar-agar bubuk hijau 600 ml air 150 gr Gula Pasir 250 gr Tape Ketan Hijau Isi: 1 bh Kelapa Muda 300 ml Santan Sirup Merah Susu Kental Manis Es Serut. Cara Membuat: 1. Didihkan air, campur dengan agar-agar. Aduk rata dan tambahkan gula pasir. Setelah mendidih, masukkan tape, aduk rata dan masak sebentar.  2. Tuangkan ke dalam loyang, hilangkan uap panas dan masukkan dalam lemari pendingin.  3. Setelah dingin, potong bentuk dadu dan tuangkan ke dalam gelas. Tambahkan kelapa muda dan santan atau susu.  4. Beri es serut, tambahkan susu kental manis dan sirup merah.  5. Hias dengan daun pandan dan sajikan. ",
                "http://www.pusatgratis.com/ebook-gratis/hobby/kumpulan-resep-masakan.html"
        );

        tambahBuku(masakan2, db);
        idBuku++;

        try {
            tempDate = sdFormat.parse("13/03/2020 06:22");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku masakan3 = new Buku(
                idBuku,
                "Resep Makanan Serba Cokelat",
                tempDate,
                storeImagesFiles(R.drawable.cokelat),
                "15 kategori kumpulan resep",
                "Indosiar",
                "CHOCOLATE CHEESE CAKE. BAHAN : Dasar 100 gram biscuit marie, haluskan 100 gram mentega 1 sdt kayumanis bubuk adonan : 150 gram cokelat masak dark 500 gram cream cheese 100 gram gula pasir castor 2 butir telur 50 gram tepung terigu 1 sdm cokelat bubuk 80 ml yogurt taburan : cokelat bubuk, gula bubuk dan stroberi segar. CARA MEMBUAT : 1. Siapkan loyang bongkar pasang ukuran : 20 cm alasi dasar loyang dengan aluminium foil. Panaskan oven dengan temperature 180 Derajat Celcius.  2. Dasar : campur biscuit marie dengan mentega dan kayumanis bubuk, aduk rata dan tata didasar loyang, padatkan.  3. Adonan : kocok cream cheese dengan gula pasir hingga lembut, masukkan telur dan kocok hingga rata, tambahkan tepung terigu dan cokelat bubuk, aduk rata, masukkan yogurt dan cokelat masak yang telah dilelehkan, aduk rata.  4. Tuangkan kedalam loyang dan panggang selama 1 jam, angkat, hilangkan uap panasnya dan simpan dalam lemari pendingin selama 3 jam.  5. Hias dengan cokelat, gula bubuk dan stroberi.  6. Sajikan dingin. ",
                "http://www.pusatgratis.com/ebook-gratis/hobby/kumpulan-resep-masakan.html"
        );

        tambahBuku(masakan3, db);
        idBuku++;

        try {
            tempDate = sdFormat.parse("13/03/2020 06:22");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku masakan4 = new Buku(
                idBuku,
                "Resep Makanan Soup",
                tempDate,
                storeImagesFiles(R.drawable.sup),
                "15 kategori kumpulan resep",
                "Indosiar",
                "SUP PASTA BENING. BAHAN : Kaldu ikan 250 gram ikan kakap 1 buah bawang Bombay potong-potong 1 batang daun bawang iris 1 batang seledri iris 5 biji merica butiran 1 liter air Isi : 12 udang ukuran sedang 2 sdm minyak goreng 2 siung bawang putih memarkan 5 cm jahe memarkan 125 gram pasta/angel hair, rebus matang 150 gram kapri 2 sdt garam 1/2 sdt merica bubuk 2 sdm saus ikan/kecap ikan 2 sdm air jeruk nipis Taburan : irisan daun bawang dan daun ketumbarCARA MEMBUAT : 1. Kaldu ikan : masak ikan bersama bawang Bombay, daun bawang, seledri, merica butiran dan air, setelah mendidih dan ikan matang angkat dan saring.  2. Panaskan minyak, tumis bawang putih dan jahe hingga harum, masukkan ke dalam kaldu dan didihkan kembali.  3. Masukkan udang, kapri, masak hingga udang matang, tambahkan garam dan merica bubuk, aduk rata, masukkan saus ikan dan air jeruk nipis angkat.  4. Siapkan mangkuk isi dengan pasta potongan ikan dan tuangkan kuah dengan isiannya, taburi daun bawang dan daun ketumbar. Untuk : 6 orang ",
                "https://www.webnovel.com/book/8212987205006305/Trial-Marriage-Husband%3A-Need-to-Work-Hard"
        );

        tambahBuku(masakan4, db);
        idBuku++;


    }

}
