package com.ir.ali.guess_number

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.ir.ali.guess_number.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.oneToFive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val anim = AnimationUtils.loadAnimation(this, R.anim.aplha)
                anim.fillAfter = true
                anim.duration = 550
                binding.rangeText.startAnimation(anim)
                binding.rangeText.text = "Range is 1..5"
            }
        }
        binding.oneToTen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val anim = AnimationUtils.loadAnimation(this, R.anim.aplha)
                anim.fillAfter = true
                anim.duration = 550
                binding.rangeText.startAnimation(anim)
                binding.rangeText.text = "Range is 1..10"
            }
        }
        binding.oneToTwenty.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val anim = AnimationUtils.loadAnimation(this, R.anim.aplha)
                anim.fillAfter = true
                anim.duration = 550
                binding.rangeText.startAnimation(anim)
                binding.rangeText.text = "Range is 1..20"
            }
        }
    }
}