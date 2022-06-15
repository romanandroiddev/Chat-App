package uz.project.graphqlchatapp.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import uz.project.graphql.MessageListQuery
import uz.project.graphqlchatapp.R
import uz.project.graphqlchatapp.databinding.ScreenChatBinding
import uz.project.graphqlchatapp.presentation.adapters.ChatAdapter
import uz.project.graphqlchatapp.presentation.viewModels.impls.ChatScreenVMImpl
import uz.project.graphqlchatapp.presentation.viewModels.interfaces.ChatScreenVM
import uz.project.minusgram.utils.showHideView
import uz.project.minusgram.utils.showToast


@AndroidEntryPoint
class ChatScreen : Fragment(R.layout.screen_chat) {
    private val binding by viewBinding(ScreenChatBinding::bind)
    private val viewModel: ChatScreenVM by viewModels<ChatScreenVMImpl>()
    private val adapter = ChatAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TTT", "foods : ")
        initListeners()
        initObservers()

        lifecycleScope.launchWhenResumed {
            viewModel.getAll()
        }
    }

    private fun initListeners() {

        binding.rcView.adapter = adapter

        binding.apply {
            sendBtn.clicks().debounce(200).onEach {
//                viewModel.
            }.launchIn(lifecycleScope)

        }

    }

    private fun initObservers() {
        viewModel.allMessageFlow.onEach {
            adapter.models = it.messages as MutableList<MessageListQuery.Message>
        }.launchIn(lifecycleScope)


        viewModel.errorFlow.onEach {
            Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        viewModel.messageFlow.onEach {
            showToast(requireContext(), it)
        }.launchIn(lifecycleScope)

        viewModel.loaderFlow.onEach {
            showHideView(binding.progressBar, it)
        }.launchIn(lifecycleScope)


        viewModel.observeFoodFlow.onEach {
            adapter.models.add(it.newMessage as MessageListQuery.Message)
        }.launchIn(lifecycleScope)
    }
}
