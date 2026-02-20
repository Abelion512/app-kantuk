package com.example.drowsinessdetector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drowsinessdetector.R
import com.example.drowsinessdetector.ml.DrowsinessDetector
import kotlinx.coroutines.delay

@Composable
fun MonitoringScreen(
    onEndMonitoring: () -> Unit,
    onDrowsinessDetected: () -> Unit
) {
    var earValue by remember { mutableFloatStateOf(0.35f) }
    var marValue by remember { mutableFloatStateOf(0.12f) }

    val detector = remember {
        DrowsinessDetector(onDrowsinessDetected = onDrowsinessDetected)
    }

    DisposableEffect(Unit) {
        onDispose {
            detector.close()
        }
    }

    // Simulate real-time metrics and random drowsiness (Fallback for demo)
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            earValue = (0.30f + (Math.random() * 0.1f)).toFloat()
            marValue = (0.10f + (Math.random() * 0.05f)).toFloat()

            // Randomly trigger drowsiness for demo
            if (Math.random() < 0.05) {
                onDrowsinessDetected()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        // Map texture placeholder
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        )

        // Top Status
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFF1E1E1E).copy(alpha = 0.9f),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Visibility,
                        contentDescription = null,
                        tint = Color(0xFF81C784),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        stringResource(R.string.keep_focused),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }

        // Metrics Panel
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 120.dp, end = 16.dp)
                .width(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1E1E1E).copy(alpha = 0.95f))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Text(
                "REAL-TIME METRICS",
                color = Color.Gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            MetricRow("EAR", earValue, Color(0xFF81C784))
            Spacer(modifier = Modifier.height(8.dp))
            MetricRow("MAR", marValue, Color(0xFF81C784))
        }

        // Mini Monitoring Feed
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 140.dp)
                .size(100.dp, 120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for face
            Icon(Icons.Default.Visibility, contentDescription = null, tint = Color(0xFF81C784).copy(alpha = 0.3f), modifier = Modifier.size(40.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(vertical = 2.dp)
            ) {
                Text(
                    "Monitoring",
                    color = Color.White,
                    fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Bottom Trip Panel
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color(0xFF1E1E1E),
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Target Tidur", color = Color.Gray, fontSize = 12.sp)
                    Text("22:30", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Optimalkan Kesehatan", color = Color(0xFF81C784), fontSize = 14.sp)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF4A4458))
                    ) {
                        Icon(Icons.Default.Pause, contentDescription = null, tint = Color.White)
                    }
                    IconButton(
                        onClick = onEndMonitoring,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFB3261E))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun MetricRow(label: String, value: Float, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color.LightGray, fontSize = 12.sp)
            Text("%.2f".format(value), color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { value.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(CircleShape),
            color = color,
            trackColor = Color.Gray.copy(alpha = 0.3f)
        )
    }
}
