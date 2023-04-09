package com.example.foodwizard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.example.foodwizard.databinding.ActivityLoginBinding
import java.util.zip.Inflater

class login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        binding.title.text= Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>L</font>" +
                    "<font color=${Color.parseColor("#06F23A")}>og In</font>")
        binding.back.setOnClickListener {
            finish();
        }
        binding.login.setOnClickListener {
            val intent = Intent(this, Record::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }
}