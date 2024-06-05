package com.example.atividades.atividade10;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {

    private UserService mockUserService;
    private UserManager userManager;

    @Before
    public void setUp() {
        mockUserService = Mockito.mock(UserService.class);
        userManager = new UserManager(mockUserService);
    }

    @Test
    public void testFetchUserInfo_success() {
        User expectedUser = new User("John Doe", "john.doe@example.com");
        when(mockUserService.getUserInfo(1)).thenReturn(expectedUser);

        User actualUser = userManager.fetchUserInfo(1);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getName(), actualUser.getName());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test(expected = RuntimeException.class)
    public void testFetchUserInfo_userNotFound() {
        when(mockUserService.getUserInfo(1)).thenReturn(null);

        userManager.fetchUserInfo(1);
    }

    @Test
    public void testFetchUserInfo_verifyServiceCall() {
        User expectedUser = new User("Jane Doe", "jane.doe@example.com");
        when(mockUserService.getUserInfo(2)).thenReturn(expectedUser);

        userManager.fetchUserInfo(2);

        verify(mockUserService, times(1)).getUserInfo(2);
    }
}
