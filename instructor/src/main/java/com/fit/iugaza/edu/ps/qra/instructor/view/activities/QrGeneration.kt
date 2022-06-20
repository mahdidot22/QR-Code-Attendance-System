package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityQrGenerationBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.time.LocalDateTime
import java.util.*


class QrGeneration : AppCompatActivity() {
    private var _binding: ActivityQrGenerationBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQrGenerationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            appbar.title.text = "صناعة الباركود"
            appbar.btnBack.setOnClickListener { finish() }
            btnManualAdding.setOnClickListener {
                Constants().navigation(
                    this@QrGeneration,
                    ManualAdding::class.java
                )
            }
            reload.setOnClickListener {
                btnQrGenerate.setImageBitmap(
                    getQrCodeBitmap(
                        "${Calendar.getInstance().time}",
                        "2468",
                        "101",
                        "مثال"
                    )
                )
                reload.visibility = View.GONE
            }
        }

    }

    fun getQrCodeBitmap(
        generatingTime: String,
        password: String,
        division: String,
        courseName: String
    ): Bitmap {
        val size = 512
        val qrCodeContent = "hello"
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}