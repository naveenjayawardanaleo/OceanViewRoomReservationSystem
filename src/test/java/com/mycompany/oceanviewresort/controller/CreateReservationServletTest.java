package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.ReservationDAO;
import com.mycompany.oceanviewresort.model.Reservation;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateReservationServletTest {
    @Test
    void doPostCreatesReservationAndRedirects() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("name")).thenReturn("Alex");
        when(req.getParameter("address")).thenReturn("Beach Road");
        when(req.getParameter("telephone")).thenReturn("555-1234");
        when(req.getParameter("nicOrPassport")).thenReturn("NIC555");
        when(req.getParameter("roomType")).thenReturn("2");
        when(req.getParameter("checkIn")).thenReturn("2026-02-01");
        when(req.getParameter("checkOut")).thenReturn("2026-02-03");

        try (MockedConstruction<ReservationDAO> mocked = Mockito.mockConstruction(
                ReservationDAO.class,
                (mock, context) -> when(mock.addReservation(any())).thenReturn(true)
        )) {
            new CreateReservationServlet().doPost(req, res);

            ReservationDAO dao = mocked.constructed().get(0);
            ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
            verify(dao).addReservation(captor.capture());

            Reservation saved = captor.getValue();
            assertEquals("Alex", saved.getName());
            assertEquals("Beach Road", saved.getAddress());
            assertEquals("555-1234", saved.getTelephone());
            assertEquals("NIC555", saved.getNicOrPassport());
            assertEquals(2, saved.getRoomType());
            assertEquals(Date.valueOf("2026-02-01"), saved.getCheckIn());
            assertEquals(Date.valueOf("2026-02-03"), saved.getCheckOut());

            verify(res).sendRedirect("viewReservation.jsp");
        }
    }
}
