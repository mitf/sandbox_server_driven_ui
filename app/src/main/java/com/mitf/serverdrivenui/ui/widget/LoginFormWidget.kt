package com.mitf.serverdrivenui.ui.widget

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mitf.serverdrivenui.BackEndService
import com.mitf.serverdrivenui.R
import com.mitf.serverdrivenui.ScreenJson
import com.mitf.serverdrivenui.ServiceLocator
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.getComposableWidget

class LoginFormWidget(
    private val widgetDto: WidgetDto
) : ComposableWidget {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        val childElements = widgetDto.children?.map { it.getComposableWidget() } ?: listOf()
        val children = childElements.map { Pair(it, it.getHoist()) }

//        val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
//        val scope = rememberCoroutineScope()
//        ModalBottomSheetLayout(sheetContent = {
//
//        }, sheetState = state) {
//            ConstraintLayout(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                val (header, lineCenter, body) = createRefs()
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(fraction = 0.5f)
//                        .background(Color(0xff4782FC))
//                        .constrainAs(header) {
//                            top.linkTo(parent.top)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                            bottom.linkTo(lineCenter.top)
//                        },
//                ) {
//                    ConstraintLayout {
//                        val (bg, icon) = createRefs()
//                        Image(
//                            painter = painterResource(id = R.drawable.bg_login_top),
//                            contentDescription = "ImageBackgroundTop",
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .constrainAs(bg) {
//                                    top.linkTo(parent.top)
//                                    start.linkTo(parent.start)
//                                    end.linkTo(parent.end)
//                                }
//                        )
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_logo),
//                            contentDescription = "ImageBackgroundTop",
//                            contentScale = ContentScale.Inside,
//                            modifier = Modifier
//                                .constrainAs(icon) {
//                                    top.linkTo(parent.top, margin = 20.dp)
//                                    start.linkTo(parent.start)
//                                    end.linkTo(parent.end)
//                                }
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier
//                    .fillMaxWidth()
//                    .constrainAs(lineCenter) {
//                        top.linkTo(body.top)
//                        bottom.linkTo(body.bottom)
//                        start.linkTo(body.start)
//                        end.linkTo(body.end)
//                    })
//                Card(
//                    elevation = 2.dp,
//                    shape = RoundedCornerShape(corner = CornerSize(16.dp)),
//                    modifier = Modifier
//                        .constrainAs(body) {
//                            top.linkTo(header.bottom)
//                            bottom.linkTo(header.bottom)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        }
//                        .padding(start = 16.dp, end = 16.dp)
//                ) {
//                    Column(
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier.padding(24.dp)
//                    ) {
//                        children.map {
//                            it.first.compose(it.second)
//                        }
//                        val json = ScreenJson.current
//                        Button(
//                            onClick = {
//                                val parameters = children.flatMap {
//                                    it.second.entries.map { maps ->
//                                        Pair(
//                                            maps.key,
//                                            maps.value.value
//                                        )
//                                    }
//                                }.toMap()
//                                Log.e("testing clicked", "$parameters")
//                                val newPage = ServiceLocator.resolve(BackEndService::class.java)
//                                    .getPage(widgetDto.data ?: "", parameters)
//                                json.held.value = newPage
//                            },
//                            modifier = Modifier
//                                .padding(top = 16.dp)
//                                .fillMaxWidth()
//                        ) {
//                            Text(widgetDto.label ?: "")
//                        }
//                    }
//                }
//            }
//        }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (header, lineCenter, body) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.5f)
                    .background(Color(0xff4782FC))
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
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(lineCenter) {
                    top.linkTo(body.top)
                    bottom.linkTo(body.bottom)
                    start.linkTo(body.start)
                    end.linkTo(body.end)
                })
            Card(
                elevation = 2.dp,
                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(header.bottom)
                        bottom.linkTo(header.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    children.map {
                        it.first.compose(it.second)
                    }
                    val json = ScreenJson.current
                    Button(
                        onClick = {
                            val parameters = children.flatMap {
                                it.second.entries.map { maps ->
                                    Pair(
                                        maps.key,
                                        maps.value.value
                                    )
                                }
                            }.toMap()
                            Log.e("testing clicked", "$parameters")
                            val newPage = ServiceLocator.resolve(BackEndService::class.java)
                                .getPage(widgetDto.data ?: "", parameters)
                            json.held.value = newPage
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(widgetDto.label ?: "")
                    }
                }
            }
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }

}