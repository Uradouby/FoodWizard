package com.example.foodwizard.Fragments
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.databinding.FragmentUpdatePlanBinding
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
        val usersViewModel: UsersViewModel by activityViewModels()
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        val userId: String = user!!.uid
        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(userId)

        _binding = FragmentUpdatePlanBinding.inflate(inflater, container, false)
        binding.apply{
            caloryInput.text=Editable.Factory.getInstance().newEditable(usersViewModel.plancalory.toString())
            fatInput.text=Editable.Factory.getInstance().newEditable(usersViewModel.planfat.toString())
            proteinInput.text=Editable.Factory.getInstance().newEditable(usersViewModel.planprotein.toString())
            carbInput.text=Editable.Factory.getInstance().newEditable(usersViewModel.plancarb.toString())

            updateButton.setOnClickListener(){
                var calory= caloryInput.text.toString().toInt()
                var fat = fatInput.text.toString().toInt()
                var protein = proteinInput.text.toString().toInt()
                var carb = carbInput.text.toString().toInt()
                usersViewModel.plancalory=calory
                usersViewModel.plancarb=carb
                usersViewModel.planfat=fat
                usersViewModel.planprotein=protein
                val hashMap: HashMap<String,Any > = HashMap()

                hashMap.put("calory",calory)
                hashMap.put("fat",fat)
                hashMap.put("carb",carb)
                hashMap.put("protein",protein)
                databaseReference.setValue(hashMap)
                val usersViewModel: UsersViewModel by activityViewModels()
                usersViewModel.updateSign()
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