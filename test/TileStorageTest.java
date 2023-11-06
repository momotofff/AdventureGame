import tile.Tile;
import tile.TileStorage;
import tile.Tiles;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TileStorageTest
{
    TileStorage tiles = new TileStorage();

    @org.junit.jupiter.api.Test
    void getAfterAdd_ExpectSuccess()
    {
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass1.png", false));
        Tile tile = tiles.get(Tiles.Grass);
        assertNotNull(tile);
        assertFalse(tile.collision);
    }

    @org.junit.jupiter.api.Test
    void getBeforeAdd_ExpectException()
    {
        assertThrows(NoSuchElementException.class, () -> tiles.get(Tiles.Grass));
    }

    @org.junit.jupiter.api.Test
    void getAnotherTile_ExpectException()
    {
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass1.png", false));
        assertThrows(NoSuchElementException.class, () -> tiles.get(Tiles.Water));
    }
}