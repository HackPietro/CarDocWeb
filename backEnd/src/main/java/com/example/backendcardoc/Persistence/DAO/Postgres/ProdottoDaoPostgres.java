package com.example.backendcardoc.Persistence.DAO.Postgres;

import com.example.backendcardoc.Persistence.DAO.ProdottoDao;
import com.example.backendcardoc.Persistence.Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDaoPostgres implements ProdottoDao {

    Connection connection;

    public ProdottoDaoPostgres(Connection connection) { this.connection = connection; }

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
    public List<Prodotto> findAll() {
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        String query = "select * from prodotti";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { prodotti.add(createNewProduct(rs)); }
            return prodotti;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
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
    public List<Prodotto> findCategoryProduct(String category) {
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        String query = "select * from prodotti where categoria=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,category);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { prodotti.add(createNewProduct(rs)); }
            return prodotti;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Prodotto> findSearchedProduct(String searchedWord) {
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        String query = "select * from prodotti where lower(nome) like lower(?) or lower(venditore) like lower(?) or lower(descrizione) like lower(?) or lower(categoria) like lower(?)";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, "%" + searchedWord.toLowerCase() + "%");
            st.setString(2, "%" + searchedWord.toLowerCase() + "%");
            st.setString(3, "%" + searchedWord.toLowerCase() + "%");
            st.setString(4, "%" + searchedWord.toLowerCase() + "%");

            ResultSet rs = st.executeQuery();
            while(rs.next()) { prodotti.add(createNewProduct(rs)); }
            return prodotti;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Prodotto prodotto) {
        Prodotto existingProduct = findByPrimaryKey(prodotto.getId());
        if (existingProduct == null){
            // Se il prodotto non esiste, esegui un'insert

            double tmpPrice = prodotto.getPrezzo();
            double price = Double.parseDouble(String.valueOf(tmpPrice));

            String insertQuery = "INSERT INTO prodotti (id, nome, venditore, descrizione, categoria, prezzo, numerovenduti) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, prodotto.getId());
                preparedStatement.setString(2, prodotto.getNome());
                preparedStatement.setString(3, prodotto.getVenditore());
                preparedStatement.setString(4, prodotto.getDescrizione());
                preparedStatement.setString(5, prodotto.getCategoria());
                preparedStatement.setDouble(6, price);
                preparedStatement.setInt(7, 0);

                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (existingProduct != null) {
            // Se la prodotto esiste, esegui un update
            String updateQuery = "UPDATE prodotti SET nome = ?, venditore = ?, descrizione = ?, categoria = ?, prezzo = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, prodotto.getNome());
                preparedStatement.setString(2, prodotto.getVenditore());
                preparedStatement.setString(3, prodotto.getDescrizione());
                preparedStatement.setString(4, prodotto.getCategoria());
                preparedStatement.setDouble(5, prodotto.getPrezzo());
                preparedStatement.setString(6, prodotto.getId());

                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    @Override
    public void deleteProduct(Prodotto prodotto) {
        String query = "delete from prodotti where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, prodotto.getId());
            st.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToCart(Cart cart){
        boolean cond = findProductInCart(cart.getCf(), cart.getIdProdotto());
        if (!cond){
            String insertQuery = "INSERT INTO carrello (id_prodotto, cf, quantity, prezzo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, cart.getIdProdotto());
                preparedStatement.setString(2, cart.getCf());
                preparedStatement.setInt(3, cart.getQuantity());
                preparedStatement.setDouble(4, cart.getPrezzo());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{

            String updateQuery = "UPDATE carrello SET quantity = quantity + ? WHERE cf=? AND id_prodotto=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, cart.getQuantity());
                preparedStatement.setString(2, cart.getCf());
                preparedStatement.setString(3, cart.getIdProdotto());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean findProductInCart(String cf, String idProdotto){
        String query = "select * from carrello where cf=? and id_prodotto=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, cf);
            st.setString(2, idProdotto);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void addToWishlist(Wishlist wishlist) {
        boolean cond = findProductInWishlist(wishlist.getCf(), wishlist.getIdProdotto());
        if (!cond) {
            String insertQuery = "INSERT INTO wishlist (id_prodotto, cf, prezzo) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, wishlist.getIdProdotto());
                preparedStatement.setString(2, wishlist.getCf());
                preparedStatement.setDouble(3, wishlist.getPrezzo());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Non eseguire alcuna operazione se il prodotto è già nella wishlist
        }
    }

    @Override
    public boolean findProductInWishlist(String cf, String idProdotto){
        String query = "select * from wishlist where cf=? and id_prodotto=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, cf);
            st.setString(2, idProdotto);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
