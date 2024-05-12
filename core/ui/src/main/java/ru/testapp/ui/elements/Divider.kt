package ru.testapp.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divider() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .alpha(0.12F),
    ) {}
}