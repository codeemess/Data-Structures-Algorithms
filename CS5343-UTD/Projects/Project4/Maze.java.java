package project4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * Maze Class uses The Disjoint Set data structure to create a
 * maze according to algorithm discussed in textbook chapter 8.
 * @author Fangzhou pan
 * Grade 100/100
 */
public class Maze {

	private int width;
    private int height;
    private int dsSize;
    private DisjSets dset;
    private Cell[][] myMaze;
    private static final int[] UP = {0,-1},DOWN = {0,1},LEFT = {-1,0},RIGHT = {1,0};
	private List<int[]> orientations;
    /**
     * Construct the maze Object.
     * @param w the number of total columns of the maze.
     * @param h the number of total rows of the maze.
     */
    public Maze(int h, int w){
    	this.width = w;
    	this.height = h;
    	this.dsSize = width * height;
        this.myMaze = new Cell[height][width];
        this.dset = new DisjSets(dsSize);
        orientations = new ArrayList<>(Arrays.asList(UP,DOWN,LEFT,RIGHT));
        for (int row = 0; row<height; row++){
        	for(int col = 0; col<width; col++){
        		myMaze[row][col] = new Cell(row,col);
        	}
        }
        myMaze[0][0] = new Cell(0,0,false,true,false,true);
        myMaze[height-1][width-1] = new Cell(height-1,width-1,false,false,false,false);


    }
    /**
     * Print the grid of the current the maze.
     */
    public void printGrid(){

    	System.out.print("  ");

        for(int i = 1; i < width; i++)
        {
            System.out.print(" _");
        }
        System.out.print('\n');
    	for(int row = 0; row < height; row++)
	       {
    		 if (row>0) System.out.print('|');
    		 else System.out.print(' ');
	         for(int col = 0; col < width; col++)
	         {
	             System.out.print(myMaze[row][col]);
	         }
	         System.out.print('\n');
	       }
    }

    /**
     * Print the randomly generated maze.
     */
    public void printMaze(){
    	this.generateMaze();
    	this.printGrid();
    }

    /**
     * Generate maze using the initial grid and algorithm discussed in the textbook.
     */
    private void generateMaze(){

        for (int count = 0; count < dsSize-1;){
        	int row = (int) (Math.random() * height);
        	int col = (int) (Math.random() * width);
        	Cell theCell = findNeighbors(row,col);
        	// randomly choose the cell next to the current cell
        	Cell nCell = theCell.neighbors.get((int)(Math.random()*theCell.neighbors.size()));
        	if (this.knockDownWall(theCell, nCell)) count++;
        }
    }
    /**
     * Internal method to find all the cells next to the current cell.
     * @param row The row the current cell resides.
     * @param col The column the current cell resides.
     * @return The current cell object.
     */
    private Cell findNeighbors(int row, int col){
    	Cell currCell = myMaze[row][col];
    	if (!currCell.neighbors.isEmpty()) return currCell;
		for (int[] orientation: orientations){
			int nRow = row + orientation[1];
			int nCol = col + orientation[0];
			if (nRow<0||nRow>=height) continue;
	    	if (nCol<0||nCol>=width) continue;
	    	Cell neighbor = myMaze[nRow][nCol];
	    	currCell.addNeighbor(neighbor);
		}
		return currCell;
    }

    /**
     * Knock the wall between two adjacent cell.
     * @param c1 The first cell object.
     * @param c2 The second cell object which is next to the first cell.
     * @return false if the two cells are already in the same set and
     *         there is no wall to knock down.
     */
    private boolean knockDownWall(Cell c1, Cell c2){
    	int set1 = dset.find(c1.col+c1.row*width);
    	int set2 = dset.find(c2.col+c2.row*width);
    	if (set1 == set2) return false;
    	if (c1.row == c2.row){
    		if (c1.col>c2.col){
    			c1.left = false;
    			c2.right = false;
    		}
    		else{
    			c1.right = false;
    			c2.left = false;
    		}
    	}
    	else{
    		if (c1.row<c2.row){
    			c1.down = false;
    			c2.up = false;
    		}
    		else{
    			c1.up = false;
    			c2.down = false;
    		}
    	}
    	dset.union(set1, set2);
    	return true;
    }


    private static class Cell{

    	public boolean up;     //false if there is not a roof wall
        public boolean down;   //false if there is not a bottom wall
        public boolean left;   //false if there is not a left wall
        public boolean right;  //false if there is not a right wall
        public int row, col;
        public List<Cell> neighbors; // Cells next to this cell in maze.

        public Cell(int r, int c){
        	this(r,c,true,true,true,true);
        }

		public Cell(int r,int c, boolean u, boolean d, boolean lt, boolean rt){
			row = r;
			col = c;
        	up = u;
        	down = d;
        	left = lt;
        	right = rt;
        	neighbors = new ArrayList<>();
        }

		public void addNeighbor(Cell c){
			neighbors.add(c);
		}

		public String toString()
        {
			StringBuilder sb = new StringBuilder();
			if(down)sb.append('_');
            else sb.append(" ");
            if(right) sb.append('|');
            else sb.append(" ");
            return sb.toString();
        }



    }

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
        System.out.println("Please enter an integers for the rows of the maze: ");
        int row = sc.nextInt();
        System.out.println("Please enter an integers for the columns of the maze: ");
        int column = sc.nextInt();

        Maze m = new Maze(row, column);
        //m.printGrid();
        System.out.println();
        m.printMaze();

	}

}
