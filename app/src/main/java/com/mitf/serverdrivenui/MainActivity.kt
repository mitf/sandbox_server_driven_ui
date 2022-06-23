package com.mitf.serverdrivenui

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mitf.serverdrivenui.dto.OptionModel
import com.mitf.serverdrivenui.dto.ScreenDtoNew
import com.mitf.serverdrivenui.ui.Screen
import com.mitf.serverdrivenui.ui.theme.ServerDrivenUITheme
import com.mitf.serverdrivenui.ui.widget.BottomSheetWidget
import com.mitf.serverdrivenui.ui.widget.TextFieldSelectorWidget
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
            Log.d("datanyaCheck", "TopOfCOntent")
            ServerDrivenUITheme {
                Log.d("datanyaCheck", "TopOfCOntentTheme")
                MyScreenContent(viewModel)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

data class StringHolder(var held: MutableState<String> = mutableStateOf(""))

val ScreenJson = compositionLocalOf { StringHolder() }

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun MyScreenContent(viewModel: ViewModel) {
    val screenJson = ServiceLocator.resolve(BackEndService::class.java).getPage("/", mapOf())
    val screenJsonString = StringHolder(remember { mutableStateOf(screenJson) })
    val moshi = Moshi.Builder().build()
    val screenAdapter = moshi.adapter(ScreenDtoNew::class.java)

    val coroutineScope = rememberCoroutineScope()

    val list = listOf(
        OptionModel(
            "1",
            "value 1"
        ),
        OptionModel(
            "2",
            "value 2"
        ),
        OptionModel(
            "3",
            "value 3"
        ),
        OptionModel(
            "4",
            "value 4"
        ),
        OptionModel(
            "5",
            "value 5"
        ),
        OptionModel(
            "6",
            "value 6"
        ),
        OptionModel(
            "7",
            "value 7"
        ),
        OptionModel(
            "8",
            "value 8"
        ),
        OptionModel(
            "9",
            "value 9"
        ),
        OptionModel(
            "10",
            "value 10"
        )
    )
    val list2 = listOf(
        OptionModel(
            "1",
            "value 12"
        ),
        OptionModel(
            "2",
            "value 22"
        ),
        OptionModel(
            "3",
            "value 33"
        ),
        OptionModel(
            "4",
            "value 45"
        ),
        OptionModel(
            "5",
            "value 56"
        ),
        OptionModel(
            "6",
            "value 67"
        ),
        OptionModel(
            "7",
            "value 78"
        ),
        OptionModel(
            "8",
            "value 89"
        ),
        OptionModel(
            "9",
            "value 90"
        ),
        OptionModel(
            "10",
            "value 111"
        )
    )
    val listAll =
        when(TextFieldSelectorWidget.widgetId.value){
            "profession" -> list
            "lead_source" -> list2
            else -> listOf()
        }
    Log.d("datanyaIdField", TextFieldSelectorWidget.widgetId.value)
    Log.d("datanyaList===", listAll.toString())

    CompositionLocalProvider(ScreenJson provides screenJsonString) {
        val holder = ScreenJson.current
        screenAdapter.fromJson(holder.held.value)?.let {
            Log.d("datanyaList", "testcall")
            BottomSheetWidget(
                widgetId = TextFieldSelectorWidget.widgetId,
                list = TextFieldSelectorWidget.optionItem,
                onClicked = TextFieldSelectorWidget.isClicked,
                scope = coroutineScope,
                onItemSelected = { optionSelected, id ->
                    TextFieldSelectorWidget.isClicked.value = false
                    TextFieldSelectorWidget.itemSelected.value = optionSelected.value
                }
            ) {
                Screen(it).compose()
            }
        }
    }
}
