package com.example.latihaninteraksi

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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    // Konstanta untuk Notifikasi
    private val CHANNEL_ID = "channel_latihan_01"
    private val NOTIFICATION_ID = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Membuat Channel Notifikasi (Wajib untuk Android 8.0+)
        createNotificationChannel()

        // 1. TOAST
        findViewById<Button>(R.id.btn_toast).setOnClickListener {
            Toast.makeText(this, "Halo! Ini adalah pesan Toast.", Toast.LENGTH_SHORT).show()
        }

        // 2. ALERT DIALOG
        findViewById<Button>(R.id.btn_alert).setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi")
            builder.setMessage("Apakah Anda yakin ingin menghapus data ini?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            // Tombol Positif
            builder.setPositiveButton("Ya, Hapus") { dialog, _ ->
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            // Tombol Negatif
            builder.setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss() // Menutup dialog
            }

            builder.show()
        }

        // 3. CUSTOM PROGRESS DIALOG (LOADING)
        findViewById<Button>(R.id.btn_loading).setOnClickListener {
            // Mengambil layout custom yang sudah dibuat
            val viewLoading = LayoutInflater.from(this).inflate(R.layout.custom_loading, null)

            // Membangun Dialog tanpa tombol (hanya view)
            val builder = AlertDialog.Builder(this)
            builder.setView(viewLoading)
            builder.setCancelable(false) // User tidak bisa menutup dengan klik luar

            val dialog = builder.create()
            dialog.show()

            // Simulasi loading selama 3 detik, lalu tutup otomatis
            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
                Toast.makeText(this, "Proses Selesai!", Toast.LENGTH_SHORT).show()
            }, 3000)
        }

        // 4. NOTIFICATION
        findViewById<Button>(R.id.btn_notification).setOnClickListener {
            tampilkanNotifikasi()
        }
    }

    private fun tampilkanNotifikasi() {
        // Intent agar saat notifikasi diklik, aplikasi terbuka kembali
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Ikon Wajib
            .setContentTitle("Info Kampus")
            .setContentText("Jadwal Praktikum telah diperbarui.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // Aksi klik
            .setAutoCancel(true) // Hilang saat diklik

        with(NotificationManagerCompat.from(this)) {
            // Cek Izin untuk Android 13+
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Jika belum diizinkan, minta izin (simplifikasi untuk latihan)
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Channel hanya diperlukan untuk API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Latihan"
            val descriptionText = "Channel untuk notifikasi latihan android"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Mendaftarkan channel ke sistem
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}