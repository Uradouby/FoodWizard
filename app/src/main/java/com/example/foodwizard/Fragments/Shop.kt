package com.example.foodwizard.Fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodwizard.R
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentRecordBinding
import com.example.foodwizard.databinding.FragmentShopBinding

class Shop : Fragment() {
    private var _binding: FragmentShopBinding? = null

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
        _binding = FragmentShopBinding.inflate(inflater, container, false)
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