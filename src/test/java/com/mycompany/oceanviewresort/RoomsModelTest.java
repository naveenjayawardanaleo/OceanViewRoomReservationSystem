package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.model.Rooms;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomsModelTest {
    @Test
    void gettersAndSettersWork() {
        Rooms room = new Rooms();
        room.setId(1);
        room.setName("Ocean Suite");
        room.setCapacity(2);
        room.setRoom_type(3);
        room.setWifi(1);
        room.setAircon(1);
        room.setHotwater(1);
        room.setShortDescription("Sea view");
        room.setPricePerNight(220.5);

        assertEquals(1, room.getId());
        assertEquals("Ocean Suite", room.getName());
        assertEquals(2, room.getCapacity());
        assertEquals(3, room.getRoom_type());
        assertEquals(1, room.getWifi());
        assertEquals(1, room.getAircon());
        assertEquals(1, room.getHotwater());
        assertEquals("Sea view", room.getShortDescription());
        assertEquals(220.5, room.getPricePerNight());
    }
}
