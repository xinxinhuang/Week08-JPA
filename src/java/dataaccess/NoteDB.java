/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import domainmodel.Note;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 727153
 */
public class NoteDB {
    public int insert(String contents, java.sql.Date dateCreated) throws NotesDBException
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        
        try {
            String preparedQuery = "INSERT INTO Note(noteId, dateCreated, contents) VALUES (null, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setDate(1, dateCreated);
            ps.setString(2, contents);
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot insert note", ex);
            throw new NotesDBException("Error inserting note");
        } finally {
            pool.freeConnection(connection);
        }
    }
    
    public int update(int noteId, String contents) throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try {
            String preparedSQL = "UPDATE Note SET "
                    + "contents = ? "
                    + "WHERE noteId = ?";

            PreparedStatement ps = connection.prepareStatement(preparedSQL);

            ps.setString(1, contents);
            ps.setInt(2, noteId);

            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot update note", ex);
            throw new NotesDBException("Error updating note");
        } finally {
            pool.freeConnection(connection);
        }
    }
    
    public List<Note> getAll() throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM note;");
            rs = ps.executeQuery();
            List<Note> notes = new ArrayList<>();
            while (rs.next()) {
                notes.add(new Note(rs.getInt("noteId"), rs.getTimestamp("dateCreated"), rs.getString("contents")));
            }
            pool.freeConnection(connection);
            return notes;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting Notes");
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
            }
            pool.freeConnection(connection);
        }
    }
    
    public Note getNote(int noteId) throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String selectSQL = "SELECT * FROM Note WHERE noteId = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(selectSQL);
            ps.setInt(1, noteId);
            rs = ps.executeQuery();

            Note note = null;
            while (rs.next()) {
                note = new Note(rs.getInt("noteId"), rs.getTimestamp("dateCreated"), rs.getString("contents"));
            }
            pool.freeConnection(connection);
            return note;
        } catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting Notes");
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
            }
            pool.freeConnection(connection);
        }
    }
    
    public int delete(int noteId) throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String preparedQuery = "DELETE FROM Note WHERE noteId = ?";
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement(preparedQuery);
            ps.setInt(1, noteId);
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot delete note", ex);
            throw new NotesDBException("Error deleting Note");
        } finally {
            pool.freeConnection(connection);
        }
    }
}
