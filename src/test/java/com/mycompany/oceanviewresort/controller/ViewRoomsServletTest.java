package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Rooms;
import java.util.Collections;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ViewRoomsServletTest {
    @Test
    void doGetSetsAttributeAndForwards() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher("viewRooms.jsp")).thenReturn(dispatcher);

        Rooms room = new Rooms();
        room.setId(1);
        room.setName("Ocean Suite");

        try (MockedConstruction<RoomsDAO> mocked = Mockito.mockConstruction(
                RoomsDAO.class,
                (mock, context) -> when(mock.getAllRooms()).thenReturn(Collections.singletonList(room))
        )) {
            new ViewRoomsServlet().doGet(req, res);

            verify(req).setAttribute("RoomList", Collections.singletonList(room));
            verify(dispatcher).forward(req, res);
        }
    }
}
