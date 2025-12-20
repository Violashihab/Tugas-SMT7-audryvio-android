package com.example.pemesanantiket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etJumlah: EditText
    private lateinit var btnPesan: Button


    private val channelId = "channel_pemesanan_01"
    private val notificationId = 101
    private var namaPemesan = ""
    private var jumlahTiket = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNama = findViewById(R.id.etNama)
        etJumlah = findViewById(R.id.etJumlah)
        btnPesan = findViewById(R.id.btnPesan)

        createNotificationChannel()

        btnPesan.setOnClickListener {
            prosesPemesanan()
        }

        handleIntent(intent)
    }

    private fun prosesPemesanan() {
        namaPemesan = etNama.text.toString().trim()
        jumlahTiket = etJumlah.text.toString().trim()

        if (namaPemesan.isEmpty()) {
            etNama.error = "Nama harus diisi"
            etNama.requestFocus()
            return
        }

        if (jumlahTiket.isEmpty()) {
            etJumlah.error = "Jumlah tiket harus diisi"
            etJumlah.requestFocus()
            return
        }

        if (jumlahTiket.toIntOrNull() == null || jumlahTiket.toInt() <= 0) {
            etJumlah.error = "Jumlah tiket harus angka positif"
            etJumlah.requestFocus()
            return
        }


        showLoadingDialog()
    }

    private fun showLoadingDialog() {
        val viewLoading = LayoutInflater.from(this)
            .inflate(R.layout.custom_loading, null)


        val dialog = AlertDialog.Builder(this)
            .setView(viewLoading)
            .setCancelable(false)
            .create()

        dialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            tampilkanNotifikasi()
        }, 2000)
    }

    private fun tampilkanNotifikasi() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("FROM_NOTIFICATION", true)
            putExtra("NAMA", namaPemesan)
            putExtra("JUMLAH", jumlahTiket)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Pemesanan Berhasil!")
            .setContentText("Tiket atas nama $namaPemesan berhasil dipesan")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
                Toast.makeText(
                    this,
                    "Aktifkan izin notifikasi di pengaturan aplikasi",
                    Toast.LENGTH_LONG
                ).show()
                return
            }
        }

        notificationManager.notify(notificationId, builder.build())
        Toast.makeText(this, "Pemesanan berhasil! Cek notifikasi", Toast.LENGTH_LONG).show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Pemesanan"
            val description = "Channel untuk notifikasi pemesanan tiket"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                this.description = description
                enableVibration(true)
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.getBooleanExtra("FROM_NOTIFICATION", false) == true) {
            val nama = intent.getStringExtra("NAMA") ?: ""
            val jumlah = intent.getStringExtra("JUMLAH") ?: ""

            tampilkanDetailPesanan(nama, jumlah)
        }
    }

    private fun tampilkanDetailPesanan(nama: String, jumlah: String) {
        AlertDialog.Builder(this)
            .setTitle("Detail Pemesanan")
            .setMessage(
                """
                PEMESANAN BERHASIL!
                
                Nama Pemesan: $nama
                Jumlah Tiket: $jumlah
                
                Terima kasih telah memesan!
                """.trimIndent()
            )
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                etNama.text.clear()
                etJumlah.text.clear()
                etNama.requestFocus()
            }
            .setCancelable(false)
            .show()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }
}