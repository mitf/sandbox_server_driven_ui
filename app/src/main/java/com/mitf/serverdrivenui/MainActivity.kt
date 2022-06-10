package com.mitf.serverdrivenui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.mitf.serverdrivenui.dto.ScreenDto
import com.mitf.serverdrivenui.ui.Screen
import com.mitf.serverdrivenui.ui.theme.ServerDrivenUITheme
import com.squareup.moshi.Moshi

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    var positionData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.put(BackEndService::class.java, FakeBackEndService())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            ServerDrivenUITheme {
                MyScreenContent()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

data class StringHolder(var held: MutableState<String> = mutableStateOf(""))

val ScreenJson = compositionLocalOf { StringHolder() }

@Composable
fun MyScreenContent() {
    val screenJson = ServiceLocator.resolve(BackEndService::class.java).getPage("/", mapOf())
    val screenJsonString = StringHolder(remember { mutableStateOf(screenJson) })
    val moshi = Moshi.Builder().build()
    val screenAdapter = moshi.adapter(ScreenDto::class.java)
    CompositionLocalProvider(ScreenJson provides screenJsonString) {
        val holder = ScreenJson.current
        screenAdapter.fromJson(holder.held.value)?.let {
            Screen(it).compose()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ServerDrivenUITheme {
        MyScreenContent()
    }
}