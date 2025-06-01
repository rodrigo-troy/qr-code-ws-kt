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
    fun `should return 200 OK with PNG image when valid contents, size and type are provided`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "abcdef")
                .param("size", "250")
                .param("type", "png"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
    }
    
    @Test
    fun `should return 200 OK with JPEG image when valid contents, size and jpeg type are provided`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "test content")
                .param("size", "200")
                .param("type", "jpeg"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
    }
    
    @Test
    fun `should return 200 OK with GIF image when valid contents, size and gif type are provided`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "hello world")
                .param("size", "300")
                .param("type", "gif"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.IMAGE_GIF))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when contents is empty`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "")
                .param("size", "200")
                .param("type", "png"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Contents cannot be null or blank"))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when contents is blank`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "   ")
                .param("size", "200")
                .param("type", "png"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Contents cannot be null or blank"))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when contents is missing`() {
        mockMvc.perform(get("/api/qrcode")
                .param("size", "200")
                .param("type", "png"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Contents cannot be null or blank"))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when size is less than 150`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "test")
                .param("size", "100")
                .param("type", "png"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Image size must be between 150 and 350 pixels"))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when size is greater than 350`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "test")
                .param("size", "400")
                .param("type", "png"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Image size must be between 150 and 350 pixels"))
    }
    
    @Test
    fun `should return 400 BAD REQUEST when type is not supported`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "test")
                .param("size", "250")
                .param("type", "tiff"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Only png, jpeg and gif image types are supported"))
    }
    
    @Test
    fun `should return contents error when contents and type are invalid`() {
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "")
                .param("size", "250")
                .param("type", "tiff"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error").value("Contents cannot be null or blank"))
    }
    
    @Test
    fun `should accept boundary values for size`() {
        // Test lower boundary
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "test")
                .param("size", "150")
                .param("type", "png"))
                .andExpect(status().isOk)
                
        // Test upper boundary
        mockMvc.perform(get("/api/qrcode")
                .param("contents", "test")
                .param("size", "350")
                .param("type", "png"))
                .andExpect(status().isOk)
    }
}
