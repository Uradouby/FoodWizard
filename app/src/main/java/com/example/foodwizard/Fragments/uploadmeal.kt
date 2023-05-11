package com.example.foodwizard.Fragments

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.*
import com.example.foodwizard.Diet.DietRecognition
import com.example.foodwizard.Util.PictureUtils.Companion.getScaledBitmap
import com.example.foodwizard.databinding.FragmentUploadmealBinding
import com.example.foodwizard.viewModel.RecipeViewModel
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.asTask
import java.io.File
import java.util.*


class uploadmeal : DialogFragment() {
    private var _binding: FragmentUploadmealBinding? = null
    private val recipeViewModel: RecipeViewModel by viewModels()
    private var photoName:String? = null
    private var imageURL:String? = null

    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePhoto: Boolean ->
        // Handle the result
        if (didTakePhoto && photoName != null) {
            Log.d("photo", photoName!!)
            updatePhoto(photoName)
            binding.upload.text="take new picture"
            binding.save.visibility=View.VISIBLE
        }
    }


    private fun uploadImageToDB(photoFileName: String?): Task<Uri> {
        val photoFile = photoFileName?.let {
            File(requireContext().applicationContext.filesDir, it)
        }
        // Image Upload/Download in Firebase Storage
        val storage = Firebase.storage
        val storageRef = storage.reference
        val file = Uri.fromFile(photoFile)
        val imagesRef = storageRef.child("images")
        val imageRef = imagesRef.child(photoFileName.toString())
        val uploadTask = imageRef.putFile(file)
        return uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            // Get the download URL from the task snapshot
            task.result?.metadata?.reference?.downloadUrl
        }
    }

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
        _binding = FragmentUploadmealBinding.inflate(inflater, container, false)
        binding.upload.setOnClickListener()
        {
            photoName = "IMG_${Date()}.JPG"
            val photoFile = File(requireContext().applicationContext.filesDir,
                photoName)
            val photoUri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.foodwizard.fileprovider",
                photoFile
            )

            takePhoto.launch(photoUri)
        }

        binding.save.setOnClickListener()
        {
            uploadImageToDB(photoName).addOnSuccessListener { uri ->
                imageURL = uri.toString()
                //Call remote API to recognizeDiet Food Image
                imageURL?.let { recognizeDiet(it) }?.addOnCompleteListener { task ->
                    // This block will execute when recognizeDiet has completed its execution
                    binding.save.visibility=View.GONE
                    val result="result"
                    setFragmentResult("requestKey1", bundleOf("bundleKey1" to result))
                    dismiss()
                }
                val recipeViewModel: RecipeViewModel by viewModels()
                val usersViewModel: UsersViewModel by activityViewModels()
                recipeViewModel.initialize(usersViewModel)
                recipeViewModel.update()
            }.addOnFailureListener { exception ->
                // Handle failed upload
                Log.e("uploadMeal", "Upload Meal Image Error")
            }
        }
        /*
        binding.apply{
            val captureImageIntent = takePhoto.contract.createIntent(
                requireContext(),
                null
            )
            upload.isEnabled = canResolveIntent(captureImageIntent)
        }
        */
        return binding.root

    }
    private fun canResolveIntent(intent: Intent): Boolean {
        intent.addCategory(Intent.CATEGORY_HOME)
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }

    private fun updatePhoto(photoFileName: String?) {
        if (binding.food.tag!=photoFileName) {
            val photoFile = photoFileName?.let {
                File(requireContext().applicationContext.filesDir, it)
            }
            if (photoFile?.exists() == true) {
                binding.food.doOnLayout { measuredView ->
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path,
                        measuredView.width,
                        measuredView.height
                    )
                    binding.food.setImageBitmap(scaledBitmap)
                    binding.food.tag = photoFileName
                }
            } else {
                Log.d("da","none exist")
                binding.food.setImageBitmap(null)
                binding.food.tag = null
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun recognizeDiet(photoURL : String): Task<Unit> {
        val deferred = CompletableDeferred<Unit>()
        GlobalScope.launch(Dispatchers.IO) {
            val usersViewModel: UsersViewModel by viewModels()
            var description = binding.editTextTextMultiLine.text.toString()
            DietRecognition(usersViewModel).recognizeDiet(photoURL, description)
            withContext(Dispatchers.Main) {
                deferred.complete(Unit)
            }
        }
        return deferred.asTask()
    }
}