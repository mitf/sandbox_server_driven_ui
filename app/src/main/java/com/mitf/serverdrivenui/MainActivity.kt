package com.mitf.serverdrivenui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mitf.serverdrivenui.dto.OptionModel
import com.mitf.serverdrivenui.dto.ScreenDto
import com.mitf.serverdrivenui.ui.Screen
import com.mitf.serverdrivenui.ui.theme.*
import com.mitf.serverdrivenui.ui.widget.BottomSheetWidget
import com.mitf.serverdrivenui.ui.widget.TextFieldSelectorWidget
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun MyScreenContent() {
    val screenJson = ServiceLocator.resolve(BackEndService::class.java).getPage("/", mapOf())
    val screenJsonString = StringHolder(remember { mutableStateOf(screenJson) })
    val moshi = Moshi.Builder().build()
    val screenAdapter = moshi.adapter(ScreenDto::class.java)

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

    CompositionLocalProvider(ScreenJson provides screenJsonString) {
        val holder = ScreenJson.current
        screenAdapter.fromJson(holder.held.value)?.let {
            BottomSheetWidget(
                list = list,
                onClicked = TextFieldSelectorWidget.isClicked,
                scope = coroutineScope,
                onItemSelected = {

                }) {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val (header, lineCenter, body) = createRefs()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.5f)
                            .background(Blue600)
                            .constrainAs(header) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(lineCenter.top)
                            },
                    ) {
                        ConstraintLayout {
                            val (bg, icon) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.bg_login_top),
                                contentDescription = "ImageBackgroundTop",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .constrainAs(bg) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentDescription = "ImageBackgroundTop",
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .constrainAs(icon) {
                                        top.linkTo(parent.top, margin = 20.dp)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(lineCenter) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                    )
                    Card(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        modifier = Modifier
                            .constrainAs(body) {
                                top.linkTo(lineCenter.bottom)
                                bottom.linkTo(lineCenter.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Screen(it).compose()
                    }
                }
            }
        }
    }
}
