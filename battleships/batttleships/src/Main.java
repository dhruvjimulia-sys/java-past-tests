import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Scanner input = new Scanner(System.in);
        Grid grid;
        if (args.length == 0) {
            grid = makeInitialGrid();
        } else if (args.length == 1 && args[0].equals("random")) {
            grid = makeRandomGrid();
        } else {
            throw new IllegalArgumentException("Illegal command line argument");
        }
        int attackAttempts = 0;

        do {
            System.out.println(grid.toPlayerString());
            System.out.println();
            System.out.print("Enter a coordinate to attack: ");
            final String inputString = input.nextLine();
            final Coordinate hitCoordinate = Util.parseCoordinate(inputString);
            grid.attackCell(hitCoordinate);
            if (grid.wouldAttackSucceed(hitCoordinate)) {
                System.out.println("Direct Hit!");
            }
            attackAttempts++;
        } while (!grid.areAllSunk());

        System.out.println("All sinks sunk!");
        System.out.println("Attack Attempts required: " + attackAttempts);
        System.out.println("Final grid:");
        System.out.println(grid);
    }

    private static Grid makeInitialGrid() {
        Grid grid = new Grid();

        String[] coords = { "A7", "B1", "B4", "D3", "F7", "H1", "H4" };
        int[] sizes = { 2, 4, 1, 3, 1, 2, 5 };
        boolean[] isDowns = { false, true, true, false, false, true, false };

        for (int i = 0; i < coords.length; i++) {
            Coordinate c = Util.parseCoordinate(coords[i]);
            grid.placeShip(c, sizes[i], isDowns[i]);
        }

        return grid;
    }

    private static Grid makeRandomGrid() {
        Grid grid = new Grid();
        final int[] sizes = { 2, 4, 1, 3, 1, 2, 5 };
        final Random random = new Random();
        for (int size : sizes) {
            Coordinate c;
            boolean isDown;
            do {
                final int x = random.nextInt(Grid.getHeight());
                final int y = random.nextInt(Grid.getWidth());
                c = new Coordinate(x, y);
                isDown = random.nextInt(2) == 1;
            } while (!grid.canPlace(c, size, isDown));
            grid.placeShip(c, size, isDown);
        }
        return grid;
    }
}
