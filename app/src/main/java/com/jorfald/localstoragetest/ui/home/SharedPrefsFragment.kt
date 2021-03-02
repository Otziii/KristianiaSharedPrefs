package com.jorfald.localstoragetest.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jorfald.localstoragetest.R
import com.jorfald.localstoragetest.ui.SHARED_PREFS_COUNTER_KEY
import com.jorfald.localstoragetest.ui.SHARED_PREFS_NAME
import com.jorfald.localstoragetest.ui.SHARED_PREFS_TEXT_KEY

class SharedPrefsFragment : Fragment() {

    private lateinit var sharedPrefsViewModel: SharedPrefsViewModel

    private lateinit var savedEditText: EditText
    private lateinit var savedTextView: TextView
    private lateinit var okButton: Button
    private lateinit var counterButton: Button
    private lateinit var counterTextView: TextView
    private lateinit var saveButton: Button

    private var counter = 0
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPrefsViewModel =
            ViewModelProvider(this).get(SharedPrefsViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_shared_prefs, container, false)

        savedEditText = view.findViewById(R.id.saved_edit_text)
        savedTextView = view.findViewById(R.id.saved_text_view)
        okButton = view.findViewById(R.id.ok_button)
        counterButton = view.findViewById(R.id.counter_button)
        counterTextView = view.findViewById(R.id.counter_text_view)
        setCounterText()

        saveButton = view.findViewById(R.id.save_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

        bindButtons()
        fillFromSharedPreferences()
    }

    private fun fillFromSharedPreferences() {
        savedTextView.text = sharedPreferences.getString(SHARED_PREFS_TEXT_KEY, null)
        counter = sharedPreferences.getInt(SHARED_PREFS_COUNTER_KEY, counter)

        setCounterText()
    }

    private fun bindButtons() {
        okButton.setOnClickListener {
            savedTextView.text = savedEditText.text
        }

        counterButton.setOnClickListener {
            counter++
            setCounterText()
        }

        saveButton.setOnClickListener {
            val editor = sharedPreferences.edit()

            editor.putString(SHARED_PREFS_TEXT_KEY, savedTextView.text.toString())
            editor.putInt(SHARED_PREFS_COUNTER_KEY, counter)

            editor.apply()
        }
    }

    private fun setCounterText() {
        counterTextView.text = "Counter: $counter"
    }
}