/**
 * @author: ABDALLAH, NITEKA, DIALLO
 */
package com.mbds.calculatrice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import com.mbds.calculatrice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var bd: ActivityMainBinding

    private fun isChiffre(strChiffre: String):Boolean {
        return when (strChiffre){
            "1","2", "3","4", "5","6", "7", "8", "9", "0" -> true
            else -> false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityMainBinding.inflate(LayoutInflater.from(baseContext))
        setContentView(bd.root)
        val chiffres: List<Button> = listOf(
                bd.btn0,
                bd.btn1,
                bd.btn2,
                bd.btn3,
                bd.btn4,
                bd.btn5,
                bd.btn6,
                bd.btn7,
                bd.btn8,
                bd.btn9
        )
        val operations: List<Button> = listOf(
                bd.btnDivise,
                bd.btnPlus,
                bd.btnMoin,
                bd.btnMultiple
        )
        for (chiffre in chiffres) {
            chiffre.setOnClickListener {
                val strChiffre = chiffre.text.toString()
                bd.expressionTxt.text = bd.expressionTxt.text.toString() + strChiffre
            }
        }
        for (op in operations) {
            op.setOnClickListener {
                val expText = bd.expressionTxt.text
                if (expText.isNotEmpty()){
                    val dernierElementExpressionTxt = expText[expText.lastIndex].toString()
                    if (isChiffre(dernierElementExpressionTxt)){
                        bd.expressionTxt.text = expText.toString() + op.text.toString()
                    }else {
                        bd.expressionTxt.text = expText.toString().slice(IntRange(0, expText.lastIndex - 1)) + op.text.toString()
                    }
                }else {
                    Toast.makeText(baseContext, "Saisissez un nombre", Toast.LENGTH_LONG).show()
                }

            }
        }
        bd.btnEgal.setOnClickListener {
            if (bd.expressionTxt.text.isNotBlank()) {
                val nombres: MutableList<Int> = mutableListOf()
                val operators: MutableList<String> = mutableListOf()
                var currentNombre = ""
                bd.expressionTxt.text.toList().forEach{
                    if(isChiffre(it.toString())){
                        currentNombre += it
                    }else {
                        operators.add(it.toString())
                        nombres.add(currentNombre.toInt())
                        currentNombre = ""
                    }
                }
                if (currentNombre.isNotEmpty()) nombres.add(currentNombre.toInt())

                if (nombres.size == operators.size + 1) {
                    var idx = 1
                    var res = nombres.get(0) * 1.0
                    for (op in operators){
                        val nb = nombres.get(idx++)
                        res = when(op){
                            "+" -> res + nb
                            "-" -> res - nb
                            "*" -> res * nb
                            "/" -> res / nb
                            else -> res
                        }
                    }
                    bd.resultatTxt.text = res.toString()
                }else {
                    Toast.makeText(baseContext, "Expression incorrecte: \"${bd.expressionTxt.text}\"", Toast.LENGTH_LONG).show()
                }

            }
            else {
                bd.resultatTxt.text = "0"
            }

            bd.expressionTxt.text =""
        }

    }
}