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

    public int update(int noteId, String contents) throws Exception {
        return noteDB.update(noteId, contents);
    }

    public int delete(int noteId) throws Exception {
        return noteDB.delete(noteId);
    }

    public int insert(String contents) throws Exception {
        return noteDB.insert(contents, new java.sql.Date((new Date()).getTime()));
    }
}
