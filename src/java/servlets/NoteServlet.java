package servlets;

import businesslogic.NoteService;
import businesslogic.UserService;
import domainmodel.Note;
import domainmodel.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        NoteService ns = new NoteService();
        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            int selectedNoteId = Integer.parseInt(request.getParameter("selectedNoteId"));
            try {
                Note note = ns.get(selectedNoteId);
                request.setAttribute("selectedNote", note);
            } catch (Exception ex) {
                Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ArrayList<Note> notes = null;        
        try {
            notes = (ArrayList<Note>) ns.getAll();
        } catch (Exception ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("notes", notes);
        getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        
        
        String strSelectedNoteId = request.getParameter("selectedNoteId");
        int selectedNoteId;
        if (strSelectedNoteId == null || strSelectedNoteId.isEmpty())
        {
            selectedNoteId = 0;
        }
        else
        {
            selectedNoteId = Integer.parseInt(strSelectedNoteId);
        }
        
        
        int active = request.getParameter("active") != null ? 1 : 0;

        NoteService ns = new NoteService();

        String contents = request.getParameter("contents");
        
        try {
            if (action.equals("delete")) {
                Note note = ns.get(selectedNoteId);
                ns.delete(note);
            } else if (action.equals("edit")) {
                Note note = ns.get(selectedNoteId);
                note.setContents(contents);
                ns.update(note);
            } else if (action.equals("add")) {
                ns.insert(contents);
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Whoops.  Could not perform that action.");
        }
        
        ArrayList<Note> notes = null;
        try {
            notes = (ArrayList<Note>) ns.getAll();
        } catch (Exception ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("notes", notes);
        getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
    }
}
