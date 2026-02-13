package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.resources.JakartaEE8Resource;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JakartaEE8ResourceTest {
    @Test
    void pingReturnsOk() {
        Response response = new JakartaEE8Resource().ping();

        assertEquals(200, response.getStatus());
        assertEquals("ping", response.getEntity());
    }
}
