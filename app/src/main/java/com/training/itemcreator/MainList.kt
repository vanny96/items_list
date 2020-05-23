package com.training.itemcreator

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.adapters.MainAdapter

class MainList : Fragment() {

    var adapter : MainAdapter? = null
    var recyclerView: RecyclerView? = null
    var inputText : TextInputEditText? = null
    var button : Button? = null

    private val onClickItem = { v: View, position: Int -> Toast.makeText(
            v.context,
            String.format(getString(R.string.row_text), adapter?.getItem(position)),
            Toast.LENGTH_SHORT).show()
    }

    private val onAddItem = { _: View ->
        adapter?.addItem(inputText?.text.toString())
        recyclerView?.smoothScrollToPosition((adapter?.itemCount ?: 1) - 1)
        inputText?.text?.clear()

        val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager

        inputManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = context?.let { MainAdapter(it, mutableListOf("Figo", "Bello"), onClickItem) }
        recyclerView = view?.findViewById<RecyclerView>(R.id.recycler)?.apply {
            adapter = this@MainList.adapter
            layoutManager = LinearLayoutManager(context)
        }

        inputText = view?.findViewById(R.id.input_text)
        inputText?.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) onAddItem(v) else false;
        }

        button = view?.findViewById(R.id.button2)
        button?.setOnClickListener(View.OnClickListener {  })
        button?.setOnClickListener{
            onAddItem(it)
        }
    }
}
