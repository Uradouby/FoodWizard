package com.example.foodwizard.Diet

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.foodwizard.databinding.ListItemMealBinding
import kotlinx.coroutines.*
import java.io.File
import java.util.*

// click the take photo -> take the photo of your diet
class CameraFragment: Fragment() {
    private lateinit var binding: ListItemMealBinding
    private var photoFile: File? = null
    private var photoUri: Uri? = null

    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { result: Boolean ->
        if (result) {
            photoUri?.let { uri ->
                // use the photo URI to access the photo file
                // upload it to the API for nutrition analysis.
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val imageBytes = inputStream?.readBytes()
                inputStream?.close()

                imageBytes?.let {
                    recognizeDiet(imageBytes)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListItemMealBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.upload.setOnClickListener {
            takePhoto()
        }
    }
    private fun takePhoto() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), 0)
            return
        }
        val photoName = "IMG_DIET_${Date()}.jpg"
        photoFile = File(
            requireContext().getExternalFilesDir(null),
            photoName
        )
        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            photoFile!!
        )
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        if (takePhotoIntent.resolveActivity(requireContext().packageManager) != null) {
            takePhoto.launch(photoUri)
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun recognizeDiet(imageBytes: ByteArray) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = DietRecognition().recognizeDiet(imageBytes)
            withContext(Dispatchers.Main) {
            }
        }
    }
}