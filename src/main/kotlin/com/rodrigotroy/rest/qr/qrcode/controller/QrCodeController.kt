package com.rodrigotroy.rest.qr.qrcode.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@RestController
@RequestMapping("/api/qrcode")
class QrCodeController {

    @GetMapping
    fun qrcode(
        @RequestParam size: Int, @RequestParam type: String
    ): ResponseEntity<*> {
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

        // Generate image with requested size
        val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()

        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, size, size)
        graphics.dispose()

        // Convert image to byte array in requested format
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, type.lowercase(), outputStream)
        val imageBytes = outputStream.toByteArray()

        // Determine content type based on requested format
        val contentType = when (type.lowercase()) {
            "png" -> MediaType.IMAGE_PNG
            "jpeg" -> MediaType.IMAGE_JPEG
            "gif" -> MediaType.IMAGE_GIF
            else -> MediaType.IMAGE_PNG // This shouldn't happen due to validation
        }

        return ResponseEntity.ok().contentType(contentType).body(imageBytes)
    }
}
