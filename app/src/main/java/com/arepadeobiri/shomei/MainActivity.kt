package com.arepadeobiri.shomei

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.arepadeobiri.shomeiview.ShomeiView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shomeiView = findViewById<ShomeiView>(R.id.shomeiView)

        var count = 1


        findViewById<Button>(R.id.main_button).apply {
            setOnClickListener {

                Toast.makeText(this.context, "Button clicked", Toast.LENGTH_LONG).show()
                shomeiView.setFrameType(ShomeiView.FrameType.OneSide)
                shomeiView.invalidate()

            }
        }


    }
}