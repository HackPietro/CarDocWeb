package com.example.backendcardoc.Persistence.DAO.Postgres;

import com.example.backendcardoc.Persistence.DAO.UtenteDao;
import com.example.backendcardoc.Persistence.Model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDaoPostgres implements UtenteDao {
    Connection connection;

    public UtenteDaoPostgres(Connection connection) { this.connection = connection; }

    public Utente createNewEntity(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setId(rs.getString("cf"));
        u.setNome(rs.getString("nome"));
        u.setCognome(rs.getString("cognome"));
        u.setEmail(rs.getString("email"));
        u.setTelefono(rs.getString("telefono"));
        u.setPassword(rs.getString("password"));
        u.setTipologia(rs.getString("tipologia"));
        return u;
    }

    @Override
    public Utente findByPrimaryKey(String cf) {
        String query = "select * from utenti where cf=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, cf);
            ResultSet rs = st.executeQuery();
            if (rs.next()) { return createNewEntity(rs); }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utente findByEmail(String email) {
        String query = "select * from utenti where email=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) { return createNewEntity(rs); }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Utente utente) {
        PreparedStatement st = null;
        try {
            Utente u = findByPrimaryKey(utente.getCf());
            if (u == null) {
                // User doesn't exist in the database, creating one
                String insertQuery = "insert into utenti(nome, cognome, email, telefono, password, tipologia, cf) values(?,?,?,?,?,?,?)";
                st = connection.prepareStatement(insertQuery);
            } else {
                // User exists in the database, updating its infos
                String updateQuery = "update utenti set nome = ?, cognome = ?, email = ?, telefono = ?, password = ?, tipologia = ? where cf = ?";
                st = connection.prepareStatement(updateQuery);
            }

            st.setString(1, utente.getNome().substring(0, 1).toUpperCase() + utente.getNome().substring(1).toLowerCase());
            st.setString(2, utente.getCognome().substring(0, 1).toUpperCase() + utente.getCognome().substring(1).toLowerCase());
            st.setString(3, utente.getEmail().toLowerCase());
            st.setString(4, utente.getTelefono());
            st.setString(5, utente.getPassword());
            st.setString(6, utente.getTipologia().toLowerCase());
            st.setString(7, utente.getCf().toUpperCase());

            st.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(Utente utente) {
        if (findByPrimaryKey(utente.getCf()) == null) return;
        String query = "delete from utenti where cf = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, utente.getCf());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recoveryNullCart(Utente utente){
        String updateQuery = "UPDATE carrello SET cf = ? WHERE cf = ?";
        try {
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, utente.getCf());
            updateStatement.setString(2, "null");
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserType(String cf, String type) {
        String updateQuery = "update utenti set tipologia = ? where cf = ?";
        try {
            PreparedStatement st = connection.prepareStatement(updateQuery);
            st.setString(1, type);
            st.setString(2, cf);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
