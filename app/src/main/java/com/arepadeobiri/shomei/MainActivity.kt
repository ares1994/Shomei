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


            shomeiView.setFrameType(ShomeiView.FrameType.DirectOppositesTopBottom)
            shomeiView.setDrawColor(Color.parseColor("#4DE897"))



            setOnClickListener {

//                Toast.makeText(this.context, "Button clicked", Toast.LENGTH_LONG).show()
//                shomeiView.setFrameType(ShomeiView.FrameType.OneSide)
//                shomeiView.setDrawColor(Color.parseColor("#016938"))






                when(count){
                    1->{

                    }
                    2->{
                    }
                    3->{
                    }
                    4->{
//                        shomeiView.setCanvasColor(Color.parseColor("#7EC0F9"))
                    }

                }


                shomeiView.getUri(externalCacheDir)


                count++


            }
        }


    }
}