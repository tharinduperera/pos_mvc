package dao;

import entity.Item;
import java.util.List;

public interface ItemDAO {

    public List<Item> getAllItems();

    public Item findItem(String code);

    public boolean saveItem(Item item);

    public boolean updateItem(Item item);

    public boolean deleteItem(String code);

    public String getLastItemCode();
}
