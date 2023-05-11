package com.example.foodwizard.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwizard.Util.Constants.CHATGPT_KEY
import com.example.foodwizard.Util.Constants.CHATGPT_URL
import com.example.foodwizard.databinding.FragmentDialogBinding
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Dialog : DialogFragment() {

    private lateinit var binding: FragmentDialogBinding
    private val messages = mutableListOf<String>()
    private lateinit var adapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.messageReceived.layoutManager = layoutManager

        adapter = MessageAdapter(messages)
        binding.messageReceived.adapter = adapter

        binding.sendIcon.setOnClickListener {
            val message = binding.etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                // add user message to the list
                messages.add(message)
                adapter.notifyItemInserted(messages.size - 1)
                binding.etMessage.text?.clear()

                // get system response and add to the list
                GlobalScope.launch(Dispatchers.Main) {
                    val responseMessage = chatWithGPT(message)
                    messages.add(responseMessage)
                    adapter.notifyItemInserted(messages.size - 1)

                    // scroll to the bottom of the list
                    binding.messageReceived.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }

        return binding.root
    }

    private val client = OkHttpClient()

    private suspend fun chatWithGPT(message: String): String {
        val apiKey = CHATGPT_KEY
        val url = CHATGPT_URL

        val requestBody = JSONObject()
            .put("model", "gpt-3.5-turbo")
            .put("messages", JSONArray().put(JSONObject().put("role", "user").put("content", message)))
            .toString()
            .toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
        val responseBody = response.body?.string() ?: ""
        println(requestBody)
        val json = JSONObject(responseBody)
        val messageObj = json.getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
        return messageObj.getString("content")
    }


}


