package com.arepadeobiri.shomeiview


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.annotation.ColorInt
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.shomeiview.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.util.logging.Handler
import kotlin.math.abs


private const val STROKE_WIDTH = 12f


class ShomeiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val runningHandler = android.os.Handler()


    lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private lateinit var frame: Rect

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var canvasColor = Color.parseColor("#ffffff")

    private var drawColor = Color.parseColor("#000000")

    private var side = Side.Left
    private var frameType = FrameType.AllSides

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



        when (frameType) {
            FrameType.AllSides -> {
                frame = Rect(inset, inset, w - inset, h - inset)
            }
            FrameType.OneSide -> {
                frame = when (side) {
                    Side.Left -> {
                        Rect(inset, 0 - 10, w + 10, h + 10)
                    }
                    Side.Right -> {
                        Rect(0 - 10, 0 - 10, w - inset, h + 10)
                    }
                    Side.Top -> {
                        Rect(0 - 10, inset, w + 10, h + 10)
                    }
                    Side.Bottom -> {
                        Rect(0 - 10, 0 - 10, w + 10, h - inset)
                    }

                }
            }
            FrameType.DirectOppositesTopBottom -> {
                frame = Rect(0 - 10, inset, w + 10, h - inset)

            }
            FrameType.DirectOppositesLeftRight -> {
                frame = Rect(inset, 0 - 10, w - inset, h + 10)

            }
            FrameType.None -> {
                frame = Rect()
            }
        }


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

        path


        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
        canvas?.drawRect(frame, paint)


    }


    fun getImageFile(
        directory: File?,
        imageFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
        rotate: Float = 0f
    ): File? {


        val fileName = "signature.${
            when (imageFormat) {
                Bitmap.CompressFormat.PNG -> "png"
                Bitmap.CompressFormat.JPEG -> "jpg"
                Bitmap.CompressFormat.WEBP -> "webp"
            }
        }"
        val file = File(directory, fileName)
//            Log.i(TAG, "" + file)
//            if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            val matrix = Matrix()

            matrix.postRotate(rotate)

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
                imageFormat,
                90,
                out
            )
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        //Log.d("Ares",file.toUri().toString())
        return file
    }


    fun setCanvasColor(@ColorInt color: Int) {
        runningHandler.postDelayed({
            canvasColor = color
            onSizeChanged(width, height, width, height)

            invalidate()
        }, 200)
    }

    fun setDrawColor(@ColorInt color: Int) {
        runningHandler.postDelayed({
            drawColor = color
            paint.color = drawColor
            invalidate()
        }, 200)
    }


    fun setFrameType(type: FrameType, selectedSide: Side? = null) {

        runningHandler.postDelayed({
            frameType = type
            selectedSide?.let { side = it }



            onSizeChanged(width, height, width, height)



            invalidate()
        }, 200)
    }


    enum class FrameType {
        OneSide,
        DirectOppositesTopBottom,
        DirectOppositesLeftRight,
        AllSides,
        None
    }

    enum class Side {
        Left,
        Right,
        Top,
        Bottom
    }
}