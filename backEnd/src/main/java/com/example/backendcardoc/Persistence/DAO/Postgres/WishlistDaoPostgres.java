package com.example.backendcardoc.Persistence.DAO.Postgres;

import com.example.backendcardoc.Persistence.Model.Prodotto;
import com.example.backendcardoc.Persistence.Model.Wishlist;
import com.example.backendcardoc.Persistence.DAO.WishlistDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistDaoPostgres implements WishlistDao{

    Connection connection;

    public WishlistDaoPostgres(Connection connection) { this.connection = connection; }

    public Prodotto createNewProduct(ResultSet rs) throws SQLException {
        Prodotto p = new Prodotto();
        p.setId(rs.getString("id"));
        p.setNome(rs.getString("nome"));
        p.setVenditore(rs.getString("venditore"));
        p.setDescrizione(rs.getString("descrizione"));
        p.setCategoria(rs.getString("categoria"));
        p.setPrezzo(rs.getDouble("prezzo"));
        p.setNumeroVenduti(rs.getInt("numeroVenduti"));
        return p;
    }

    @Override
    public List<Prodotto> findByCf(String cf) {
        List<Prodotto> products = new ArrayList<>();
        String query = "select * from wishlist where cf=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,cf);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                Prodotto p = findByPrimaryKey(rs.getString("id_prodotto"));
                products.add(p);
            }
            return products;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Prodotto findByPrimaryKey(String id) {
        String query = "select * from prodotti where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return createNewProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removewithid_prodotto(String cf, String id_prodotto) {
        String query = "DELETE FROM wishlist WHERE cf = ? AND id_prodotto = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, cf);
            st.setString(2, id_prodotto);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll(String cf) {
        String query = "DELETE FROM wishlist WHERE cf=?";
        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,cf);
            st.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Wishlist> getWishlistByCf(String cf) {
        List<Wishlist> wishlist = new ArrayList<>();
        String query = "select * from wishlist where cf=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,cf);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String idProdotto = rs.getString("id_prodotto");
                Double prezzo = rs.getDouble("prezzo");
                Wishlist w = new Wishlist(cf, idProdotto, prezzo);
                wishlist.add(w);
            }
            return wishlist;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}