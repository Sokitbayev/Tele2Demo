package com.example.tele2demo.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tele2demo.R
import com.example.tele2demo.databinding.FragmentLoginPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginPageFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginPageEnterBtn.setOnClickListener {
            viewModel.onBtnClick("admin", "betaWolves#1")

            /*viewModel.onBtnClick(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )*/
        }
        viewModel.viewState.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}