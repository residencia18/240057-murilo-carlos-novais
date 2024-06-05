package com.example.atividades.atividade14;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class EventHandlerTest {

    private EmailService emailServiceMock;
    private EventHandler eventHandler;

    @Before
    public void setUp() {
        emailServiceMock = Mockito.mock(EmailService.class);
        eventHandler = new EventHandler(emailServiceMock);
    }

    @Test
    public void testHandleEvent() {
        String testEvent = "Test Event";
        
        eventHandler.handleEvent(testEvent);
        
        ArgumentCaptor<String> recipientCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
        
        verify(emailServiceMock).sendEmail(recipientCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture());
        
        assertEquals("test@example.com", recipientCaptor.getValue());
        assertEquals("Event Occurred", subjectCaptor.getValue());
        assertEquals(testEvent, bodyCaptor.getValue());
    }
}
