package com.mitf.serverdrivenui.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mitf.serverdrivenui.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mitf.serverdrivenui.MainViewModel
import com.mitf.serverdrivenui.databinding.FragmentDashboardBinding
import com.mitf.serverdrivenui.ui.theme.Purple700
import com.mitf.serverdrivenui.ui.theme.primaryBlue
import com.mitf.serverdrivenui.ui.theme.primaryYellow

class DashboardFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setContent {
                composeView()
            }
        }
    }

    @Composable
    fun composeTest() {
        Box(modifier = Modifier.fillMaxSize()) {

        }
    }

    @Composable
    fun composeView() {
        var txtUsername by remember {
            mutableStateOf("")
        }
        var txtPassword by remember {
            mutableStateOf("")
        }
        var passwordVisibility by remember {
            mutableStateOf(false)
        }
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (header, body, footer) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryBlue)
                    .fillMaxHeight(fraction = 0.5F)
                    .constrainAs(header) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            ) {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (iconTitle, background) = createRefs()
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "ImageIcon",
                        modifier = Modifier.constrainAs(iconTitle) {
                            top.linkTo(parent.top, margin = 20.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.bg_login_top),
                        contentDescription = "ImageIcon",
                        modifier = Modifier
                            .fillMaxWidth(fraction = 1F)
                            .constrainAs(background) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            }
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(150.dp, 400.dp)
                    .padding(20.dp, 0.dp)
                    .constrainAs(body) {
                        start.linkTo(parent.start, margin = 10.dp)
                        end.linkTo(parent.end, margin = 10.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                shape = RoundedCornerShape(20.dp),
                elevation = 8.dp
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val (txtTitleLogin, edtUsername, edtPassword, txtForgotPassword, imgBottom, btnLogin) = createRefs()
                    Text(
                        modifier = Modifier
                            .padding(20.dp, 30.dp, 0.dp, 0.dp)
                            .constrainAs(txtTitleLogin) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        text = "Selamat Datang"
                    )
                    OutlinedTextField(
                        value = txtUsername,
                        onValueChange = { txtUsername = it },
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier
                            .padding(20.dp, 0.dp)
                            .fillMaxWidth()
                            .constrainAs(edtUsername) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(txtTitleLogin.bottom, 30.dp)
                            },
                        label = { Text("Username") },
                    )
                    OutlinedTextField(
                        value = txtPassword,
                        onValueChange = { txtPassword = it },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisibility) R.drawable.ic_baseline_visibility_24
                                    else R.drawable.ic_baseline_visibility_off_24
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        passwordVisibility = !passwordVisibility
                                    }
                            )
                        },
                        label = {
                            Text(text = "Password")
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .padding(20.dp, 0.dp)
                            .fillMaxWidth()
                            .constrainAs(edtPassword) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(edtUsername.bottom, 15.dp)
                            },
                    )
                    ClickableText(
                        AnnotatedString("Lupa kata sandi?"),
                        style = TextStyle(
                            color = primaryYellow,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.constrainAs(txtForgotPassword) {
                            top.linkTo(edtPassword.bottom, 30.dp)
                            end.linkTo(parent.end, 20.dp)
                        },
                        onClick = {
                            Log.d("clicked", "Lupa Password")
                        }
                    )
                    Button(
                        onClick = {
                            Log.d(
                                "datanyaUser",
                                "Username : $txtUsername dan Password : $txtPassword"
                            )
                            viewModel.username.value = txtUsername
                            viewModel.password.value = txtPassword
                            findNavController().navigate(R.id.navigation_second)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            primaryYellow
                        ),
                        contentPadding = PaddingValues(
                            0.dp, 15.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 10.dp)
                            .constrainAs(btnLogin) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(txtForgotPassword.bottom, 10.dp)
                            }
                    ) {
                        Text(
                            text = "Login",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
//                    Image(
//                        painter = painterResource(id = R.drawable.bg_bottom),
//                        contentDescription = "ImageIcon",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(80.dp)
//                            .constrainAs(imgBottom) {
//                                end.linkTo(parent.end)
//                                start.linkTo(parent.start)
//                                bottom.linkTo(parent.bottom)
//                            }
//                    )
                }
            }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(20.dp, 35.dp)
                    .constrainAs(footer) {
                        bottom.linkTo(parent.bottom, 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "Terdaftar dan \n diawasi oleh"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo_ojk),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(80.dp),
                    )
                }
            }
        }
    }
}