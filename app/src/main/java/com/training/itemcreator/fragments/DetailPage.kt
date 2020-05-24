package com.training.itemcreator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.training.itemcreator.R

class DetailPage : Fragment() {

    val args: DetailPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_page, container, false)

        view.findViewById<TextView>(R.id.id_text).text = args.itemId.toString()

        return view
    }
}
