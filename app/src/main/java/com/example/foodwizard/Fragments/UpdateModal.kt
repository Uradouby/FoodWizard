package com.example.foodwizard.Fragments
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.foodwizard.DB.Repository
import com.example.foodwizard.R
import com.example.foodwizard.login
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UpdateModal() : DialogFragment() {
    private lateinit var nameTextView: TextView
    private lateinit var inputView: EditText
    private lateinit var enterButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        val type = arguments?.getString("type");
        auth = FirebaseAuth.getInstance()

        Log.d("in here", "The type is " + type);
        // looked at the firebase docs here https://firebase.google.com/docs/auth/android/start
        val user = Firebase.auth.currentUser

        var label: String = getString(R.string.dietPlanLabel);

        var inputTypeToSet = InputType.TYPE_CLASS_NUMBER
        var inputHint = ""
        var icon = 0
        if(type == "password"){
            inputTypeToSet = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            inputHint = getString(R.string.title_activity_register_password);
            label = getString(R.string.passwordLabel);
            // looked at https://stackoverflow.com/questions/61487006/how-can-i-get-drawable-in-kotlin to see how you get a drawable in a fragment
            icon =  R.drawable.lock
        }else if(type == "email"){
            inputHint = getString(R.string.title_activity_register_email);
            inputTypeToSet = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            label = getString(R.string.emailLabel);
            icon =  R.drawable.account
            Log.d("icon", icon.toString());
        }

        val view = inflater.inflate(R.layout.fragment_update_modal, container, false)

        nameTextView = view.findViewById(R.id.typeLabel)
        nameTextView.text = label;

        inputView = view.findViewById(R.id.input)
        inputView.inputType = inputTypeToSet
        inputView.hint = inputHint
        // looked at https://stackoverflow.com/questions/22297073/how-to-programmatically-set-drawableright-on-android-edittext
        inputView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
//        inputView.setlistener

        enterButton = view.findViewById(R.id.updateButton)

        enterButton.setOnClickListener {
            Log.d("in here", "Clicked enter");
            val input = inputView.text.toString()

            if(input.isNullOrEmpty()){
                Toast.makeText(
                    activity,
                    "Input is null or empty",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                if (type == "password") {
                    if(input.length < 6){
                        Toast.makeText(
                            activity,
                            "Change password failed, password length must be at least 6",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        user?.updatePassword(input)?.addOnCompleteListener(requireActivity()) {
                            if (it.isSuccessful) {
                                Log.d("In here", "It was sucessful")
                                Toast.makeText(
                                    activity,
                                    "Successfully changed password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Log.d("In here", "It failed")
                                Toast.makeText(
                                    activity,
                                    "Change password failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    Log.d("in here", "updating password with " + input);
                } else if (type == "email") {
                    // got from https://stackoverflow.com/questions/72117435/kotlin-android-email-validation
                    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
                    if(!input.isValidEmail()){
                        Toast.makeText(
                            activity,
                            "Change email failed, not a valid email address",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Log.d("in here", "updating email with " + input);
                        user?.updateEmail(input)?.addOnCompleteListener(requireActivity()) {
                            if (it.isSuccessful) {
                                Log.d("In here", "It was sucessful")
                                Toast.makeText(
                                    activity,
                                    "Successfully changed email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Log.d("In here", "It failed")
                                Toast.makeText(
                                    activity,
                                    "Change email failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}