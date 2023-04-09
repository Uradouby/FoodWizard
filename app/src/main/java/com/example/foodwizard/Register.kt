package com.example.foodwizard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.example.foodwizard.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        binding.title.text= Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>R</font>" +
                    "<font color=${Color.parseColor("#06F23A")}>egister</font>")
        binding.back.setOnClickListener {
            finish();
        }
        binding.register.setOnClickListener {
            finish();
        }
        setContentView(binding.root)

    }
}