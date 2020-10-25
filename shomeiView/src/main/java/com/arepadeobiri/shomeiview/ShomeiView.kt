package com.arepadeobiri.shomeiview


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import kotlin.math.abs


private const val STROKE_WIDTH = 12f


class ShomeiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private lateinit var frame: Rect

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var canvasColor = Color.parseColor("#ffffff")

    private var drawColor = Color.parseColor("#000000")

    private val paint = Paint().apply {

        color = drawColor

        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f


    private var currentX = 0f
    private var currentY = 0f


    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        if (::extraBitmap.isInitialized) extraBitmap.recycle()

        extraBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(canvasColor)


        val inset = 40
        frame = Rect(inset, 0 - 10, w + 10, h + 10)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event.let { x -> motionTouchEventX = x!!.x }
        event.let { x -> motionTouchEventY = x!!.y }


        when (event?.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }


        return true
    }

    private fun touchUp() {

    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)

//        if (dx >= touchTolerance || dy >= touchTolerance) {
        path.quadTo(
            currentX,
            currentY,
            (motionTouchEventX + currentX) / 2,
            (motionTouchEventY + currentY) / 2
        )
        currentX = motionTouchEventX
        currentY = motionTouchEventY

        extraCanvas.drawPath(path, paint)
//        }


        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
        canvas?.drawRect(frame, paint)


    }


    @SuppressLint("WrongThread")
    fun getUri(directory: File?): Uri {


        val fileName = "signature.png"
        val file = File(directory, fileName)
//            Log.i(TAG, "" + file)
//            if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            val matrix = Matrix()

            matrix.postRotate(-90f)

            val scaledBitmap = Bitmap.createScaledBitmap(extraBitmap, width, height, true)

            val rotatedBitmap = Bitmap.createBitmap(
                scaledBitmap,
                0,
                0,
                scaledBitmap.width,
                scaledBitmap.height,
                matrix,
                true
            )

            rotatedBitmap.compress(
                Bitmap.CompressFormat.PNG,
                90,
                out
            )
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        //Log.d("Ares",file.toUri().toString())
        return file.toUri()
    }


    fun setCanvasColor(color : Int){
        canvasColor = color
    }

    fun setDrawColor(color : Int){
        drawColor = color
    }

}