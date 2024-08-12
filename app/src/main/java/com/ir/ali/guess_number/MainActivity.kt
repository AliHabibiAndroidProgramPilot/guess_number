package com.ir.ali.guess_number

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import com.ir.ali.guess_number.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestFocus()
        binding.guessCheck.shrink()
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
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                if(binding.guess1.text!!.isNotBlank() && binding.guess2.text!!.isNotBlank() && binding.guess3.text!!.isNotBlank()) {
                    if(
                        binding.guess1.text.toString().toIntOrNull() == binding.guess2.text.toString().toInt() ||
                        binding.guess2.text.toString().toIntOrNull() == binding.guess3.text.toString().toInt() ||
                        binding.guess3.text.toString().toIntOrNull() == binding.guess1.text.toString().toInt()
                    ) {
                        binding.sameGuess.visibility = View.VISIBLE
                    } else binding.sameGuess.visibility = View.INVISIBLE
                }
                toExtendFab()
            }
        }
        binding.guess1.addTextChangedListener(textWatcher)
        binding.guess2.addTextChangedListener(textWatcher)
        binding.guess3.addTextChangedListener(textWatcher)
    }
    private fun requestFocus() {
        val editText: View = binding.guess1
        editText.requestFocus()
        val makeFocus: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        makeFocus.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
    private fun toExtendFab() {
        if (
            binding.guess1.text!!.isNotBlank() &&
            binding.guess2.text!!.isNotBlank() &&
            binding.guess3.text!!.isNotBlank()
        ) {
            binding.guessCheck.extend()
        }
    }
}