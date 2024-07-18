package cz.nuanced.composemindframe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                ) { innerPadding ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color.DarkGray)
                    ){
                        Meme(content = "Android")
                        Meme(content = "Compose")
                        Meme(content = "Often these building blocks are all you need. You can write" +
                                " your own composable function to combine these layouts into a more" +
                                " elaborate layout that suits your app.")
                        ConfirmButton(onClick = { /*TODO*/ })
                    }


                }
            }
        }
    }
}

@Composable
fun Meme(content: String) {
    Text(
        text = content,
        modifier = Modifier
            .padding(4.dp, 6.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black)
            .background(Color.Gray)
        ,
        color = Color.White
    )
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
        Text(text = "->",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(1.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun MemePreview() {
    ComposeMindFrameTheme {
        Meme("Android")
    }
}