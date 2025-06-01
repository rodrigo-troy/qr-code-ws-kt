package com.rodrigotroy.rest.qr.qrcode.controller

import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@RestController
@RequestMapping("/api/qrcode")
class QrCodeController {

    @GetMapping
    fun qrcode(
        @RequestParam contents: String?,
        @RequestParam size: Int,
        @RequestParam type: String
    ): ResponseEntity<*> {
        // Validate contents parameter (highest priority)
        if (contents.isNullOrBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(mapOf("error" to "Contents cannot be null or blank"))
        }

        // Validate size parameter
        if (size < 150 || size > 350) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(mapOf("error" to "Image size must be between 150 and 350 pixels"))
        }

        // Validate type parameter
        val supportedTypes = setOf("png", "jpeg", "gif")
        if (type.lowercase() !in supportedTypes) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(mapOf("error" to "Only png, jpeg and gif image types are supported"))
        }

        // Generate QR code
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, size, size)
            val bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix)

            // Convert image to byte array in requested format
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(bufferedImage, type.lowercase(), outputStream)
            val imageBytes = outputStream.toByteArray()

            // Determine content type based on requested format
            val contentType = when (type.lowercase()) {
                "png" -> MediaType.IMAGE_PNG
                "jpeg" -> MediaType.IMAGE_JPEG
                "gif" -> MediaType.IMAGE_GIF
                else -> MediaType.IMAGE_PNG // This shouldn't happen due to validation
            }

            ResponseEntity.ok().contentType(contentType).body(imageBytes)
        } catch (e: WriterException) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Failed to generate QR code"))
        }
    }
}
