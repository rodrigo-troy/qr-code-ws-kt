package com.rodrigotroy.rest.qr.qrcode.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/qrcode")
class QrCodeController {

    @GetMapping("/generate")
    fun generateQrCode(@RequestParam content: String): Map<String, String> {
        // In a real implementation, this would generate a QR code
        return mapOf("content" to content, "status" to "QR code would be generated here")
    }
}
