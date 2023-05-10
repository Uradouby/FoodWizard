package com.example.foodwizard.Fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.R
import com.example.foodwizard.databinding.FragmentRecipeBinding
import com.example.foodwizard.databinding.FragmentRecordBinding

class Recipe : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
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
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        var tmp:String=""
        var tokens=binding.title.text.split("\n")
        for (i in tokens.indices)
        {
            tmp+="<font color=${Color.parseColor("#45C561")}>"+tokens[i][0]+"</font>" +
                    "<font color=${Color.parseColor("#0E6D68")}>"+tokens[i].substring(1)+"\n</font>"
        }
        binding.title.text = Html.fromHtml(tmp)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}