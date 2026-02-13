package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.model.Reservation;
import java.sql.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationModelTest {
    @Test
    void gettersAndSettersWork() {
        Reservation reservation = new Reservation();
        reservation.setId(3);
        reservation.setName("Ava");
        reservation.setAddress("Palm Street");
        reservation.setTelephone("555-2000");
        reservation.setNicOrPassport("NIC300");
        reservation.setRoomType(2);
        reservation.setCheckIn(Date.valueOf("2026-02-05"));
        reservation.setCheckOut(Date.valueOf("2026-02-06"));
        reservation.setStatus("Pending");

        assertEquals(3, reservation.getId());
        assertEquals("Ava", reservation.getName());
        assertEquals("Palm Street", reservation.getAddress());
        assertEquals("555-2000", reservation.getTelephone());
        assertEquals("NIC300", reservation.getNicOrPassport());
        assertEquals(2, reservation.getRoomType());
        assertEquals(Date.valueOf("2026-02-05"), reservation.getCheckIn());
        assertEquals(Date.valueOf("2026-02-06"), reservation.getCheckOut());
        assertEquals("Pending", reservation.getStatus());
    }
}
