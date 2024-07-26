package cz.nuanced.composemindframe

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import cz.nuanced.composemindframe.db.MemeDatabaseSingleton
import cz.nuanced.composemindframe.db.Tag
import cz.nuanced.composemindframe.ui.theme.ComposeMindFrameTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        val memeDB = MemeDatabaseSingleton.getDatabase(this).getMemeDAO()
        val tagDB = MemeDatabaseSingleton.getDatabase(this).getTagDAO()
        val memes = mutableListOf<String>("prdel")
        val tags = mutableListOf<String>("check", "this", "out")

        lifecycleScope.launch {
            if(memeDB.getAll().isNotEmpty()) {
                memes.addAll(memeDB.getAll().map { it.content })
            }

            if(tagDB.getAllTags().isNotEmpty()) {
                tags.addAll(tagDB.getAllTags().map { it.id })
            }

            if (memes.size > 5) {
                tagDB.deleteAll()
                memeDB.deleteAll()
            }
        }
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
                            .padding(innerPadding)
                            .imePadding(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(3.6f)
                                .background(Color.DarkGray)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            memes.forEach { memeContent ->
                                Meme(content = memeContent)
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
                            tags.forEach {tag ->
                                TagButton(tag = tag)
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
                                onClick = {
                                    if (text.isNotEmpty()) {
                                        val extractedTags = extractTags(text)

                                        lifecycleScope.launch {
                                            extractedTags.forEach { tag ->
                                                tagDB.insert(Tag(id = tag.replace("#", ""), createdAt = LocalDate.now()))
                                            }
                                            memeDB.insert(cz.nuanced.composemindframe.db.Meme(
                                                    content = text,
                                                    createdAt = LocalDate.now())).also {
                                                        memes.add(text)
                                                        text = ""
                                            }
                                        }

                                        if(extractedTags.isNotEmpty()) {
                                            extractedTags.forEach { tag ->
                                                if(!tags.contains(tag)) {
                                                    tags.add(tag.replace("#", ""))
                                                }
                                            }
                                        }
                                    }
                                },
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

private fun extractTags(input: String): List<String> {
    return Regex("#[a-zA-Z0-9_]+").findAll(input).map { it.value }.toList()
}