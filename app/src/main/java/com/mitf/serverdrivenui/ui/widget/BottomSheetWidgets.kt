package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.OptionModel
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.getComposableWidget
import com.mitf.serverdrivenui.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
class BottomSheetWidgets(
    val widgetId: MutableState<String>,
    val list: List<OptionModel>,
    val scope: CoroutineScope,
    val onClicked: MutableState<Boolean> = mutableStateOf(false),
    val onItemSelected: (OptionModel, String) -> Unit,
    val composable: @Composable () -> Unit
) : ComposableWidget {

    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {

        val keyboardController = LocalSoftwareKeyboardController.current
        val state = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        var searchSelectorItem by remember { mutableStateOf("") }

        BottomSheetScaffold(
            sheetContent = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.7f)
                            .background(
                                color = White,
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth(0.2f)
                                .height(4.dp)
                                .background(
                                    color = Black200,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                        )
                        Text(
                            text = "Pilih " + TextFieldSelectorWidget.selectorLabel.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = BodyBold,
                            textAlign = TextAlign.Start
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = searchSelectorItem, onValueChange = {
                                searchSelectorItem = it
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = White,
                                cursorColor = Blue600,
                                focusedBorderColor = Blue600,
                                unfocusedBorderColor = Black300
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search"
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {}),
                            placeholder = {
                                Text(
                                    text = "Cari " + TextFieldSelectorWidget.selectorLabel.value,
                                    style = CaptionRegular,
                                    color = Black600
                                )
                            }
                        )
                        LazyColumn {
                            items(list) { item ->
                                ListItem(
                                    modifier = Modifier.clickable {
                                        scope.launch {
                                            onItemSelected(item, widgetId.value)
                                            if (state.bottomSheetState.isCollapsed) {
                                                keyboardController?.hide()
                                                state.bottomSheetState.expand()
                                            } else {
                                                keyboardController?.hide()
                                                state.bottomSheetState.collapse()
                                            }
                                        }
                                    },
                                    text = { Text(item.value) }
                                )
                            }
                        }
                    }
                }
            },
            scaffoldState = state,
            sheetPeekHeight = 0.dp,
            sheetBackgroundColor = BackgroundTransparent,
            sheetElevation = 0.dp,
            drawerGesturesEnabled = false
        ) {
            composable()
            if (onClicked.value) {
                scope.launch {
                    if (state.bottomSheetState.isCollapsed) {
                        keyboardController?.hide()
                        state.bottomSheetState.expand()
                        TextFieldSelectorWidget.isClicked.value = false
                    } else {
                        keyboardController?.hide()
                        state.bottomSheetState.collapse()
                        TextFieldSelectorWidget.isClicked.value = true
                    }
                }
            }
        }

    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}