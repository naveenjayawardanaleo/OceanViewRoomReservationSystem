package com.mycompany.oceanviewresort.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddItemServletTest {
    @Test
    void doPostDoesNotThrow() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("name")).thenReturn("Item");
        when(req.getParameter("shortdescription")).thenReturn("Short");

        assertDoesNotThrow(() -> new AddItemServlet().doPost(req, res));
    }
}
