package entity;

public enum Direction
{
    Up,
    Down,
    Left,
    Right;

    public static Direction invert(Direction source)
    {
        return switch (source)
        {
            case Left -> Direction.Right;
            case Right -> Direction.Left;
            case Up -> Direction.Down;
            case Down -> Direction.Up;
        };
    }
}
