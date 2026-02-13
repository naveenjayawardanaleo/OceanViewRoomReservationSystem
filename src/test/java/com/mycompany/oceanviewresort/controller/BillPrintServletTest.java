package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.ReservationDAO;
import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Reservation;
import com.mycompany.oceanviewresort.model.Rooms;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BillPrintServletTest {
    @Test
    void doGetForwardsWhenReservationAndRoomFound() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getParameter("id")).thenReturn("7");
        when(req.getRequestDispatcher("bill.jsp")).thenReturn(dispatcher);

        Reservation reservation = new Reservation();
        reservation.setId(7);
        reservation.setRoomType(2);
        reservation.setCheckIn(Date.valueOf("2026-02-01"));
        reservation.setCheckOut(Date.valueOf("2026-02-04"));

        Rooms room = new Rooms();
        room.setRoom_type(2);
        room.setPricePerNight(100.0);

        try (MockedConstruction<ReservationDAO> rdao = Mockito.mockConstruction(
                ReservationDAO.class,
                (mock, context) -> when(mock.getReservationById(7)).thenReturn(reservation)
        ); MockedConstruction<RoomsDAO> roomDao = Mockito.mockConstruction(
                RoomsDAO.class,
                (mock, context) -> when(mock.getRoomByType(2)).thenReturn(room)
        )) {
            new BillPrintServlet().doGet(req, res);

            verify(req).setAttribute("reservation", reservation);
            verify(req).setAttribute("room", room);
            verify(req).setAttribute("invoiceNumber", "INV-7");

            ArgumentCaptor<Long> nightsCaptor = ArgumentCaptor.forClass(Long.class);
            verify(req).setAttribute(Mockito.eq("nights"), nightsCaptor.capture());
            assertEquals(3L, nightsCaptor.getValue());

            ArgumentCaptor<Double> subtotalCaptor = ArgumentCaptor.forClass(Double.class);
            verify(req).setAttribute(Mockito.eq("subtotal"), subtotalCaptor.capture());
            assertEquals(300.0, subtotalCaptor.getValue(), 0.0001);

            ArgumentCaptor<Double> serviceCaptor = ArgumentCaptor.forClass(Double.class);
            verify(req).setAttribute(Mockito.eq("serviceCharge"), serviceCaptor.capture());
            assertEquals(30.0, serviceCaptor.getValue(), 0.0001);

            ArgumentCaptor<Double> vatCaptor = ArgumentCaptor.forClass(Double.class);
            verify(req).setAttribute(Mockito.eq("vat"), vatCaptor.capture());
            assertEquals(59.4, vatCaptor.getValue(), 0.0001);

            ArgumentCaptor<Double> totalCaptor = ArgumentCaptor.forClass(Double.class);
            verify(req).setAttribute(Mockito.eq("total"), totalCaptor.capture());
            assertEquals(389.4, totalCaptor.getValue(), 0.0001);

            verify(dispatcher).forward(req, res);
        }
    }

    @Test
    void doGetRedirectsWhenIdMissing() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("id")).thenReturn(null);

        new BillPrintServlet().doGet(req, res);

        verify(res).sendRedirect("viewReservations");
    }
}
