package com.ir.ali.guess_number

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ir.ali.guess_number.databinding.ActivityMainBinding
import com.ir.ali.guess_number.databinding.SuccessfulBottomsheetDialogBinding
import com.ir.ali.guess_number.databinding.UnsuccessfulBottomsheetDialogBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var randomNumber: Int = 0
//    private var guessArray: IntArray = intArrayOf()
    private lateinit var userGuesses: List<Int>
    @SuppressLint("SetTextI18n")
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
                if (binding.guess1.text!!.isNotBlank() && binding.guess2.text!!.isNotBlank() && binding.guess3.text!!.isNotBlank()) {
                    if (
                        binding.guess1.text.toString()
                            .toIntOrNull() == binding.guess2.text.toString().toInt() ||
                        binding.guess2.text.toString()
                            .toIntOrNull() == binding.guess3.text.toString().toInt() ||
                        binding.guess3.text.toString()
                            .toIntOrNull() == binding.guess1.text.toString().toInt()
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
        binding.guessCheck.setOnClickListener {
            kernel()
        }
    }

    private fun requestFocus() {
        val editText: View = binding.guess1
        editText.requestFocus()
        val makeFocus: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
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

    private fun randomNumberGenerator(range: Int): Int {
        var randomNumber: Int = 0
        when (range) {
            1 -> {
                randomNumber = (1..5).random()
            }
            2 -> {
                randomNumber = (1..10).random()
            }
            3 -> {
                randomNumber = (1..20).random()
            }
            else -> {}
        }
        return randomNumber
    }

    private fun kernel() {
        // Put users inputs in array
        userGuesses = listOf(
            binding.guess1.text.toString().toInt(),
            binding.guess2.text.toString().toInt(),
            binding.guess3.text.toString().toInt()
        )
        if (binding.oneToFive.isChecked)
            randomNumber = randomNumberGenerator(1)
        if (binding.oneToTen.isChecked)
            randomNumber = randomNumberGenerator(2)
        if (binding.oneToTwenty.isChecked)
            randomNumber = randomNumberGenerator(3)
        if (randomNumber in userGuesses) successBottomSheetDialog()
        else unsuccessfulBottomSheetDialog()
    }
    @SuppressLint("SetTextI18n")
    private fun unsuccessfulBottomSheetDialog() {
        val sheetDialog = BottomSheetDialog(this)
        val bottomSheetDialogBinding = UnsuccessfulBottomsheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialogBinding.showRandomNumber.text = "Random number was: $randomNumber"
        bottomSheetDialogBinding.userNumbers.text = userGuesses.joinToString(separator = ", ", prefix = "Your guess was: ", limit = 4)
        sheetDialog.setContentView(bottomSheetDialogBinding.root)
        sheetDialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun successBottomSheetDialog() {
        val sheetDialog = BottomSheetDialog(this)
        val bottomSheetDialogBinding = SuccessfulBottomsheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialogBinding.showRandomNumber.text = "Random number was: $randomNumber"
        sheetDialog.setContentView(bottomSheetDialogBinding.root)
        sheetDialog.show()
    }
}