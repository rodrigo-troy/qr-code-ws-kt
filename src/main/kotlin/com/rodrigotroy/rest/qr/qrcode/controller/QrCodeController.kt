package com.rodrigotroy.rest.qr.qrcode.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.awt.Color
import java.awt.image.BufferedImage

@RestController
@RequestMapping("/api/qrcode")
class QrCodeController {

    @GetMapping
    fun qrcode(): ResponseEntity<BufferedImage> {
        val width = 250
        val height = 250
        
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()
        
        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, width, height)
        graphics.dispose()
        
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(image)
    }

    @GetMapping("/generate")
    fun generateQrCode(@RequestParam content: String): Map<String, String> {
        // In a real implementation, this would generate a QR code
        return mapOf("content" to content, "status" to "QR code would be generated here")
    }
}
