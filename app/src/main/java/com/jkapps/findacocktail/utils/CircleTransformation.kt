package com.jkapps.findacocktail.utils

import android.graphics.*
import com.squareup.picasso.Transformation


class CircleTransformation(private val strokeWidth : Float) : Transformation {

    override fun transform(source: Bitmap): Bitmap {

        val size = Math.min(source.width, source.height)

        val width = (source.width - size) / 2f
        val height = (source.height - size) / 2f

        val squaredBitmap = Bitmap.createBitmap(source, width.toInt(), height.toInt(), size, size)
        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val bitmapShader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val paint = Paint().apply {
            shader = bitmapShader
            isAntiAlias = true
        }

        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        val newPaint = Paint().apply {
            color = 0xFFFF6E40.toInt()
            style = Paint.Style.STROKE
            strokeWidth = this@CircleTransformation.strokeWidth * 2
        }

        canvas.drawCircle(radius, radius, radius - strokeWidth, newPaint)
        source.recycle()

        return bitmap
    }

    override fun key(): String {
        return "circleTransformation $strokeWidth"
    }
}