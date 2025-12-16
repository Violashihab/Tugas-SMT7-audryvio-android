package com.example.latihansqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "kampus.db"
        private const val DATABASE_VERSION = 1


        const val TABLE_MAHASISWA = "mahasiswa"
        const val COLUMN_NIM = "nim"
        const val COLUMN_NAMA = "nama"
    }

    // ini untuk bagian membuat database
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_MAHASISWA (
                $COLUMN_NIM TEXT PRIMARY KEY,
                $COLUMN_NAMA TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MAHASISWA")
        onCreate(db)
    }

    // --- crud ---


    fun tambahData(mahasiswa: Mahasiswa): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NIM, mahasiswa.nim)
        values.put(COLUMN_NAMA, mahasiswa.nama)


        val result = db.insert(TABLE_MAHASISWA, null, values)
        db.close()
        return result
    }

    // mengambil/get semua data
    fun tampilkanSemuaData(): ArrayList<String> {
        val listData = ArrayList<String>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_MAHASISWA"

        val cursor: Cursor? = db.rawQuery(query, null)

        // looping  untuk mengambil data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
                val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
                listData.add("$nim - $nama")
            } while (cursor.moveToNext())
            cursor.close()
        }

        db.close()
        return listData
    }

    // update bersadarkan nim mhs
    fun ubahData(mahasiswa: Mahasiswa): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAMA, mahasiswa.nama)

        // update baris yang ada nim yang sesuai
        val result = db.update(
            TABLE_MAHASISWA,
            values,
            "$COLUMN_NIM = ?",
            arrayOf(mahasiswa.nim)
        )
        db.close()
        return result // mengembalikan jumlah baris yang diupdate (0 jika gagal)
    }

    // menghapus data bersadarkan nimi
    fun hapusData(nim: String): Int {
        val db = this.writableDatabase
        // menghapus baris di mana clause "nim = ?"
        val result = db.delete(TABLE_MAHASISWA, "$COLUMN_NIM = ?", arrayOf(nim))
        db.close()
        return result
    }
}