package com.fkocak.witranslate.ui.fragments.translate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.fkocak.witranslate.R

class TranslateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_translate, container, false)

        view?.let {
            init(it)
        }

        return view
    }

    private fun init(view: View){
        view.findViewById<Button>(R.id.btnGoHistory).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_translateFragment_to_historyFragment)
        }

        view.findViewById<Button>(R.id.btnGoFavorite).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_translateFragment_to_favoriteFragment2)
        }
    }
}