package com.example.formdosen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener // Pastikan library KTX aktif di build.gradle
import com.example.formdosen.databinding.ActivityFormDosenBinding
import com.example.formdosen.network.ApiClient
import com.example.formdosen.model.ResponseDosen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormDosenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormDosenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupValidation()
        setupButton()

        // Tambahkan ini agar saat pertama buka, tombol simpan langsung divalidasi (mati)
        validateForm()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.jabatan_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerJabatan.adapter = adapter
    }

    private fun setupValidation() {
        // Gunakan addTextChangedListener dari androidx.core.widget
        binding.etNidn.addTextChangedListener {
            validateForm()
        }

        binding.etNamaDosen.addTextChangedListener {
            validateForm()
        }

        binding.spinnerJabatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                validateForm()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun validateForm() {
        val nidn = binding.etNidn.text.toString().trim()
        val nama = binding.etNamaDosen.text.toString().trim()
        val jabatanPos = binding.spinnerJabatan.selectedItemPosition

        // Tombol hanya aktif jika NIDN & NAMA diisi, dan SPINNER tidak di posisi 0
        val isValid = nidn.isNotEmpty() && nama.isNotEmpty() && jabatanPos > 0
        binding.btnSimpan.isEnabled = isValid

        // Opsional: Ubah alpha tombol agar terlihat jelas kalau mati/aktif
        if (isValid) binding.btnSimpan.alpha = 1.0f else binding.btnSimpan.alpha = 0.5f
    }

    private fun setupButton() {
        binding.btnSimpan.setOnClickListener {
            val nidn = binding.etNidn.text.toString().trim()
            val nama = binding.etNamaDosen.text.toString().trim()
            val jabatan = binding.spinnerJabatan.selectedItem.toString()

            // Menampilkan loading sederhana agar user tahu proses sedang berjalan
            binding.btnSimpan.isEnabled = false
            binding.btnSimpan.text = "Loading..."

            ApiClient.instance.insertDosen(nidn, nama, jabatan)
                .enqueue(object : Callback<ResponseDosen> {
                    override fun onResponse(call: Call<ResponseDosen>, response: Response<ResponseDosen>) {
                        binding.btnSimpan.isEnabled = true
                        binding.btnSimpan.text = "Simpan Dosen"

                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.kode == 1) {
                                Toast.makeText(this@FormDosenActivity, "✅ ${result.pesan}", Toast.LENGTH_LONG).show()

                                // Reset Form
                                binding.etNidn.text?.clear()
                                binding.etNamaDosen.text?.clear()
                                binding.spinnerJabatan.setSelection(0)
                            } else {
                                Toast.makeText(this@FormDosenActivity, "❌ Gagal: ${result?.pesan}", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this@FormDosenActivity, "⚠️ Error Server: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseDosen>, t: Throwable) {
                        binding.btnSimpan.isEnabled = true
                        binding.btnSimpan.text = "Simpan Dosen"
                        Toast.makeText(this@FormDosenActivity, "🌐 Masalah Jaringan: ${t.message}", Toast.LENGTH_LONG).show()
                        Log.e("API_ERROR", t.message.toString())
                    }
                })
        }
    }
}
