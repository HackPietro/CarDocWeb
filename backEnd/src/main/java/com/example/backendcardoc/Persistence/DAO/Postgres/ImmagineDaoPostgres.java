package com.example.backendcardoc.Persistence.DAO.Postgres;

import com.example.backendcardoc.Persistence.DAO.ImmagineDao;
import com.example.backendcardoc.Persistence.Model.Immagine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImmagineDaoPostgres implements ImmagineDao {

    Connection connection;
    public ImmagineDaoPostgres(Connection connection) { //crea la connessione con il DB
        this.connection = connection;
    }

    public Immagine createNewImage(ResultSet rs) throws SQLException {
        Immagine i = new Immagine();
        i.setId_prodotto(rs.getString("id_prodotto"));

        i.setImg(rs.getString("img"));
        i.setId(rs.getInt("id"));
        return i;
    }

    @Override
    public Immagine findByPrimaryKey(Integer id) {
        String query = "select * from immagini where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) { return createNewImage(rs); }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Immagine findById_prodotto(String id_prodotto) {
        String query = "select * from immagini where id_prodotto=?";
        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,id_prodotto);
            ResultSet rs = st.executeQuery();
            if(rs.next()) { return createNewImage(rs); }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Immagine image) {
        try {
            int tmpID = getMaxImgId();
            String insertQuery = "insert into immagini(id_prodotto, img, id) values(?,?,?)";
            PreparedStatement st = connection.prepareStatement(insertQuery);
            st.setString(1, image.getId_prodotto());
            st.setString(2, image.getImg());
            st.setInt(3, tmpID+1);

            st.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int getMaxImgId() {
        String query = "SELECT MAX(id) AS maxId FROM immagini";

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