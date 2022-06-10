package com.mitf.serverdrivenui.ui.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mitf.serverdrivenui.MainViewModel
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                initCompose(viewModel)
            }
        }
    }

    @Composable
    fun initCompose(viewModel: MainViewModel){
        val username by viewModel.username.observeAsState()
        val password by viewModel.password.observeAsState()
        val composableScope = rememberCoroutineScope()
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (parents) = createRefs()
            Column(modifier = Modifier
                .wrapContentSize(
                    align = Alignment.Center
                )
                .constrainAs(parents) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Username : $username")
                Text(text = "Password : $password")
                Button(onClick = { composableScope.launch {
                    initBottomSheet()
                } }) {

                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun initBottomSheet(){

    }
}

