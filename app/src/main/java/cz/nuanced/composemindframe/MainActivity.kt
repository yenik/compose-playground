package cz.nuanced.composemindframe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withRunningRecomposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.nuanced.composemindframe.ui.theme.ComposeMindFrameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMindFrameTheme {
                var text by remember { mutableStateOf("input text") }
                Scaffold(
//                    floatingActionButtonPosition = FabPosition.End,
//                    floatingActionButton = {
//                        FloatingActionButton(onClick = { /*TODO*/ }) {
//                            ConfirmButton(onClick = { /*TODO*/ })
//                        }
//                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(3.6f)
                                .background(Color.DarkGray)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Meme(content = "Android")
                            Meme(content = "Compose")
                            repeat(12) {
                                Meme(
                                    content = "Often these building blocks are all you need. You can write" +
                                            " your own composable function to combine these layouts into a more" +
                                            " elaborate layout that suits your app."
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(.4f)
                                .padding(8.dp, 4.dp)
                                .background(Color.DarkGray, shape = MaterialTheme.shapes.small)
                                .horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(7) {
                                TagButton(tag = "Android")
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp, 0.dp)
                                .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                        ) {
                            BasicTextField(
                                value = text,
                                onValueChange = { text = it },
                                textStyle = TextStyle(fontSize = 18.sp, color = Color.DarkGray),
                                modifier = Modifier
                                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(8.dp)
                            ) {
                                Text(text = "Send")
                            }
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFF7C1C1C))
                            ) {
                                Text(text = "Add")
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Meme(content: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.Gray, shape = MaterialTheme.shapes.small)
    ) {
        Text(
            text = content,
            modifier = Modifier
                .padding(6.dp),
            color = Color.White
        )
    }
}

@Composable
fun ConfirmButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = { onClick() },
        Modifier
            .background(Color.Gray)
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(40.dp)
            ),
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(
            text = "Confirm",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(1.dp)
        )

    }
}

@Composable
fun TagButton(tag: String) {
    SuggestionChip(
        onClick = { /*TODO*/ },
        label = {
            Text(
                text = tag,
                color = Color.White
            )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = Color(0xFF7C1C1C)
        ),
        modifier = Modifier
            .padding(4.dp, 0.dp)
    )
}

@Composable
fun Survey(modifier: Modifier = Modifier) {
    Row {
        Image(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "logo")
        Text(text = "logo", color = Color(0xFF7C1C1C))
        RadioButton(selected = false, onClick = { /*TODO*/ })
    }
}

@Preview(showBackground = true)
@Composable
fun MemePreview() {
    ComposeMindFrameTheme {
        Survey()
    }
}