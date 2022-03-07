public class Grid {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private final Piece[][] grid = new Piece[HEIGHT][WIDTH];

    public Grid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = Piece.WATER;
            }
        }
    }

    public boolean canPlace(Coordinate c, int size, boolean isDown) {
        if (isDown) {
            for (int i = 0; i < size; i++) {
                if (c.getRow() + i >= HEIGHT || c.getColumn() >= WIDTH || grid[c.getRow() + i][c.getColumn()] != Piece.WATER) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (c.getRow() >= HEIGHT || c.getColumn() + i >= WIDTH || grid[c.getRow()][c.getColumn() + i] != Piece.WATER) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeShip(Coordinate c, int size, boolean isDown) {
        assert canPlace(c, size, isDown);
        if (isDown) {
            for (int i = 0; i < size; i++) {
                grid[c.getRow() + i][c.getColumn()] = Piece.SHIP;
            }
        } else {
            for (int i = 0; i < size; i++) {
                grid[c.getRow()][c.getColumn() + i] = Piece.SHIP;
            }
        }
    }

    public boolean wouldAttackSucceed(Coordinate c) {
        return grid[c.getRow()][c.getColumn()] == Piece.SHIP;
    }

    public void attackCell(Coordinate c) {
        if (wouldAttackSucceed(c)) {
            grid[c.getRow()][c.getColumn()] = Piece.DAMAGED_SHIP;
        } else if (grid[c.getRow()][c.getColumn()] == Piece.WATER) {
            grid[c.getRow()][c.getColumn()] = Piece.MISS;
        }
    }

    public boolean areAllSunk() {
        for (Piece[] row : grid) {
            for (Piece piece : row) {
                if (piece == Piece.SHIP) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toPlayerString() {
        Piece[][] playerGrid = Util.deepClone(grid);
        Util.hideShips(playerGrid);
        return renderGrid(playerGrid);
    }

    @Override
    public String toString() {
        return renderGrid(grid);
    }

    private static String renderGrid(Piece[][] grid) {
        StringBuilder sb = new StringBuilder();
        sb.append(" 0123456789\n");
        for (int i = 0; i < grid.length; i++) {
            sb.append((char) ('A' + i));
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == null) {
                    return "!";
                }
                switch (grid[i][j]) {
                case SHIP:
                    sb.append('#');
                    break;
                case DAMAGED_SHIP:
                    sb.append('*');
                    break;
                case MISS:
                    sb.append('o');
                    break;
                case WATER:
                    sb.append('.');
                    break;
                }

            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }
}
