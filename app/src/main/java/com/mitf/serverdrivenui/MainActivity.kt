package com.mitf.serverdrivenui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.mitf.serverdrivenui.dto.ScreenDto
import com.mitf.serverdrivenui.ui.Screen
import com.mitf.serverdrivenui.ui.theme.ServerDrivenUITheme
import com.squareup.moshi.Moshi

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.put(BackEndService::class.java, FakeBackEndService())
        setContent {
            ServerDrivenUITheme {
                MyScreenContent()
            }
        }
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