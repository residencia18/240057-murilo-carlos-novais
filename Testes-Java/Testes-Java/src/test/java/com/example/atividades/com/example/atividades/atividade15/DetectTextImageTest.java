package com.example.atividades.atividade15;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DetectTextImageTest {

    @Mock
    private RekognitionClient rekognitionClientMock;

    private DetectTextImage detectTextImage;
    private final String testFilePath = "data/detected_text_results.txt";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        detectTextImage = new DetectTextImage("test-image.jpg", rekognitionClientMock);
    }

    @Test
    void testDetectTextLabels() throws IOException {
        // Mocking the response from RekognitionClient
        TextDetection textDetection1 = TextDetection.builder()
                .detectedText("Test Text 1")
                .confidence(99.0f)
                .id(1)
                .parentId(0)
                .type(TextTypes.LINE)
                .build();
        TextDetection textDetection2 = TextDetection.builder()
                .detectedText("Test Text 2")
                .confidence(98.0f)
                .id(2)
                .parentId(0)
                .type(TextTypes.LINE)
                .build();
        List<TextDetection> mockTextDetections = Arrays.asList(textDetection1, textDetection2);
        DetectTextResponse mockResponse = DetectTextResponse.builder()
                .textDetections(mockTextDetections)
                .build();

        when(rekognitionClientMock.detectText(any(DetectTextRequest.class))).thenReturn(mockResponse);

        // Call the method to test
        detectTextImage.detectTextLabels(testFilePath);

        // Verify that detectText was called once
        verify(rekognitionClientMock, times(1)).detectText(any(DetectTextRequest.class));

        // Verify the content of the result file
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            assertEquals("Detected: Test Text 1", reader.readLine());
            assertEquals("Confidence: 99.0", reader.readLine());
            assertEquals("Id: 1", reader.readLine());
            assertEquals("Parent Id: 0", reader.readLine());
            assertEquals("Type: LINE", reader.readLine());
            reader.readLine(); // skip empty line
            assertEquals("Detected: Test Text 2", reader.readLine());
            assertEquals("Confidence: 98.0", reader.readLine());
            assertEquals("Id: 2", reader.readLine());
            assertEquals("Parent Id: 0", reader.readLine());
            assertEquals("Type: LINE", reader.readLine());
        }
    }
}
