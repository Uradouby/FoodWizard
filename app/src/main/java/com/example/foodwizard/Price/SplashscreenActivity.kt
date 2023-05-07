package com.example.foodwizard.Price

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodwizard.Main
import com.example.foodwizard.Welcome
import com.example.foodwizard.databinding.ActivitySplashscreenBinding


class SplashscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageViewLogo.alpha = 0f
        binding.imageViewLogo.animate().setDuration(3000).alpha(1f).withEndAction {
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}