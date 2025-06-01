package com.rodrigotroy.rest.qr.qrcode.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(QrCodeController::class)
class QrCodeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return 200 OK with PNG image when valid size and type are provided`() {
        mockMvc.perform(get("/api/qrcode")
                .param("size", "250")
                .param("type", "png"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
    }
    
    @Test
    fun `should return 200 OK with JPEG image when valid size and jpeg type are provided`() {
        mockMvc.perform(get("/api/qrcode")
                .param("size", "200")
                .param("type", "jpeg"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
    }
    
    @Test
    fun `should return 200 OK with GIF image when valid size and gif type are provided`() {
        mockMvc.perform(get("/api/qrcode")
                .param("size", "300")
                .param("type", "gif"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.IMAGE_GIF))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when size is less than 150`() {
        mockMvc.perform(get("/api/qrcode")
                .param("size", "100")
                .param("type", "png"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Image size must be between 150 and 350 pixels"))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when size is greater than 350`() {
        mockMvc.perform(get("/api/qrcode")
                .param("size", "400")
                .param("type", "png"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Image size must be between 150 and 350 pixels"))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when type is not supported`() {
        mockMvc.perform(get("/api/qrcode")
                .param("size", "250")
                .param("type", "tiff"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Only png, jpeg and gif image types are supported"))
    }
    
    @Test
    fun `should return size error when both parameters are invalid`() {
        mockMvc.perform(get("/api/qrcode")
                .param("size", "100")
                .param("type", "tiff"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Image size must be between 150 and 350 pixels"))
    }
    
    @Test
    fun `should accept boundary values for size`() {
        // Test lower boundary
        mockMvc.perform(get("/api/qrcode")
                .param("size", "150")
                .param("type", "png"))
                .andExpect(status().isOk)
                
        // Test upper boundary
        mockMvc.perform(get("/api/qrcode")
                .param("size", "350")
                .param("type", "png"))
                .andExpect(status().isOk)
    }

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
