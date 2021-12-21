package com.example.proyekandroid

import android.R.attr
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.WriterException

import android.R.attr.bitmap
import android.graphics.Point
import android.util.Log
import android.view.Display

import android.view.WindowManager
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import java.text.SimpleDateFormat
import java.util.*


class QRCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val ivQrCode = findViewById<ImageView>(R.id.ivQrCode)

        val manager = getSystemService(WINDOW_SERVICE) as WindowManager

        // initializing a variable for default display.

        // initializing a variable for default display.
        val display: Display = manager.defaultDisplay

        // creating a variable for point which
        // is to be displayed in QR Code.

        // creating a variable for point which
        // is to be displayed in QR Code.
        val point = Point()
        display.getSize(point)

        // getting width and
        // height of a point

        // getting width and
        // height of a point
        val width: Int = point.x
        val height: Int = point.y

        // generating dimension from width and height.

        // generating dimension from width and height.
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        val qrgEncoder = QRGEncoder(SimpleDateFormat("dd-MM-yyyy").format(Date()), null, QRGContents.Type.TEXT, dimen)
        try {
            // getting our qrcode in the form of bitmap.
            val bitmap = qrgEncoder.encodeAsBitmap()
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            ivQrCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            // this method is called for
            // exception handling.
            Log.e("QRCODEERR", e.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}