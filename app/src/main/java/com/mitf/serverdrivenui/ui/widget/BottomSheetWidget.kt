package com.mitf.serverdrivenui.ui.widget

import android.util.Log
import androidx.compose.ui.ExperimentalComposeUiApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.OptionModel
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun BottomSheetWidget(
    widgetId: MutableState<String>,
    list: List<OptionModel>,
    scope: CoroutineScope,
    onClicked: MutableState<Boolean> = mutableStateOf(false),
    onItemSelected: (OptionModel, String) -> Unit,
    composable: @Composable () -> Unit
) {
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
                        value = searchSelectorItem,
                        onValueChange = {
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
                        keyboardActions = KeyboardActions(onSearch = {
                            Log.d("datanyaSearching", searchSelectorItem)

                        }),
                        placeholder = {
                            Text(
                                text = "Cari " + TextFieldSelectorWidget.selectorLabel.value,
                                style = CaptionRegular,
                                color = Black600
                            )
                        }
                    )
                    LazyColumn {
                        items(
                            if (searchSelectorItem.isEmpty()) {
                                list
                            } else {
                                list.filter { data -> data.value.contains(searchSelectorItem) }
                            }
                        ) { item ->
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
        sheetBackgroundColor = Color.Transparent,
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