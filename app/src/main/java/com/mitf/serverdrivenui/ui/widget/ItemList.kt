package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mitf.serverdrivenui.ui.theme.Black200
import com.mitf.serverdrivenui.ui.theme.CaptionBold
import com.mitf.serverdrivenui.ui.theme.CaptionMedium

@Preview(showBackground = true)
@Composable
fun itemList() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val line = createRef()
        val title = createRef()
        val status = createRef()
        val groupBtn = createRef()
        Spacer(
            modifier = Modifier
                .constrainAs(line) {
                    linkTo(start = parent.start, end = parent.end)
                }
                .padding(top = 8.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(color = Black200),
        )
        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(line.bottom)
                    start.linkTo(parent.start)
                }
                .padding(top = 8.dp)
                .fillMaxWidth(0.5f),
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Latest sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = CaptionBold
        )
        Text(
            modifier = Modifier.constrainAs(status) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            text = "test 2", maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = CaptionMedium
        )
        Row(modifier = Modifier
            .constrainAs(groupBtn) {
                top.linkTo(title.top)
                end.linkTo(parent.end)
                bottom.linkTo(status.bottom)
            }
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Email, contentDescription = "")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Email, contentDescription = "")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Email, contentDescription = "")
            }
        }
    }
}