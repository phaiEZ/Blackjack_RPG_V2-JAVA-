import java.util.ArrayList;
import java.util.Random;

public class mapGen {

    private int GRID_WIDTH = 11;
    private int GRID_HEIGHT = 11;
    private int NORTH = 0;
    private int EAST = 1;
    private int SOUTH = 2;
    private int WEST = 3;
    private int r;
    private int temp;
    private StringBuilder grid = new StringBuilder("");
    int[] dirs = {NORTH, EAST, SOUTH, WEST};
    private void ResetGrid() {
        for (int i = 0; i < GRID_WIDTH * GRID_HEIGHT; ++i) {
            // grid.setCharAt(i, '*');
            grid.append("*");

        }
    }

    private int XYToIndex(int x, int y) {
        // Converts the two-dimensional index pair (x,y) into a
        // single-dimensional index. The result is y * ROW_WIDTH + x.
        return y * GRID_WIDTH + x;
    }

    private boolean IsInBounds(int x, int y) {
        // Returns "true" if x and y are both in-bounds.
        if (x < 0 || x >= GRID_WIDTH)
            return false;
        if (y < 0 || y >= GRID_HEIGHT)
            return false;
        return true;
    }

    private void Visit(int x, int y){
    // Starting at the given index, recursively visits every direction in a
    // randomized order.
    // Set my current location to be an empty passage.
    grid.setCharAt(XYToIndex(x, y), ' ');
    // Create an local array containing the 4 directions and shuffle their order.
    dirs[0] = NORTH;
    dirs[1] = EAST;
    dirs[2] = SOUTH;
    dirs[3] = WEST;
    for (int i = 0; i < 4; ++i)
    {
        int r = (int)(Math.random() * 3);
        int temp = dirs[r];
        dirs[r] = dirs[i];
        dirs[i] = temp;
    }
    // Loop through every direction and attempt to Visit that direction.
    for (int i = 0; i < 4; ++i)
    {
        // dx,dy are offsets from current location. Set them based
        // on the next direction I wish to try.
        int dx = 0, dy = 0;
        switch (dirs[i])
        {
        case 0:
            dy = -1;
            break;
        case 1:
            dy = 1;
            break;
        case 2:
            dx = 1;
            break;
        case 3:
            dx = -1;
            break;
        }
        // Find the (x,y) coordinates of the grid cell 2 spots
        // away in the given direction.
        int x2 = x + (dx << 1);
        int y2 = y + (dy << 1);
        if (IsInBounds(x2, y2))
        {
            if (grid.charAt(XYToIndex(x2, y2)) == '*')
            {
                // (x2,y2) has not been visited yet... knock down the
                // wall between my current position and that position
                grid.setCharAt(XYToIndex(x2 - dx, y2 - dy), ' ');
                // Recursively Visit (x2,y2)
                Visit(x2, y2);
            }
        }
    }
}

public String getGrid(){
    ResetGrid();
    Visit(1, 1);
    String str = grid.substring(0);
    return (str);
}

}
