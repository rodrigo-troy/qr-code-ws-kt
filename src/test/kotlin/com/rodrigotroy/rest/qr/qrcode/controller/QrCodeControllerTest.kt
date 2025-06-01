package com.rodrigotroy.rest.qr.qrcode.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(QrCodeController::class)
class QrCodeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return QR code information when generate endpoint is called`() {
        val testContent = "test-content"

        mockMvc.perform(get("/api/qrcode/generate")
                .param("content", testContent))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.content").value(testContent))
                .andExpect(jsonPath("$.status").value("QR code would be generated here"))
    }
}
