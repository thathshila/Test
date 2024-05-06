package lk.ijse.repository;

import javafx.scene.control.Alert;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Item;
import lk.ijse.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {

    public static void saveItem(String itemId, String name, int qty, double price, Date date, String description) throws SQLException {
        String sql = "INSERT INTO Items VALUES (?, ? ,? ,? ,? ,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, itemId);
        pstm.setObject(2, name);
        pstm.setObject(3, qty);
        pstm.setObject(4, price);
        pstm.setObject(5, description);
        pstm.setObject(6, date);

        int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this item").show();
        }
    }

    public static Item searchItem(String id) throws SQLException {
        String sql = "SELECT * FROM Items WHERE Item_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String Item_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            int qty = Integer.parseInt(resultSet.getString(3));
            double price = Double.parseDouble(resultSet.getString(4));
            String description = resultSet.getString(5);
            Date date = resultSet.getDate(6);

            Item item = new Item(Item_id, name, qty, price, description, date);
            return item;
        }
        return null;
    }

    public static boolean UPDATE(Item item) throws SQLException {
       String sql = "UPDATE Items SET Item_name = ?, Quantity = ?,Price = ? , Description= ? , Date = ? WHERE Item_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, item.getName());
        pstm.setObject(2, item.getQty());
        pstm.setObject(3, item.getPrice());
        pstm.setObject(4, item.getDescription());
        pstm.setObject(5, item.getDate());
        pstm.setObject(6, item.getItem_id());


        return pstm.executeUpdate() > 0;
    }
      /*  for (OrderItem od : odList) {
            boolean isUpdateQty = updateQty(od.getItem_id(), od.getQuantity());
            if (!isUpdateQty) {
                return false;
            }
        }
        return true;
    }*/

/*    private static updateQty(String Item_id, int Quantity) throws SQLException {
        String sql = "Update Items set Quantity = Quantity - ? where Item_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, Quantity);
        pstm.setString(2, Item_id);

        return pstm.executeUpdate() > 0;
    }*/



    public static boolean DELETE(String ItemId) throws SQLException {
        String sql = "DELETE FROM Items WHERE Item_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,ItemId);

        return pstm.executeUpdate() > 0;

    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM Items";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Item> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String Item_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            int quantity = Integer.parseInt(resultSet.getString(3));
            double price = Double.parseDouble(resultSet.getString(4));
            String description = resultSet.getString(5);
            Date date = Date.valueOf(resultSet.getString(6));

            Item item = new Item(Item_id, name, quantity, price,description,date);
            itemList.add(item);
        }
        return itemList;
    }

    public static List<String> getItemId() throws SQLException {
        String sql = "SELECT Item_id FROM Items";

        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static boolean UpdateQty(OrderItem items) throws SQLException {
        String sql = "Update Items set Quantity = Quantity - ? where Item_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, items.getQuantity());
        pstm.setString(2, items.getItem_id());

        return pstm.executeUpdate() > 0;

    }

    public static boolean SaveItem(OrderItem items) throws SQLException {
        String sql = "INSERT INTO Order_Item VALUES(?, ?, ?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, items.getOrder_id());
        pstm.setObject(2, items.getItem_id());
        pstm.setObject(3, items.getQuantity());
        pstm.setObject(4, items.getPrice());



        return pstm.executeUpdate() > 0;
    }
    }



