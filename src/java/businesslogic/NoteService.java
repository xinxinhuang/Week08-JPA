package businesslogic;

import dataaccess.NoteDB;
import domainmodel.Note;
import java.util.Date;
import java.util.List;

public class NoteService {

    private NoteDB noteDB;

    public NoteService() {
        noteDB = new NoteDB();
    }

    public Note get(int noteId) throws Exception {
        return noteDB.getNote(noteId);
    }

    public List<Note> getAll() throws Exception {
        return noteDB.getAll();
    }

    public int update(Note note) throws Exception {
        return noteDB.update(note);
    }

    public int delete(Note note) throws Exception {
        return noteDB.delete(note);
    }

    public int insert(String content) throws Exception {
        Note note = new Note(null, new Date(), content);
        return noteDB.insert(note);
    }
}
