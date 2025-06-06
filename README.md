# QR Code KT

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![JDK](https://img.shields.io/badge/JDK-21-orange.svg)](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Introduction

QR Code KT is a Spring Boot application written in Kotlin that provides a REST API for generating QR codes. The application allows users to generate QR codes in various image formats (PNG, JPEG, GIF) by providing content through a simple API endpoint.

This project serves as a demonstration of building RESTful web services using Spring Boot and Kotlin, with a focus on simplicity and maintainability. It uses the ZXing library for QR code generation.

## Project Structure

The project follows a standard Spring Boot application structure:

* **src/main/kotlin/com/rodrigotroy/rest/qr/qrcode**: Main application code
  * **QrCodeKtApplication.kt**: Entry point for the Spring Boot application
  * **controller/**: Contains REST controllers that define API endpoints
    * **QrCodeController.kt**: Defines the QR code generation endpoint

* **src/test/kotlin/com/rodrigotroy/rest/qr/qrcode**: Test code
  * **controller/**: Contains tests for controllers
    * **QrCodeControllerTest.kt**: Tests for the QR code controller

* **src/main/resources/**: Configuration files and static resources
  * **static/**: Static web resources (HTML, CSS, JavaScript)
  * **templates/**: Template files for server-side rendering

* **build.gradle.kts**: Gradle build configuration file that defines project dependencies and build settings

## Getting Started

### Prerequisites

* JDK 21 or later
* Gradle (or use the included Gradle wrapper)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/qr-code-kt.git
   cd qr-code-kt
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

### Running the Application

Run the application using the Gradle wrapper:

```bash
./gradlew bootRun
```

The application will start on port 8080 by default.

## Features

* **QR Code Generation API**: Generate QR codes by providing content through a REST API
* **Multiple Image Formats**: Support for PNG, JPEG, and GIF output formats
* **Customizable Size**: Generate QR codes with sizes between 150 and 350 pixels
* **Error Correction Levels**: Support for all four QR code error correction levels (L, M, Q, H)
* **Spring Boot Framework**: Built on the robust Spring Boot framework for easy development and deployment
* **Kotlin Language**: Written in Kotlin for concise, expressive, and safe code
* **RESTful Architecture**: Follows REST principles for API design
* **Comprehensive Test Suite**: Includes tests to ensure functionality works as expected
* **Input Validation**: Built-in validation for request parameters with meaningful error messages
* **Default Parameters**: Optional parameters with sensible defaults for easier usage

## Usage Examples

### Generating a QR Code

To generate a QR code, send a GET request to the `/api/qrcode` endpoint with the required parameters:

#### Parameters

| Parameter  | Type   | Required | Description                                      | Default | Constraints             |
|------------|--------|----------|--------------------------------------------------|---------|-------------------------|
| contents   | string | Yes      | The text or data to encode in the QR code       | -       | Cannot be empty/blank   |
| size       | int    | No       | The size of the QR code image in pixels         | 250     | 150-350                 |
| correction | string | No       | Error correction level                           | L       | L, M, Q, H              |
| type       | string | No       | The image format                                 | png     | png, jpeg, gif          |

#### Example Request

```bash
# Generate a QR code with default parameters
curl "http://localhost:8080/api/qrcode?contents=Hello%20World" --output qrcode.png

# Generate a PNG QR code with specific size
curl "http://localhost:8080/api/qrcode?contents=Hello%20World&size=300" --output qrcode.png

# Generate a JPEG QR code with high error correction
curl "http://localhost:8080/api/qrcode?contents=https://example.com&correction=H&type=jpeg" --output qrcode.jpg

# Generate a GIF QR code with all parameters
curl "http://localhost:8080/api/qrcode?contents=Contact%20Info&size=300&correction=Q&type=gif" --output qrcode.gif
```

### Using the API in a Web Application

```javascript
// Display QR code with minimal parameters (using defaults)
const imageUrl = 'http://localhost:8080/api/qrcode?contents=Hello%20World';
document.getElementById('qrcode').src = imageUrl;

// Generate QR code with high error correction for better durability
const durableQR = 'http://localhost:8080/api/qrcode?contents=Important%20Data&correction=H';
document.getElementById('durable-qr').src = durableQR;

// Download QR code with custom parameters
fetch('http://localhost:8080/api/qrcode?contents=Hello%20World&size=300&correction=M&type=jpeg')
  .then(response => response.blob())
  .then(blob => {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'qrcode.jpg';
    a.click();
  });
```

### Error Handling

The API returns meaningful error messages for invalid requests:

```bash
# Missing contents
curl "http://localhost:8080/api/qrcode"
# Response: {"error": "Contents cannot be null or blank"}

# Invalid size
curl "http://localhost:8080/api/qrcode?contents=test&size=400"
# Response: {"error": "Image size must be between 150 and 350 pixels"}

# Invalid correction level
curl "http://localhost:8080/api/qrcode?contents=test&correction=S"
# Response: {"error": "Permitted error correction levels are L, M, Q, H"}

# Invalid type
curl "http://localhost:8080/api/qrcode?contents=test&type=bmp"
# Response: {"error": "Only png, jpeg and gif image types are supported"}
```

#### Error Correction Levels

* **L (Low)**: ~7% error correction capability
* **M (Medium)**: ~15% error correction capability
* **Q (Quartile)**: ~25% error correction capability
* **H (High)**: ~30% error correction capability

Higher error correction levels make QR codes more resilient to damage but require more space, reducing data capacity.

## Configuration

The application uses Spring Boot's default configuration. You can customize the configuration by adding properties to the `application.properties` file or by using environment variables.

| Property                        | Description                            | Default Value | Usage                                 |
|---------------------------------|----------------------------------------|---------------|---------------------------------------|
| `server.port`                   | The port on which the application runs | 8080          | `server.port=9090`                    |
| `logging.level.com.rodrigotroy` | Log level for application packages     | INFO          | `logging.level.com.rodrigotroy=DEBUG` |

## Testing

The project includes unit tests for the controllers. To run the tests:

```bash
./gradlew test
```

### Running Specific Tests

To run a specific test class:

```bash
./gradlew test --tests "com.rodrigotroy.rest.qr.qrcode.controller.QrCodeControllerTest"
```

To run a specific test method:

```bash
./gradlew test --tests "com.rodrigotroy.rest.qr.qrcode.controller.QrCodeControllerTest.should return QR code information when generate endpoint is called"
```

## Deployment

The application can be deployed as a standalone JAR file:

1. Build the JAR file:
   ```bash
   ./gradlew bootJar
   ```

2. Run the JAR file:
   ```bash
   java -jar build/libs/qr-code-kt-0.0.1-SNAPSHOT.jar
   ```

For production deployments, consider using containerization with Docker or deploying to a cloud platform like Heroku, AWS, or Google Cloud.

## Dependencies

The project uses the following key dependencies:

* **Spring Boot Starter Web**: For building REST APIs
* **ZXing Core & JavaSE**: For QR code generation functionality
* **Jackson Module Kotlin**: For JSON serialization/deserialization
* **Kotlin Reflect**: For Kotlin reflection support

## Security

This project is a demonstration and does not include advanced security features. For production use, consider implementing:

* Authentication and authorization
* HTTPS
* Additional input validation and sanitization
* Rate limiting
* CORS configuration

If you discover a security vulnerability, please report it by creating an issue in the repository.
