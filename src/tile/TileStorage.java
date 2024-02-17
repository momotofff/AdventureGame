package tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TileStorage
{
    private final Map<Integer, ArrayList<Tile>> tiles = new HashMap<>();

    public void add(Integer id, Tile tile)
    {
        if (!tiles.containsKey(id))
            tiles.put(id, new ArrayList<>());

        tiles.get(id).add(tile);
    }

    public Tile get(Integer id) throws NoSuchElementException
    {
        ArrayList<Tile> tile = tiles.get(id);

        if (tile == null || tile.isEmpty())
            throw new NoSuchElementException("Failed to get tile with ID: " + id);

        return tile.get((int) (Math.random() * tile.size()));
    }
}
