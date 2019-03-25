package com.example.aacstudy

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class MainFragment:Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel2 = ViewModelProviders.of(this).get(MainViewMoodel::class.java)
        val viewModel3 = ViewModelProviders.of(requireActivity()).get(MainViewMoodel::class.java)
        println("${viewModel2} ${viewModel3}")
    }
}