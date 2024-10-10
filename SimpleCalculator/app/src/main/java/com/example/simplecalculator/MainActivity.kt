package com.example.simplecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {

    private var tvResult: TextView? = null
    private var tvInput: TextView? = null
    private var tvResultCheck = false
    private var tvInputCheck = false
    private var FirstNum: Double = 0.0
    private var SecondNum: Double = 0.0
    private var ResultNum: Double = 0.0
    private var ArithOper: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvInput = findViewById(R.id.tvInput)
    }

    fun numClick(view : View){
        if(!tvResultCheck){
            tvResult?.text=""
        }
        tvResult?.append((view as Button).text)
        tvResultCheck=true
        var str=tvResult?.text.toString()
        SecondNum=str.toDouble()
    }

    fun BackClick(view: View){
        var str=tvResult?.text.toString()
        if(str.isNotEmpty()){
            str=str.substring(0,str.length-1)
            tvResult?.text=str
        }
    }

    fun ClearClick(view: View){
        tvResult?.text=""
        tvResultCheck=false
        SecondNum=0.0
    }

    fun ACClick(view: View){
        tvResult?.text=""
        tvInput?.text=""
        tvResultCheck=false
        tvInputCheck=false
        FirstNum=0.0
        SecondNum=0.0
        ArithOper=""
    }

    fun DotClick(view: View){
        var dotCheck=false

        tvResult?.text?.let{
            if(it.contains(".")){
                dotCheck=true
            }
        }
        if(!tvResultCheck){
            tvResult?.text="0."
            tvResultCheck=true
        }else if(!dotCheck){
            tvResult?.append(".")
        }
    }

    fun OperClick(view: View){
        val btn=(view as Button).text

        if(!tvInputCheck){
            tvInput?.text=if(SecondNum%1.0==0.0) "${SecondNum.toLong()} $btn" else "$SecondNum $btn"
            FirstNum=SecondNum
            tvInputCheck=true
            tvResultCheck=false
        } else{
            if(!tvResultCheck){
                tvInput?.text=if(FirstNum%1.0==0.0) "${FirstNum.toLong()} $btn" else "$FirstNum $btn"
            }else{
                ResultNum=when(ArithOper){
                    "+" -> FirstNum+SecondNum
                    "-" -> FirstNum-SecondNum
                    "x" -> FirstNum*SecondNum
                    else -> FirstNum / SecondNum
                }
                ResultNum=round(ResultNum*1000000000)/1000000000
                tvInput?.text=if(ResultNum%1.0==0.0) "${ResultNum.toLong()} $btn" else "${ResultNum} $btn"
                tvResultCheck=false
                FirstNum=ResultNum
            }
        }
        ArithOper=btn.toString()
    }

    fun EqualClick(view: View){
        tvInput?.text=""
        tvInput?.append(if(FirstNum % 1.0==0.0) "${FirstNum.toLong()} $ArithOper" else "$FirstNum $ArithOper")
        tvInput?.append(if(SecondNum % 1.0==0.0) "${SecondNum.toLong()} =" else "$SecondNum")
        ResultNum=when(ArithOper){
            "+"->FirstNum+SecondNum
            "-"->FirstNum-SecondNum
            "X"-> FirstNum*SecondNum
            else-> FirstNum/SecondNum
        }
        ResultNum=round(ResultNum*1000000000)/1000000000
        tvResult?.text=if(ResultNum%1.0==0.0) "${ResultNum.toLong()}" else "${ResultNum}"
        FirstNum=ResultNum
        tvResultCheck=false
    }


}