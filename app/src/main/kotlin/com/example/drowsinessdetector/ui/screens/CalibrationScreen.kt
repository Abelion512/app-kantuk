package com.example.drowsinessdetector.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drowsinessdetector.R
import kotlinx.coroutines.delay

@Composable
fun CalibrationScreen(
    onCalibrationComplete: () -> Unit,
    onCancel: () -> Unit
) {
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(100)
            progress += 0.01f
        }
        onCalibrationComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Mock Camera Feed
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F172A)) // Dark blueish
        )

        // Progress Bar at top
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = Color(0xFF81C784),
            trackColor = Color.White.copy(alpha = 0.2f),
        )

        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.4f)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.cancel), fontSize = 14.sp)
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Red.copy(alpha = 0.2f))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Red, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("REC", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Face Guide
        FaceGuideOverlay(modifier = Modifier.align(Alignment.Center))

        // Bottom Instruction Card
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF1E1E1E).copy(alpha = 0.9f))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF81C784).copy(alpha = 0.2f)
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null,
                    tint = Color(0xFF81C784),
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.face_alignment),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.calibration_desc),
                color = Color.LightGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Dots
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { i ->
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(
                                if (i == 0) Color.White else Color.White.copy(alpha = 0.4f),
                                RoundedCornerShape(3.dp)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun FaceGuideOverlay(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "scan")
    val scanAnim by infiniteTransition.animateFloat(
        initialValue = -160f,
        targetValue = 160f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanLine"
    )

    Box(modifier = modifier.size(260.dp, 320.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
            drawOval(
                color = Color.White.copy(alpha = 0.5f),
                style = stroke
            )

            // Corners
            val cornerLength = 24.dp.toPx()
            val cornerWidth = 4.dp.toPx()

            // Top Left
            drawLine(Color(0xFF81C784), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Offset(cornerLength, 0f), cornerWidth)
            drawLine(Color(0xFF81C784), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Offset(0f, cornerLength), cornerWidth)

            // Top Right
            drawLine(Color(0xFF81C784), androidx.compose.ui.geometry.Offset(size.width, 0f), androidx.compose.ui.geometry.Offset(size.width - cornerLength, 0f), cornerWidth)
            drawLine(Color(0xFF81C784), androidx.compose.ui.geometry.Offset(size.width, 0f), androidx.compose.ui.geometry.Offset(size.width, cornerLength), cornerWidth)

            // Bottom Left
            drawLine(Color(0xFF81C784), androidx.compose.ui.geometry.Offset(0f, size.height), androidx.compose.ui.geometry.Offset(cornerLength, size.height), cornerWidth)
            drawLine(Color(0xFF81C784), androidx.compose.ui.geometry.Offset(0f, size.height), androidx.compose.ui.geometry.Offset(0f, size.height - cornerLength), cornerWidth)

            // Bottom Right
            drawLine(Color(0xFF81C784), androidx.compose.ui.geometry.Offset(size.width, size.height), androidx.compose.ui.geometry.Offset(size.width - cornerLength, size.height), cornerWidth)
            drawLine(Color(0xFF81C784), androidx.compose.ui.geometry.Offset(size.width, size.height), androidx.compose.ui.geometry.Offset(size.width, size.height - cornerLength), cornerWidth)
        }

        // Scan line
        Box(
            modifier = Modifier
                .offset(y = 160.dp + scanAnim.dp)
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color.Transparent, Color(0xFF81C784), Color.Transparent)
                    )
                )
        )
    }
}
