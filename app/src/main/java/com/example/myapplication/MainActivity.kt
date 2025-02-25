package com.example.myapplication

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity(),MyView.Listener {

    private lateinit var canvasView: MyView
    private lateinit var textTv: TextView
    private lateinit var text1: TextView
    private lateinit var text2: TextView
   var victory = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         canvasView = findViewById(R.id.canvasView)
        textTv = findViewById(R.id.textView)
        text1 = findViewById(R.id.text1)
        text2 = findViewById(R.id.text2)

           canvasView.listener = this
        textTv.setTextColor(getColor(R.color.blue))
        textTv.setText("Выбирайте ход")

        text1.setOnClickListener { canvasView.onSize(); canvasView.model[Random.nextInt(0,2)][Random.nextInt(0,2)].flag = 1;
         textTv.setTextColor(getColor(R.color.blue)); textTv.setText("Играем");victory = true }

          text2.setOnClickListener { canvasView.onSize();textTv.setTextColor(getColor(R.color.blue)); textTv.setText("Играем"); victory = true }

    }

    override fun onClick( x: Int, y: Int) {

        if(!victory) return


        canvasView.model[x][y].flag = 2
        canvasView.invalidate()

              onCheck()


             if (step(1)) {onCheck();canvasView.invalidate(); return}
             if (step(2)) {onCheck();canvasView.invalidate(); return}


        if ( canvasView.model[1][1].flag != null ) {
            val l = canvasView.model[1][1].flag


            if (canvasView.model[0][0].flag == l ) { if (canvasView.model[2][2].flag == null ) { canvasView.model[2][2].flag = 1; onCheck(); return} }
            if (canvasView.model[2][0].flag == l ) { if (canvasView.model[0][2].flag == null )  {canvasView.model[0][2].flag = 1; onCheck(); return }}
            if (canvasView.model[2][2].flag == l ) { if (canvasView.model[0][0].flag == null ) { canvasView.model[0][0].flag = 1; onCheck(); return }}
            if (canvasView.model[0][2].flag == l ) { if (canvasView.model[2][0].flag == null ) { canvasView.model[2][0].flag = 1; onCheck(); return }}

        }
              onCheck()
               for (a in 0.. 2) { for (b in 0..2) {

                   if (canvasView.model[a][b].flag == null) { canvasView.model[a][b].flag = 1;onCheck(); return }  }
               }
        canvasView.invalidate(); textTv.setText("Ничья")

    }

    private fun onVictory(flag: Int): Boolean {
        var t = false


        for (i in 0..2 ) { if ( (( canvasView.model[i][0].flag == flag ) && (canvasView.model[i][1].flag == flag) && (canvasView.model[i][2].flag == flag) ||
                (canvasView.model[0][i].flag == flag) && (canvasView.model[1][i].flag == flag ) && (canvasView.model[2][i].flag == flag)))
        { t = true; break }}

         if (((canvasView.model[0][0].flag == flag) && (canvasView.model[1][1].flag == flag) &&  (canvasView.model[2][2].flag == flag)) ||
             ((canvasView.model[2][0].flag == flag) && (canvasView.model[1][1].flag == flag) && (canvasView.model[0][2].flag == flag)))
         { t = true }

        if (t) { when (flag) {
            1 -> { textTv.setTextColor(getColor(R.color.red)); textTv.setText("Вы проиграли") }
            2 -> { textTv.setTextColor(getColor(R.color.green));textTv.setText("Вы выиграли") }}
            victory = false
        }

        return t
    }

    private fun step (flag: Int): Boolean {
        var v = 0

        for (j in 0..2) { for (i in 0..2) { if(canvasView.model[i][j].flag == flag){v++}}; if(v == 2) {

            for (i in 0..2) { if (canvasView.model[i][j].flag == null ) { canvasView.model[i][j].flag = 1; return true }} }; v = 0 }

        v = 0
        for (i in 0..2) { for (j in 0..2) { if(canvasView.model[i][j].flag == flag){v++}}; if(v == 2) { canvasView.model[i]
            .forEach { if (it.flag == null) { it.flag = 1; return true;}} }; v = 0 }

         if ((canvasView.model[0][0].flag == canvasView.model[2][2].flag ) || (canvasView.model[2][0].flag == canvasView.model[0][2].flag))
         { if (canvasView.model[1][1].flag == null) { canvasView.model[1][1].flag =1; return true} }



        return false
    }


    private fun onCheck() {
        onVictory(1); onVictory(2)
       var f = 0;
        canvasView.model.forEach { it.forEach { if (it.flag != null) f++ } }

        if (f == 9) { textTv.setText("Ничья")}


    }
}










