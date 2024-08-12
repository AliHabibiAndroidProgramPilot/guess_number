package com.ir.ali.guess_number

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ir.ali.guess_number.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.oneToFive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.rangeText.text = "Range is 1..5"
        }
        binding.oneToTen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.rangeText.text = "Range is 1..10"
        }
        binding.oneToTwenty.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.rangeText.text = "Range is 1..20"
        }
    }

    private fun matchTextWithRadioGroup() {
        binding.rangeRadioGroup.setOnCheckedChangeListener { radioGroup, _ ->
            when (radioGroup.id) {
                R.id.one_to_five -> {
                    binding.rangeText.text = "Range is 1..5"
                }
                R.id.one_to_ten -> {
                    binding.rangeText.text = "Range is 1..10"
                }
                R.id.one_to_twenty -> {
                    binding.rangeText.text = "Range is 1..20"
                }
            }
        }
    }
}