package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.google.android.material.animation.AnimatableView.Listener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyView(context: Context,attributeSet: AttributeSet): View(context,attributeSet)  {


    var listener: Listener? = null
    var a: Model = Model(0,0,null)
    var model: Array<Array<Model>> = arrayOf(
        Array(3,{a}),
        Array(3,{a}),
       Array(3,{a}))

       private val paint = Paint()
       private lateinit var canvas: Canvas
        var size = 0
       private lateinit var  setOnClick: View
       var x = 100; var y = 100


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
           this.canvas = canvas

        paint.strokeWidth = 8f
         paint.style = Paint.Style.STROKE
          setOnClick = findViewById(R.id.canvasView)

        for ( i in 0..model.size -1 ) { for (j in 0..model.size -1)

        { if ( model[i][j].flag == 1) { drawCross(model[i][j].x,model[i][j].y) }
          if ( model[i][j].flag == 2) { drawCircle(model[i][j].x,model[i][j].y)}
        } }


        for ( i in 0..height step ( height / 3) ) {
        canvas.drawLine(i.toFloat(),0f,i.toFloat(),height.toFloat(),paint)
        canvas.drawLine(0f,i.toFloat(),width.toFloat(),i.toFloat(),paint,)
        }
    }

   private fun drawCircle(x: Int, y: Int) {
        canvas.drawCircle(x.toFloat(),y.toFloat(),40f,paint)
    }

    private fun drawCross(x: Int,y: Int) {

        canvas.drawLine(x.toFloat() - 30f,y.toFloat() - 30f,x.toFloat() + 30f,y.toFloat() + 30f,paint)
        canvas.drawLine(x.toFloat() + 30f,y.toFloat() - 30f,x.toFloat() - 30f,y.toFloat() + 30f,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (size == 0) { onSize() }

     x = event.x.toInt() ; y = event.y.toInt()

        when(event.action) { MotionEvent.ACTION_DOWN ->  {

                val xx =  when ( x ) {
                      in 0..size/3 -> 0
                      in size/3..size * 2/3 -> 1
                      in size * 2/3..size -> 2
                    else -> -1 }

                val yy = when ( y ) {
                    in 0..size/3 -> 0
                    in size/3..size * 2/3 -> 1
                    in size *2/3..size -> 2
                   else -> -1
                }

               if ( model[xx][yy].flag == null ) listener?.onClick(xx,yy) }}


        return true
    }

   fun onSize(){

       size = height

          for ( i in 0..2) {
            for ( j in 0..2) { model[i][j] = Model(size/6 + i*size/3,size/6 + j*size/3,null) }
          }
               invalidate()
    }

    interface Listener {
        fun onClick(x: Int,y: Int) }

}












