package com.example.yonchat.presentation.fragments

import android.app.AlertDialog
import android.content.Context
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
import com.example.yonchat.domain.model.User
import com.example.yonchat.presentation.viewmodel.LoginViewModel
import com.example.yonchat.utils.SignState
import com.example.yonchat.utils.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel by viewModels<LoginViewModel>()

    private lateinit var user:User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)






        loginViewModel.signState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SignState.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }

                is SignState.Success -> {
                    findNavController().navigate(R.id.switch_login_fragment_to_home_fragment)
                }

                is SignState.Error -> {
                    val errorMessage = state.message ?: "Giriş yapılamadı."
                    println(state.errorCode)
                    if (state.errorCode == "ERROR_INVALID_CREDENTIAL") {
                        showAlertDialog(
                            context = requireActivity(),
                            title = "Kayıt Dışı Mail",
                            message = "Kayıt olmak ister Misiniz?",
                            positiveButtonText = "Kayıt Ol",
                            negativeButtonText = "İptal",
                            onPositiveButtonClicked = {loginViewModel.signInWithFirebase(user.email,user.password)},
                            onNegativeButtonClicked = {}
                        )
                    }
                }
            }

        }

        binding.fragmentLoginSaveButtonId.setOnClickListener {
            val usermail = binding.fragmentLoginEmailLayoutId.editText?.text.toString()
            val password = binding.fragmentLoginPasswordLayoutId.editText?.text.toString()
            user = User(usermail,password)
            if (user.email.isNotEmpty() && user.password.isNotEmpty()) {
                loginViewModel.loginWithFirebase(usermail, password)
            } else {
                // Kullanıcıya bilgi girişini tamamlaması gerektiğini belirtin.
                Toast.makeText(
                    requireContext(),
                    "Lütfen tüm bilgileri doldurun",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}