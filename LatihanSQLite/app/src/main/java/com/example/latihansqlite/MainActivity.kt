package com.example.latihansqlite

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // meklarasi variabel
    private lateinit var editTextNim: EditText
    private lateinit var editTextNama: EditText
    private lateinit var buttonSimpan: Button
    private lateinit var buttonUbah: Button
    private lateinit var buttonHapus: Button
    private lateinit var listViewData: ListView

    // memanggil helper
    private lateinit var databaseHelper: DatabaseHelper

    // Adapter untuk ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inisialisasiKomponen()
        databaseHelper = DatabaseHelper(this)

        // loading data awal saat apk dibuka
        muatData()

        buttonSimpan.setOnClickListener {
            val nim = editTextNim.text.toString()
            val nama = editTextNama.text.toString()

            if (nim.isNotEmpty() && nama.isNotEmpty()) {
                val mahasiswa = Mahasiswa(nim, nama)
                val status = databaseHelper.tambahData(mahasiswa)

                if (status > -1) {
                    Toast.makeText(this, "Data tersimpan!", Toast.LENGTH_SHORT).show()
                    editTextNim.text.clear()
                    editTextNama.text.clear()
                    muatData() // Refresh ListView
                } else {
                    Toast.makeText(this, "Gagal menyimpan (NIM mungkin duplikat)",
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }


        buttonUbah.setOnClickListener {
            val nim = editTextNim.text.toString()
            val nama = editTextNama.text.toString()

            if (nim.isNotEmpty() && nama.isNotEmpty()) {
                val mahasiswa = Mahasiswa(nim, nama)
                val rowsUpdated = databaseHelper.ubahData(mahasiswa)

                if (rowsUpdated > 0) {
                    Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                    muatData() // Refresh ListView
                } else {
                    Toast.makeText(this, "Gagal mengupdate. NIM tidak ditemukan.",
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Isi NIM dan Nama untuk mengupdate",
                    Toast.LENGTH_SHORT).show()
            }
        }

        // tombol hapus (berdasarkan nim)
        buttonHapus.setOnClickListener {
            val nim = editTextNim.text.toString()

            if (nim.isNotEmpty()) {
                val rowsDeleted = databaseHelper.hapusData(nim)

                if (rowsDeleted > 0) {
                    Toast.makeText(this, "Data NIM $nim dihapus", Toast.LENGTH_SHORT).show()
                    editTextNim.text.clear()
                    editTextNama.text.clear()
                    muatData()
                } else {
                    Toast.makeText(this, "NIM tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Masukkan NIM yang akan dihapus", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun inisialisasiKomponen() {
        editTextNim = findViewById(R.id.edit_text_nim_mahasiswa)
        editTextNama = findViewById(R.id.edit_text_nama_mahasiswa)
        buttonSimpan = findViewById(R.id.button_simpan_data)
        buttonUbah = findViewById(R.id.button_ubah_data)
        buttonHapus = findViewById(R.id.button_hapus_data)
        listViewData = findViewById(R.id.list_view_data_mahasiswa)
    }

    private fun muatData() {
        // mengambil/get data dari database berupa arraylist<string>
        val listMahasiswa = databaseHelper.tampilkanSemuaData()

        // memasukkan ke adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listMahasiswa)
        listViewData.adapter = adapter
    }
}