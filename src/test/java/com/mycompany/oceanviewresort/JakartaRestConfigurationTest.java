package com.mycompany.oceanviewresort;

import javax.ws.rs.ApplicationPath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JakartaRestConfigurationTest {
    @Test
    void hasApplicationPathAnnotation() {
        ApplicationPath path = JakartaRestConfiguration.class.getAnnotation(ApplicationPath.class);

        assertNotNull(path);
        assertEquals("resources", path.value());
    }
}
