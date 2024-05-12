//package ru.testapp.nti_test
//
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import kotlinx.coroutines.delay
//import ru.testapp.app.nti_test.R
//
//@Composable
//fun AnimatedSplashScreen() {
//
//    var startAnimation by remember { mutableStateOf(false) }
//    val alphaAnimation = animateFloatAsState(
//        targetValue = if (startAnimation) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 3000
//        ), label = ""
//    )
//
//    LaunchedEffect(key1 = true) {
//        startAnimation = true
//        delay(4000)
//    }
//
//    Splash(alphaValue = alphaAnimation.value)
//}
//
//@Composable
//fun Splash(alphaValue: Float) {
//    Box(
//        modifier = Modifier
//            .background(Color(0xFFF15412))
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center,
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.splash_logo),
//            contentDescription = null,
//            modifier = Modifier.alpha(alphaValue)
//        )
//    }
//}
