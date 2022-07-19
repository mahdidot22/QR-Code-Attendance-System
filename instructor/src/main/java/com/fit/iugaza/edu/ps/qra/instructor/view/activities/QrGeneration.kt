package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.R
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityQrGenerationBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*


class QrGeneration : AppCompatActivity() {
    private var _binding: ActivityQrGenerationBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        Constants().statusBarColor(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityQrGenerationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            appbar.title.text = "صناعة الباركود"
            appbar.btnBack.setOnClickListener { finish() }
            btnManualAdding.setOnClickListener {
                Constants().navigation(
                    intent.getStringExtra("courseId").toString(),
                    this@QrGeneration,
                    ManualAdding::class.java
                )
            }
            val calendar: Calendar = Calendar.getInstance()
            val hourOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)
            val minute: Int = calendar.get(Calendar.MINUTE)
            reload.setOnClickListener {
                if ((intent.getStringExtra("startTime")
                        .toString().toInt() == hourOfDay) && (intent.getStringExtra("startMinute")
                        .toString().toInt() + 20) > minute
                ) {
                    btnQrGenerate.setImageBitmap(
                        getQrCodeBitmap(
                            intent.getStringExtra("courseId").toString()
                        )
                    )
                    reload.visibility = View.GONE
                } else {
                    Constants().createToast(
                        this@QrGeneration,
                        msg.toastText,
                        msg.root,
                        "غير مسموح إنشاء كود بعد 20 دقيقة على بدأ المحاضرة!"
                    )
                }
            }
        }

    }

    fun getQrCodeBitmap(
        courseId: String
    ): Bitmap {
        val size = 512
        val bits = QRCodeWriter().encode(courseId, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}