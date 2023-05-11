package com.example.foodwizard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.example.foodwizard.DB.USER_TYPE
import com.example.foodwizard.DB.User
import com.example.foodwizard.Util.RecipeUtils
import com.example.foodwizard.databinding.ActivityLoginBinding
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val usersViewModel: UsersViewModel by viewModels()
    var userId: Int = 0 //user id will be used to get the user data from the database
    private lateinit var userType: USER_TYPE //user type will be used to check if the user is admin or not
    private val adminUser = User(
        "admin",
        ValidationManager.encryption("1234"),
        userType = USER_TYPE.ADMIN
    ) //userId will be 1
    private lateinit var sharedPreferences: SharedPreferences

    //private lateinit var database2: DatabaseReference
    private lateinit var auth: FirebaseAuth

    @SuppressLint("WrongThread")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storage = Firebase.storage
        val storageRef = storage.reference
        //demo for firebase
        val database = Firebase.database
        val myRef1 = database.getReference("TestKey1")
        myRef1.setValue("TestValue1")
        val myRef2 = database.getReference("TestKey2")
        myRef2.setValue("TestValueNow 04/30")
       /* var tmps= mutableListOf<String>("https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fgreen%20leafy.png?alt=media&token=c3c1e80c-a280-4086-ac42-8d32172ab17a",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fbeef.png?alt=media&token=69945226-f0bb-483a-b7f8-28efc2d91b36",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fbroccoli.png?alt=media&token=8aa77ee3-5fec-4d56-8c2c-6be5accd3117",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fbutter.png?alt=media&token=f96755fb-a293-4583-bb04-0ba0e2859a43",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fcereal.png?alt=media&token=df0931b7-17c9-4463-b4fe-becb105904ca",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fcheese.png?alt=media&token=96c79315-e9f7-4c2f-a5b9-11d4ccd64ac6",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fchicken.png?alt=media&token=1a7f1d43-35f1-4d78-ab7b-d809b9433640",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fchocolate.png?alt=media&token=ae86bd5a-ec62-45e7-ba46-ec9d3be52d26",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fegg.png?alt=media&token=e66e777a-0fc7-4932-aa57-de9fbda0f328",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Feggs.png?alt=media&token=1778ee7d-587b-4572-8104-dc4d27955b7c",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fsalmon.png?alt=media&token=513911fb-bad9-4cd5-a1d3-d2e09ffa58ab",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Froast%20fish.png?alt=media&token=b93088a0-0139-4a85-9b01-943fbb5f314d",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Ficecream.png?alt=media&token=229c54f4-3ece-4c93-87db-ab706341bdda",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fmilk.png?alt=media&token=6db66965-9a93-4d16-b826-2b61529b72b2",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fcashew.png?alt=media&token=7c4344fb-baf6-456f-958e-a3c720f4518e",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fnuts2.png?alt=media&token=28e241c2-c1ad-4b0e-8676-8ffabd81daa2",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fonion.png?alt=media&token=d9098a9a-3840-4dc0-b0c2-e6494fb9e411",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fpotato.png?alt=media&token=368728a4-cfdc-45b1-9f47-9a3c01bc4d8f",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fpretzel.png?alt=media&token=9ba61f08-7c52-41f0-8876-a24f90bd4623",
                                        "tohttps://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Ftomato.png?alt=media&token=a9e34b1a-0c3f-4f1e-9be9-f9241481fe69",
                                        "https://firebasestorage.googleapis.com/v0/b/foodwizard-92f05.appspot.com/o/images%2Fyogurt.png?alt=media&token=57569b1b-9da0-49e0-abbf-ea402add9d39")

        var tmp= mutableListOf<String>("green leafy","beef","broccoli","butter","cereal","cheese",
                                        "chicken","chocolate","egg","eggs","salmon","roast fish",
                                        "ice cream","milk","cashew","nuts","onion","potato","pretzel","tomato",
                                        "yogurt")*/
//        for (i in tmp.indices)
//        {
//            var database2 =
//                FirebaseDatabase.getInstance().getReference("Meal").child(i.toString())
//            val hashMap: HashMap<String,Any > = HashMap()
//            hashMap.put("name",tmp[i])
//            hashMap.put("calory",0)
//            hashMap.put("fat",0)
//            hashMap.put("carb",0)
//            hashMap.put("protein",0)
//            Log.d("1",storageRef.child("images/"+tmp[i]+".png").toString())
//            hashMap.put("imagepath",tmps[i])
//            database2.setValue(hashMap)
//            Log.d("updatemeal","success update meals!"+tmp[i])
//        }


//        var database2.child("TestKey1").get().addOnSuccessListener {
//            Log.i("firebase read", "Got value ${it.value}")
//        }.addOnFailureListener {
//            Log.e("firebase read", "Error getting data", it)
//        }

        // Image Upload/Download in Firebase Storage


        val drawableId: Int = resources.getIdentifier("dog", "drawable", packageName)
        // 加载 drawable 图像并将其转换为 InputStream
        val drawable: Drawable = resources.getDrawable(drawableId)
        val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()

        //Report error in red underline, but it is working!!!
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        // Get Firebase Storage reference

        // create StorageReference storage dir in Google Cloud
        val mountainsRef = storageRef.child("images/dog.jpg")
        val uploadTask = mountainsRef.putBytes(stream.toByteArray())
        uploadTask.addOnFailureListener {
            println("Upload Error")
        }.addOnSuccessListener { taskSnapshot ->
            println("Successfully Uploaded1!")
        }

        // download the image from cloud
        // create a StorageReference on target downloading dir
        val imageRef = storageRef.child("images/dog.jpg")

        val localFile = File.createTempFile("dog", "jpg")
        imageRef.getFile(localFile).addOnSuccessListener {
            // Uselocal File
            println("Successfully Downloaded")
        }.addOnFailureListener {
            println("Download error")
        }


        binding = ActivityLoginBinding.inflate(layoutInflater)
        /*binding.title.text = Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>L</font>" +
                    "<font color=${Color.parseColor("#06F23A")}>og In</font>"
        )*/
        binding.title.text = Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>"+binding.title.text[0]+"</font>" +
                    "<font color=${Color.parseColor("#06F23A")}>"+binding.title.text.substring(1)+"</font>"
        )

//        GlobalScope.launch(Dispatchers.IO) {
//            usersViewModel.addUserIfNotExist(adminUser) //add admin user automatically when the app is installed
//        }
        sharedPreferences = getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE)

        auth = FirebaseAuth.getInstance()

//        calculateLastLogin() //check if the user had already signed in or not

        binding.login.setOnClickListener {
//            val userName = binding.account.text.toString()

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
//            val user: FirebaseUser? = auth.currentUser
//            val userId: String = user!!.uid

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                MotionToast.darkColorToast(
                    this,
                    getString(R.string.try_again),
                    getString(R.string.signIn_error),
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM or MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.circular)
                )
            }else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){
                        if(it.isSuccessful) {
                            MotionToast.darkColorToast(
                                this,
                                getString(R.string.success),
                                getString(R.string.signIn_success),
                                MotionToastStyle.SUCCESS,
                                MotionToast.GRAVITY_BOTTOM or MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(this,R.font.circular)
                            )
                            val intent = Intent(this, Main::class.java)
                            startActivity(intent)
                            // Image Upload/Download in Firebase Storage
                            val storage = Firebase.storage
                            val storageRef = storage.reference

                            val drawableId: Int = resources.getIdentifier("dog", "drawable", packageName)
                            // 加载 drawable 图像并将其转换为 InputStream
                            val drawable: Drawable = resources.getDrawable(drawableId)
                            val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap
                            val stream = ByteArrayOutputStream()

                            //Report error in red underline, but it is working!!!
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

                            // Get Firebase Storage reference

                            // create StorageReference storage dir in Google Cloud
                            val mountainsRef = storageRef.child("images/dog.jpg")
                            val uploadTask = mountainsRef.putBytes(stream.toByteArray())
                            uploadTask.addOnFailureListener {
                                println("Upload Error")
                            }.addOnSuccessListener { taskSnapshot ->
                                println("Successfully Uploaded!")
                            }

                            // download the image from cloud
                            // create a StorageReference on target downloading dir
                            val imageRef = storageRef.child("images/dog.jpg")

                            val localFile = File.createTempFile("dog", "jpg")
                            imageRef.getFile(localFile).addOnSuccessListener {
                                // Uselocal File
                                println("Successfully Downloaded")
                            }.addOnFailureListener {
                                println("Download error")
                            }
                        }else{
                            MotionToast.darkColorToast(
                                this,
                                getString(R.string.try_again),
                                getString(R.string.signIn_error),
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM or MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(this,R.font.circular)
                            )
                        }
                    }
            }


        }



        binding.back.setOnClickListener {
            finish();
        }
//        binding.login.setOnClickListener {
//            val intent = Intent(this, Record::class.java)
//            startActivity(intent)
//        }
        setContentView(binding.root)
    }


}