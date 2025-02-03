package com.example.secretmessageapp

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon



@Composable
fun EncoderDecoderApp() {
    var inputEncode by remember { mutableStateOf("") }
    var encodedResult by remember { mutableStateOf("") }
    var inputDecode by remember { mutableStateOf("") }
    var decodedResult by remember { mutableStateOf("") }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            tint = Color.Cyan
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Encoder/Decoder App",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 60.dp, bottom = 16.dp)
        )

        // Encoding Section
        OutlinedTextField(
            value = inputEncode,
            onValueChange = { inputEncode = it },
            label = { Text("Enter a string to encode") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (inputEncode.isNotEmpty()) {
                    encodedResult = encode(inputEncode)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black
            )
        ) {
            Text("Encode")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            SelectionContainer {
                Text(
                    text = "Encoded: $encodedResult",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            IconButton(
                onClick = { shareText(context, encodedResult) },
                enabled = encodedResult.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Encoded",
                    tint = Color.Green
                )
            }
        }

        // Decoding Section
        OutlinedTextField(
            value = inputDecode,
            onValueChange = { inputDecode = it },
            label = { Text("Enter a string to decode") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (inputDecode.isNotEmpty()) {
                    decodedResult = decode(inputDecode)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.Black
            )
        ) {
            Text("Decode")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            SelectionContainer {
                Text(
                    text = "Decoded: $decodedResult",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            IconButton(
                onClick = { shareText(context, decodedResult) },
                enabled = decodedResult.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Decoded",
                    tint = Color.Red
                )
            }
        }
    }
}

private fun shareText(context: Context, text: String) {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(
            Intent.createChooser(shareIntent, "Share secret message via:")
        )
    } catch (e: Exception) {
        Toast.makeText(
            context,
            "No apps available for sharing",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun encode(input: String): String {
    return input.map { char ->
        (char.code + 2).toChar()
    }.joinToString("")
}

fun decode(input: String): String {
    return input.map { char ->
        (char.code - 2).toChar()
    }.joinToString("")
}