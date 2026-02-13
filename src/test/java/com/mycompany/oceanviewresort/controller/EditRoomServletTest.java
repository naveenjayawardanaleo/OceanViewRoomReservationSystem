package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Rooms;
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

class EditRoomServletTest {
    @Test
    void doGetLoadsRoomAndForwards() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getParameter("id")).thenReturn("5");
        when(req.getRequestDispatcher("editRoom.jsp")).thenReturn(dispatcher);

        Rooms room = new Rooms();
        room.setId(5);
        room.setName("Cozy Room");

        try (MockedConstruction<RoomsDAO> mocked = Mockito.mockConstruction(
                RoomsDAO.class,
                (mock, context) -> when(mock.getRoomById(5)).thenReturn(room)
        )) {
            new EditRoomServlet().doGet(req, res);

            verify(req).setAttribute("room", room);
            verify(dispatcher).forward(req, res);
        }
    }

    @Test
    void doPostUpdatesRoomAndRedirects() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("id")).thenReturn("8");
        when(req.getParameter("name")).thenReturn("Updated");
        when(req.getParameter("shortDescription")).thenReturn("New desc");
        when(req.getParameter("capacity")).thenReturn("3");
        when(req.getParameter("typeofroom")).thenReturn("2");
        when(req.getParameter("pricePerNight")).thenReturn("150.0");
        when(req.getParameter("wifi")).thenReturn("on");
        when(req.getParameter("Aircon")).thenReturn(null);
        when(req.getParameter("Hotwater")).thenReturn("on");

        try (MockedConstruction<RoomsDAO> mocked = Mockito.mockConstruction(RoomsDAO.class)) {
            new EditRoomServlet().doPost(req, res);

            RoomsDAO dao = mocked.constructed().get(0);
            ArgumentCaptor<Rooms> captor = ArgumentCaptor.forClass(Rooms.class);
            verify(dao).updateRoom(captor.capture());

            Rooms room = captor.getValue();
            assertEquals(8, room.getId());
            assertEquals("Updated", room.getName());
            assertEquals("New desc", room.getShortDescription());
            assertEquals(3, room.getCapacity());
            assertEquals(2, room.getRoom_type());
            assertEquals(150.0, room.getPricePerNight());
            assertEquals(1, room.getWifi());
            assertEquals(0, room.getAircon());
            assertEquals(1, room.getHotwater());

            verify(res).sendRedirect("viewRoom");
        }
    }
}
