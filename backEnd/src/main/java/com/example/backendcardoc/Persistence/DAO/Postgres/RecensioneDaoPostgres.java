package com.example.backendcardoc.Persistence.DAO.Postgres;

import com.example.backendcardoc.Persistence.DAO.RecensioneDao;
import com.example.backendcardoc.Persistence.Model.Recensione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDaoPostgres implements RecensioneDao {

    Connection connection;

    public RecensioneDaoPostgres(Connection connection) {
        this.connection = connection;
    }

    public Recensione createNewRecensione(ResultSet rs) throws SQLException {
        Recensione r = new Recensione();
        r.setId(rs.getInt("id"));
        r.setUtente(rs.getString("utente"));
        r.setContenuto(rs.getString("contenuto"));
        r.setProdotto(rs.getString("prodotto"));
        r.setRating(rs.getShort("rating"));
        return r;
    }


    @Override
    public Recensione findByPrimaryKey(int id) {
        String query = "select * from recensioni where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) { return createNewRecensione(rs); }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Recensione> findByProduct(String idProdotto) {
        List<Recensione> recensioni = new ArrayList<>();
        String query = "select * from recensioni where prodotto = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, idProdotto);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { recensioni.add(createNewRecensione(rs)); }
            return recensioni;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Recensione recensione) {
        Recensione existingRecensione = null;
        if (recensione.getId() != 0) {
            existingRecensione = findByPrimaryKey(recensione.getId());
        } else {
            int tmpID = getMaxReviewId();
            // Se la recensione non esiste, esegui un'insert
            String insertQuery = "INSERT INTO recensioni (utente, contenuto, prodotto, rating, id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, recensione.getUtente());
                preparedStatement.setString(2, recensione.getContenuto());
                preparedStatement.setString(3, recensione.getProdotto());
                preparedStatement.setInt(4, recensione.getRating());
                preparedStatement.setInt(5, tmpID+1);

                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (existingRecensione != null) {
            // Se la recensione esiste, esegui un update
            String updateQuery = "UPDATE recensioni SET contenuto = ?, utente = ?, prodotto = ?, rating = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, recensione.getUtente());
                preparedStatement.setString(2, recensione.getContenuto());
                preparedStatement.setString(3, recensione.getProdotto());
                preparedStatement.setInt(4, recensione.getRating());
                preparedStatement.setInt(5, recensione.getId());

                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void delete(Recensione recensione) {
        String query = "delete from recensioni where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, recensione.getId());
            st.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }



    public int getMaxReviewId() {
        String query = "SELECT MAX(id) AS maxId FROM recensioni";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("maxId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
