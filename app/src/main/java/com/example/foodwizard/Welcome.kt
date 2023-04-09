package com.example.foodwizard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.example.foodwizard.databinding.ActivityWelcomeBinding
import java.util.zip.Inflater

class Welcome : AppCompatActivity() {
    private lateinit var binding:ActivityWelcomeBinding
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWelcomeBinding.inflate(layoutInflater)
        binding.title.text= Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>F</font>" +
                "<font color=${Color.parseColor("#06F23A")}>ood  </font>"+
                    "<font color=${Color.parseColor("#AEFC08")}>W</font>" +
                "<font color=${Color.parseColor("#06F23A")}>izard</font>")

        setContentView(binding.root)

        binding.Register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        binding.login.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}

