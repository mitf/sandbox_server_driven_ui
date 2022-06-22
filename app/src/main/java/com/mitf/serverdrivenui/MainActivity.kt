package com.mitf.serverdrivenui

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mitf.serverdrivenui.dto.OptionModel
import com.mitf.serverdrivenui.dto.ScreenDto
import com.mitf.serverdrivenui.dto.ScreenDtoNew
import com.mitf.serverdrivenui.ui.Screen
import com.mitf.serverdrivenui.ui.theme.Blue600
import com.mitf.serverdrivenui.ui.theme.ServerDrivenUITheme
import com.mitf.serverdrivenui.ui.widget.BottomSheetWidget
import com.mitf.serverdrivenui.ui.widget.BottomSheetWidgets
import com.mitf.serverdrivenui.ui.widget.TextFieldSelectorWidget
import com.mitf.serverdrivenui.ui.widget.TextFieldWidget
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
//    val screenAdapter = moshi.adapter(ScreenDto::class.java)
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
    Log.d("datanyaList", listAll.toString())

    CompositionLocalProvider(ScreenJson provides screenJsonString) {
        val holder = ScreenJson.current
        Log.d("checkingAgainParentTop", holder.toString())
        Log.d("checkingAgainParent", holder.held.value)
        screenAdapter.fromJson(holder.held.value)?.let {
            Log.d("checkingAgain", it.toString())
//            BottomSheetWidgets(
//                widgetId = TextFieldSelectorWidget.widgetId,
//                list = list,
//                scope = rememberCoroutineScope(),
//                onClicked = TextFieldSelectorWidget.isClicked,
//                onItemSelected = { item, _ ->
//                    TextFieldSelectorWidget.isClicked.value = false
//                    TextFieldSelectorWidget.itemSelected.value = item.value
//                }
//            ) {
//                Screen(it).compose()
//            }
            BottomSheetWidget(
                widgetId = TextFieldSelectorWidget.widgetId,
//                list = list,
                list = listAll,
                onClicked = TextFieldSelectorWidget.isClicked,
                scope = coroutineScope,
                onItemSelected = { optionSelected, id ->
                    Log.d("datanyaId", id)
                    TextFieldSelectorWidget.isClicked.value = false
                    TextFieldSelectorWidget.itemSelected.value = optionSelected.value
                }
            ) {
                Screen(it).compose()
            }
//            BottomSheetWidget(
//                id = TextFieldSelectorWidget.widgetId,
//                list = list,
//                onClicked = TextFieldSelectorWidget.isClicked,
//                scope = coroutineScope,
//                onItemSelected = { optionSelected, id ->
//                    Log.d("datanyaId", id)
//                    TextFieldSelectorWidget.isClicked.value = false
//                    TextFieldSelectorWidget.itemSelected.value = optionSelected.value
//                }) {
//                ConstraintLayout(
//                    modifier = Modifier.fillMaxSize(),
//                ) {
//                    val (header, lineCenter, body) = createRefs()
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(fraction = 0.5f)
//                            .background(Blue600)
//                            .constrainAs(header) {
//                                top.linkTo(parent.top)
//                                start.linkTo(parent.start)
//                                end.linkTo(parent.end)
//                                bottom.linkTo(lineCenter.top)
//                            },
//                    ) {
//                        ConstraintLayout {
//                            val (bg, icon) = createRefs()
//                            Image(
//                                painter = painterResource(id = R.drawable.bg_login_top),
//                                contentDescription = "ImageBackgroundTop",
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .constrainAs(bg) {
//                                        top.linkTo(parent.top)
//                                        start.linkTo(parent.start)
//                                        end.linkTo(parent.end)
//                                    }
//                            )
//                            Image(
//                                painter = painterResource(id = R.drawable.ic_logo),
//                                contentDescription = "ImageBackgroundTop",
//                                contentScale = ContentScale.Inside,
//                                modifier = Modifier
//                                    .constrainAs(icon) {
//                                        top.linkTo(parent.top, margin = 20.dp)
//                                        start.linkTo(parent.start)
//                                        end.linkTo(parent.end)
//                                    }
//                            )
//                        }
//                    }
//                    Spacer(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .constrainAs(lineCenter) {
//                                top.linkTo(parent.top)
//                                bottom.linkTo(parent.bottom)
//                                start.linkTo(parent.start)
//                                end.linkTo(parent.end)
//                            },
//                    )
//                    Card(
//                        elevation = 2.dp,
//                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
//                        modifier = Modifier
//                            .constrainAs(body) {
//                                top.linkTo(lineCenter.bottom)
//                                bottom.linkTo(lineCenter.bottom)
//                                start.linkTo(parent.start)
//                                end.linkTo(parent.end)
//                            }
//                            .padding(start = 16.dp, end = 16.dp)
//                    ) {
//                        Screen(it).compose()
//                    }
//                }
//            }
        }
    }
}
