package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.ReservationDAO;
import com.mycompany.oceanviewresort.model.Reservation;
import java.util.Collections;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ViewReservationServletTest {
    @Test
    void doGetSetsAttributesAndForwards() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher("viewReservation.jsp")).thenReturn(dispatcher);

        Reservation pending = new Reservation();
        pending.setId(1);
        Reservation completed = new Reservation();
        completed.setId(2);

        try (MockedConstruction<ReservationDAO> mocked = Mockito.mockConstruction(
                ReservationDAO.class,
                (mock, context) -> {
                    when(mock.getPendingReservations()).thenReturn(Collections.singletonList(pending));
                    when(mock.getCompletedReservations()).thenReturn(Collections.singletonList(completed));
                }
        )) {
            new ViewReservationServlet().doGet(req, res);

            verify(req).setAttribute("reservationList", Collections.singletonList(pending));
            verify(req).setAttribute("completedReservationList", Collections.singletonList(completed));
            verify(dispatcher).forward(req, res);
        }
    }

    @Test
    void doPostUpdatesStatusAndRedirects() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("id")).thenReturn("7");

        try (MockedConstruction<ReservationDAO> mocked = Mockito.mockConstruction(ReservationDAO.class)) {
            new ViewReservationServlet().doPost(req, res);

            ReservationDAO dao = mocked.constructed().get(0);
            verify(dao).updateReservationStatus(7, "Completed");
            verify(res).sendRedirect("viewReservations");
        }
    }

    @Test
    void doPostWithoutIdSkipsUpdate() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("id")).thenReturn(null);

        try (MockedConstruction<ReservationDAO> mocked = Mockito.mockConstruction(ReservationDAO.class)) {
            new ViewReservationServlet().doPost(req, res);

            ReservationDAO dao = mocked.constructed().get(0);
            verify(dao, never()).updateReservationStatus(Mockito.anyInt(), Mockito.anyString());
            verify(res).sendRedirect("viewReservations");
        }
    }
}
