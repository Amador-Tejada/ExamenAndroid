package com.example.examen.Fragmentos.navigationComponentObject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.examen.R
import com.example.examen.databinding.FragmentFinalBinding

class FinalFragment : Fragment() {

    private lateinit var binding: FragmentFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = view.findNavController()

        binding.btVolver.setOnClickListener {
            navController.navigate(R.id.action_finalFragment_to_inicioComponentFragment)
        }

    }
}