package com.example.foodwizard.Fragments
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.R

class detail_meal() : DialogFragment() {
    private lateinit var nameTextView: TextView
    private lateinit var possibilityTextView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        val meal = arguments?.getSerializable("meal") as Diet?
        val view = inflater.inflate(R.layout.detail_item_meal, container, false)
        nameTextView = view.findViewById(R.id.name)
        possibilityTextView = view.findViewById(R.id.possibility)

        // Set text using values from Diet object
        nameTextView.text = meal?.dietResponse?.category?.name
        possibilityTextView.text = meal?.dietResponse?.category?.probability.toString()
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}