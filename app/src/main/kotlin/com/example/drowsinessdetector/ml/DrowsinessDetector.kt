package com.example.drowsinessdetector.ml

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class DrowsinessDetector(
    private val onDrowsinessDetected: () -> Unit
) {
    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setMinFaceSize(0.15f)
        .build()

    private val detector = FaceDetection.getClient(options)

    private var drowsyFramesCount = 0
    private val DROWSY_THRESHOLD_FRAMES = 10 // Approximate for ~1-2 seconds depending on FPS

    fun processImage(image: InputImage) {
        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isEmpty()) {
                    drowsyFramesCount = 0
                    return@addOnSuccessListener
                }

                val face = faces[0]
                val leftEyeOpen = face.leftEyeOpenProbability ?: 1.0f
                val rightEyeOpen = face.rightEyeOpenProbability ?: 1.0f

                if (leftEyeOpen < 0.2f && rightEyeOpen < 0.2f) {
                    drowsyFramesCount++
                } else {
                    drowsyFramesCount = 0
                }

                if (drowsyFramesCount >= DROWSY_THRESHOLD_FRAMES) {
                    onDrowsinessDetected()
                    drowsyFramesCount = 0 // Reset after triggering
                }
            }
    }

    fun close() {
        detector.close()
    }
}
