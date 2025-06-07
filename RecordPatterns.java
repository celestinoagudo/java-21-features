public class RecordPatterns {

    public static void main(String[] args) {
        print("This is a string!");
        print(new Position(1, 2));
        print(new Path(new Position(3, 4), new Position(5, 6)));
    }

    public static void print(final Object object) {
        switch (object) {
            case Position(final int x, final int y) -> System.out.printf("object is a position: %d/%d%n", x, y);
            case String string -> System.out.printf("object is a string: %string%n", string);
            case Path(final Position from, final Position to) -> System.out.printf("object is a path: %d/%d -> %d/%d%n",
                    from.x(), from.y(), to.x(), to.y());
            default -> System.out.printf("object is something else: %string%n", object);
        }
    }

    record Position(int x, int y) {
    }

    record Path(Position from, Position to) {
    }
}
