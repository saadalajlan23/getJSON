package com.example.getjson



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var tvAdvice:TextView
    private lateinit var btnAdvice:Button

    val adviceUrl = "https://api.adviceslip.com/advice"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Coroutines App"
        tvAdvice = findViewById(R.id.tv_advice)
        btnAdvice = findViewById(R.id.bt_advice)
        tvAdvice.text = "New Advice"


        btnAdvice.setOnClickListener(){
                requestApi()
        }







    }

    private fun requestApi()
    {

        CoroutineScope(Dispatchers.IO).launch {

            val data = async {

                fetchData()

            }.await()

            if (data.isNotEmpty())
            {



                    updateAdviceText(data)

            }

        }

    }

    private fun fetchData():String{

        var response=""
        try {
            response =URL("https://api.adviceslip.com/advice").readText()

        }catch (e:Exception)
        {
            println("Error $e")

        }
        return response

    }

    private suspend fun updateAdviceText(data:String)
    {
        withContext(Dispatchers.Main)
        {

            val jsonObject = JSONObject(data)
            val slip = jsonObject.getJSONObject("slip")
            val id = slip.getInt("id")
            val advice = slip.getString("advice")


            tvAdvice.text = advice


        }

    }

}