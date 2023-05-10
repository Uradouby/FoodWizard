package com.example.foodwizard.Fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.foodwizard.databinding.FragmentPersonalInfoBinding
import com.example.foodwizard.login
import com.google.firebase.auth.FirebaseAuth


//firebase authentication modify
// import { getAuth, updatePassword } from "firebase/auth";




class PersonalInfo : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("Personal INFO", "We in here");
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)

        var tmp:String=""
        var tokens=binding.title.text.split("\n")
        for (i in tokens.indices)
        {
            tmp+="<font color=${Color.parseColor("#45C561")}>"+tokens[i][0]+"</font>" +
                    "<font color=${Color.parseColor("#0E6D68")}>"+tokens[i].substring(1)+"\n</font>"
        }
        binding.title.text = Html.fromHtml(tmp)

        binding.changeDietPlanButton.setOnClickListener {
            Log.d(tag, "Clicked the change Diet Plan")
            val fragmentManager = getParentFragmentManager()
            val newFragment = updatePlan()
            fragmentManager?.let {
                if (newFragment != null) {
                    newFragment.show(it, "updateplan")
                }
            }
        }

            binding.changePassword.setOnClickListener {
                Log.d(tag, "Clicked the change password")
                //////////////////////////
                // Firebase change psw  //
//            const auth = getAuth();
//            const user = auth.currentUser;
//
//            // Where is the new password parameter? I just made a random one
//            const newPassword = getASecureRandomPassword();
//            updatePassword(user, newPassword).then(() => {
//                // Update successful.
//            }).catch((error) => {
//            // An error ocurred
//            // ...
//            });
                //////////////////////////
                val fragmentManager = getParentFragmentManager()
                val newFragment = UpdateModal().apply {
                    arguments = Bundle().apply {
                        putString("type", "password")
                    }
                }
                newFragment.show(fragmentManager, "password")
            }

            binding.changeEmail.setOnClickListener {
                Log.d(tag, "Clicked the change email")
                //////////////////////////
                // Firebase change mail //
//            const auth = getAuth();
//            updateEmail(auth.currentUser, "user@example.com").then(() => {
//                // Email updated!
//                // ...
//            }).catch((error) => {
//            // An error occurred
//            // ...
//        });
                //////////////////////////


                val fragmentManager = getParentFragmentManager()
                val newFragment = UpdateModal().apply {
                    arguments = Bundle().apply {
                        putString("type", "email")
                    }
                }
                newFragment.show(fragmentManager, "email")
            }

            binding.logoutButton.setOnClickListener {
                setFragmentResult("log-out", bundleOf("bundleKey2" to ""))
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(activity, login::class.java)
                startActivity(intent)
            }

            return binding.root
        }


}