package uz.project.graphqlchatapp.presentation.ui.signupOrLogin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import uz.project.graphqlchatapp.R
import uz.project.graphqlchatapp.databinding.ScreenSignUpBinding
import uz.project.graphqlchatapp.presentation.viewModels.impls.SignUpScreenVMImpl
import uz.project.graphqlchatapp.presentation.viewModels.interfaces.SignUpScreenVM


@AndroidEntryPoint
class SignUpScreen : Fragment(R.layout.screen_sign_up) {
    private val binding by viewBinding(ScreenSignUpBinding::bind)
    private val viewModel: SignUpScreenVM by viewModels<SignUpScreenVMImpl>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.apply {
            btnSignup.clicks().onEach {
                if (etPassword.text.toString() == etPasswordRetry.text.toString()) {
                    viewModel.signUp(
                        userName = etUserName.text.toString(),
                        password = etPassword.text.toString()
                    )
                    tabs.getTabAt(1)!!.select()
                } else {
                    Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT)
                        .show()
                }
            }.launchIn(lifecycleScope)

            btnLogin.clicks().onEach {
                viewModel.addedUser.onEach {
                    if (it.signUp.name == binding.etUserNameLogin.text.toString()) {
                        findNavController().navigate(R.id.action_global_chatScreen)
                    }
                }.launchIn(lifecycleScope)
            }.launchIn(lifecycleScope)



            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tabs.selectedTabPosition) {
                        0 -> {
                            linearSignup.isVisible = true
                            linearLogin.isVisible = false
                        }
                        1 -> {
                            linearSignup.isVisible = false
                            linearLogin.isVisible = true
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun initObservers() {

        viewModel.addedUser.onEach {
            val name = it.signUp.name
            Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
            if (binding.etUserName.text.toString() == name) {
                viewModel.login(
                    binding.etUserNameLogin.text.toString(),
                    binding.etPasswordLogin.text.toString()
                )
            }
        }.launchIn(lifecycleScope)
        viewModel.errorFlow.onEach {
            Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        viewModel.messageFlow.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        viewModel.loaderFlow.onEach {

        }.launchIn(lifecycleScope)


    }

}

