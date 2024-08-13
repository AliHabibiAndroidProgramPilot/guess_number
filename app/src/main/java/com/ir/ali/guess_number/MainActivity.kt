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
    //Local variable as a Random number
    private var randomNumber: Int = 0
    //Local List to take user inputs
    private lateinit var userGuesses: List<Int>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Make a top edit text in Focus mode
        requestFocus()
        binding.guessCheck.shrink()
        /* Radio buttons Animations
        Each Radio button contains a Click listener to manage how animations can apply on them
        Used anim directory and alpha resource file to apply alpha(opacity) animation
        */
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
        /** object textWatcher
         * textWatcher object passed to each edit text AfterTextChanged listener
         *@receiver object(TextWatcher)
         *@propertyName textWatcher
         *
         * @shows a message warning for same guesses
         */
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
    /** requestFocus
     * Make Focus on first edit text.
     *
     *@author Ali
     */
    private fun requestFocus() {
        val editText: View = binding.guess1
        editText.requestFocus()
        val makeFocus: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        makeFocus.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
    //Makes fab to extend when all three input sections have value
    private fun toExtendFab() {
        if (
            binding.guess1.text!!.isNotBlank() &&
            binding.guess2.text!!.isNotBlank() &&
            binding.guess3.text!!.isNotBlank()
        ) {
            binding.guessCheck.extend()
        }
    }
    /** randomNumberGenerator
     * Generates a Random number
     *
     *@author Ali
     * @return Random Integer.
     */
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
        // Put users inputs in list
        userGuesses = listOf(
            binding.guess1.text.toString().toInt(),
            binding.guess2.text.toString().toInt(),
            binding.guess3.text.toString().toInt()
        )
        //Check what range is selected
        if (binding.oneToFive.isChecked)
            randomNumber = randomNumberGenerator(1)
        if (binding.oneToTen.isChecked)
            randomNumber = randomNumberGenerator(2)
        if (binding.oneToTwenty.isChecked)
            randomNumber = randomNumberGenerator(3)
        //Manage to show Dialog based on user inputs and random number
        if (randomNumber in userGuesses)
            successBottomSheetDialog()
        else
            unsuccessfulBottomSheetDialog()
    }

    //Implement Unsuccessful Bottom Sheet Dialog
    @SuppressLint("SetTextI18n")
    private fun unsuccessfulBottomSheetDialog() {
        val sheetDialog = BottomSheetDialog(this)
        val bottomSheetDialogBinding = UnsuccessfulBottomsheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialogBinding.showRandomNumber.text = "Random number was: $randomNumber"
        bottomSheetDialogBinding.userNumbers.text =
            userGuesses.joinToString(separator = ", ", prefix = "Your guess was: ", limit = 4)
        sheetDialog.setContentView(bottomSheetDialogBinding.root)
        sheetDialog.show()
        //Clear inputs if Bottom Sheet Dialog dismissed and request Focus for first edit text
        sheetDialog.setOnDismissListener {
            binding.guess1.text?.clear()
            binding.guess2.text?.clear()
            binding.guess3.text?.clear()
            requestFocus()
        }
    }
    //Implement Successful Bottom Sheet Dialog
    @SuppressLint("SetTextI18n")
    private fun successBottomSheetDialog() {
        val sheetDialog = BottomSheetDialog(this)
        val bottomSheetDialogBinding = SuccessfulBottomsheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialogBinding.showRandomNumber.text = "Random number was: $randomNumber"
        sheetDialog.setContentView(bottomSheetDialogBinding.root)
        sheetDialog.show()
        //Clear inputs if Bottom Sheet Dialog dismissed and request Focus for first edit text
        sheetDialog.setOnDismissListener {
            binding.guess1.text?.clear()
            binding.guess2.text?.clear()
            binding.guess3.text?.clear()
            requestFocus()
        }
    }
}