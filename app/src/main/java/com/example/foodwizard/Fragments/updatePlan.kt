package com.example.foodwizard.Fragments
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.R
import com.example.foodwizard.databinding.DetailItemMealBinding
import com.example.foodwizard.databinding.FragmentListMealBinding
import com.example.foodwizard.databinding.FragmentListShopBinding
import com.example.foodwizard.databinding.FragmentUpdatePlanBinding
import com.example.foodwizard.viewModel.RecipeViewModel
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class updatePlan() : DialogFragment() {
    private var _binding: FragmentUpdatePlanBinding? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        val meal = arguments?.getSerializable("meal") as Diet?

        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        val userId: String = user!!.uid
        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(userId)

        _binding = FragmentUpdatePlanBinding.inflate(inflater, container, false)
        binding.apply{

            databaseReference.child("calory").get().addOnSuccessListener {
                Log.i("firebase read", "Got value ${it.value}")
                caloryInput.text= Editable.Factory.getInstance().newEditable(it.value.toString())
            }.addOnFailureListener {
                Log.e("firebase read", "Error getting data", it)
            }

            databaseReference.child("fat").get().addOnSuccessListener {
                Log.i("firebase read", "Got value ${it.value}")
                fatInput.text= Editable.Factory.getInstance().newEditable(it.value.toString())
            }.addOnFailureListener {
                Log.e("firebase read", "Error getting data", it)
            }

            databaseReference.child("protein").get().addOnSuccessListener {
                Log.i("firebase read", "Got value ${it.value}")
                proteinInput.text= Editable.Factory.getInstance().newEditable(it.value.toString())
            }.addOnFailureListener {
                Log.e("firebase read", "Error getting data", it)
            }

            databaseReference.child("carb").get().addOnSuccessListener {
                Log.i("firebase read", "Got value ${it.value}")
                carbInput.text= Editable.Factory.getInstance().newEditable(it.value.toString())
            }.addOnFailureListener {
                Log.e("firebase read", "Error getting data", it)
            }

            updateButton.setOnClickListener(){
                var calory:Double = caloryInput.text.toString().toDouble()
                var fat:Double = fatInput.text.toString().toDouble()
                var protein:Double = proteinInput.text.toString().toDouble()
                var carb:Double = carbInput.text.toString().toDouble()

                val hashMap: HashMap<String,Any > = HashMap()

                hashMap.put("calory",calory)
                hashMap.put("fat",fat)
                hashMap.put("carb",carb)
                hashMap.put("protein",protein)
                databaseReference.setValue(hashMap)
                val recipeViewModel: RecipeViewModel by viewModels()
                val usersViewModel: UsersViewModel by activityViewModels()
                recipeViewModel.initialize(usersViewModel)
                Log.e("init", "init ok")
                recipeViewModel.update()
                Log.e("update", "update ok")
                dismiss()
            }
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}