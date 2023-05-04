package com.example.foodwizard.Fragments
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.foodwizard.R

class UpdateModal() : DialogFragment() {
    private lateinit var nameTextView: TextView
    private lateinit var inputView: EditText
    private lateinit var enterButton: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        val type = arguments?.getString("type");

        Log.d("in here", "The type is " + type);

        var label: String = getString(R.string.dietPlanLabel);

        var inputTypeToSet = InputType.TYPE_CLASS_NUMBER
        if(type == "password"){
            inputTypeToSet = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            label = getString(R.string.passwordLabel);
        }else if(type == "email"){
            inputTypeToSet = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            label = getString(R.string.emailLabel);
        }

        val view = inflater.inflate(R.layout.fragment_update_modal, container, false)

        nameTextView = view.findViewById(R.id.typeLabel)
        nameTextView.text = label;

        inputView = view.findViewById(R.id.input)
        inputView.inputType = inputTypeToSet

        enterButton = view.findViewById(R.id.updateButton)

        enterButton.setOnClickListener {
            Log.d("in here", "Clicked enter");
            val input = inputView.text.toString()

            if(type == "dietPlan"){
                Log.d("in here", "updating diet plan with " + input);
            }else if(type == "password"){
                Log.d("in here", "updating password with " + input);
            }else if(type == "email"){
                Log.d("in here", "updating email with " + input);
            }

        }



//        nameTextView = view.findViewById(R.id.name)
//        possibilityTextView = view.findViewById(R.id.possibility)
//
//        // Set text using values from Diet object
//        nameTextView.text = meal?.dietResponse?.category?.name
//        possibilityTextView.text = meal?.dietResponse?.category?.probability.toString()
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}