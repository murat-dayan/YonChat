package com.example.yonchat.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yonchat.R
import com.example.yonchat.databinding.FragmentLoginBinding
import com.example.yonchat.presentation.viewmodel.LoginViewModel
import com.example.yonchat.utils.LoginState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private  val binding get() = _binding!!

    private val loginViewModel by  viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        val usermail = binding.fragmentLoginEmailLayoutId.editText?.text.toString()
        val password = binding.fragmentLoginPasswordLayoutId.editText?.text.toString()

        loginViewModel.loginState.observe(viewLifecycleOwner){state->
            when (state){
                is LoginState.Loading->{
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_SHORT).show()
                }
                is LoginState.Success->{
                    findNavController().navigate(R.id.switch_login_fragment_to_home_fragment)
                }
                is LoginState.Error->{
                    val errorMessage = state.message ?: "Giriş yapılamadı."
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()

                }
            }

        }

        binding.fragmentLoginSaveButtonId.setOnClickListener {
            val usermail = binding.fragmentLoginEmailLayoutId.editText?.text.toString()
            val password = binding.fragmentLoginPasswordLayoutId.editText?.text.toString()

            if (usermail.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.loginWithFirebase(usermail,password)
            } else {
                // Kullanıcıya bilgi girişini tamamlaması gerektiğini belirtin.
                Toast.makeText(requireContext(), "Lütfen tüm bilgileri doldurun", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}