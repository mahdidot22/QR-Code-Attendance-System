package com.fit.iugaza.edu.ps.qra.std.view.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.std.databinding.ActivityQrScanningBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*


class QrScanning : AppCompatActivity() {
    private val CAMERA_CODE = 200
    var _binding: ActivityQrScanningBinding? = null
    val binding get() = _binding!!
    private val db = Firebase.firestore
    private lateinit var codeScanner: CodeScanner
    private var _scannerView: CodeScannerView? = null
    private val scannerView get() = _scannerView!!
    private val calendar: Calendar = Calendar.getInstance()
    private val hourOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)
    private val minute: Int = calendar.get(Calendar.MINUTE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQrScanningBinding.inflate(layoutInflater)
        _scannerView = binding.scannerView
        codeScanner = CodeScanner(this, scannerView)
        Constants().statusBarColor(this)
        setContentView(binding.root)
        binding.apply {
            appbar.title.text = "ماسح الباركود"
            appbar.btnBack.setOnClickListener { finish() }
            galaryScan.setOnClickListener { gallaryScaner() }

        }
        setupPermission()
        codeScannerProperties()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun codeScannerProperties() {
        val startTime = intent.getStringExtra("startTime")
        val startMinute = intent.getStringExtra("startMinute")
        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        // Callbacks
        codeScanner.decodeCallback = DecodeCallback { result ->
            runOnUiThread {
                if (result.text == intent.getStringExtra("courseId")!!) {
                    checkScanTime(
                        startTime.toString().toInt(),
                        hourOfDay,
                        startMinute.toString().toInt(),
                        minute,
                        result.text
                    )
                } else {
                    Snackbar.make(
                        binding.root,
                        "خطأ في تطابق المساقات، يرجى التأكد من مسح الكود الصحيح لهذا المساق.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Snackbar.make(
                    binding.root, " خطأ في الكاميرا: ${it.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun checkScanTime(
        startTime: Int,
        hourOfDay: Int,
        startMinute: Int,
        minute: Int,
        courseId: String
    ) {
        if (startTime == hourOfDay && startMinute + 10 > minute) {
            db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr")
                .collection("students").whereEqualTo("studentId", SessionMng(this).getId("id"))
                .get().addOnSuccessListener {
                    for (doc in it) {
                        db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr")
                            .collection("students").document(doc.id)
                            .update("attending.$courseId", FieldValue.increment(1))
                    }
                }
        } else {
            Snackbar.make(
                binding.root,
                "لقد أفلت الوقت المسموح لتسجيل الحضور في هذا المساق",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun setupPermission() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(binding.root, "تحتاج لإذن الكاميرا!!", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun gallaryScaner() {
        val pickIntent = Intent(Intent.ACTION_PICK)
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(pickIntent, 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            111 -> {
                if (data == null || data.data == null) {
                    return
                }
                val uri: Uri? = data.data
                try {
                    val inputStream: InputStream? = contentResolver.openInputStream(uri!!)
                    var bitmap = BitmapFactory.decodeStream(inputStream)
                    if (bitmap == null) {
                        Toast.makeText(this, " صيغة غير صالحة ,$uri", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val width = bitmap.width
                    val height = bitmap.height
                    val pixels = IntArray(width * height)
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
                    bitmap.recycle()
                    bitmap = null
                    val source = RGBLuminanceSource(width, height, pixels)
                    val bBitmap = BinaryBitmap(HybridBinarizer(source))
                    val reader = MultiFormatReader()
                    val startTime = intent.getStringExtra("startTime")
                    val startMinute = intent.getStringExtra("startMinute")
                    try {
                        val result = reader.decode(bBitmap)
                        checkScanTime(
                            startTime.toString().toInt(),
                            hourOfDay,
                            startMinute.toString().toInt(),
                            minute,
                            result.text
                        )
                    } catch (e: NotFoundException) {
                        Log.e("TAG", "decode exception", e)
                    }
                } catch (e: FileNotFoundException) {
                    Log.e("TAG", "can not open file" + uri.toString(), e)
                }
            }
        }
    }
}