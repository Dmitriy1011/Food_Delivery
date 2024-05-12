package ru.testapp.ui.elements

import androidx.annotation.DrawableRes
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun NavigateBackButton(
    modifier: Modifier = Modifier,
    @DrawableRes imageId: Int,
    navigateUp: () -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        )
    ) {
        IconButton(onClick = navigateUp) {
            Icon(painter = painterResource(id = imageId), contentDescription = null)
        }
    }
}