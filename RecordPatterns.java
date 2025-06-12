public class RecordPatterns {

    public static void main(String[] args) {
        print("This is a string!");
        print(new Position2D(1, 2));
        print(new Path(new Position2D(3, 4), new Position3D(5, 6, 10)));
    }

    public static void print(final Object object) {
        switch (object) {
            case String string when string.length() > 5 -> System.out.printf("object is a string: %string%n", string);
            case Path(Position2D from, Position2D to) -> System.out.printf("object is a 2D path: %d/%d -> %d/%d%n",
                    from.x(), from.y(), to.x(), to.y());
            case Path(Position3D from, Position3D to) ->
                    System.out.printf("object is a 3D path: %d/%d/%d -> %d/%d/%d%n",
                            from.x(), from.y(), from.z(), to.x(), to.y(), to.z());
            case Path(Position2D from, Position3D to) -> System.out.printf("object is a 3D path: %d/%d -> %d/%d/%d%n",
                    from.x(), from.y(), to.x(), to.y(), to.z());
            default -> System.out.printf("object is something else: %string%n", object);
        }
    }

    record Path(Position from, Position to) {
    }

    sealed interface Position permits Position2D, Position3D {

    }

    record Position2D(int x, int y) implements Position {

    }

    record Position3D(int x, int y, int z) implements Position {

    }


}
