import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
 *
 * @author Fangzhou Pan
 * Grade 100/100
 */
public class WordPuzzle {

	//private static final int GRID_SIZE_LIMIT = 10000;
	private static final int[] UP = {0,-1},DOWN = {0,1},LEFT = {-1,0},RIGHT = {1,0},
			UPPER_RIGHT = {1,-1},UPPER_LEFT = {-1,-1},BOTTOM_RIGHT = {1,1},BOTTOM_LEFT = {-1,1};
	private ArrayList<int[]> orientations;
	private char grid[][];
	private MyHashTable<String> myHash;
    private int gridSize;

	public WordPuzzle(int size){
		grid = new char[size][size];
	    myHash = new MyHashTable<String>();
	    gridSize = size;
	    createGrid(size);
	}

	public void loadDict(String filename){
		 try
	       {
	            FileReader fr = new FileReader(filename);
	            BufferedReader br = new BufferedReader(fr);
                myHash.makeEmpty();
	            String validWord;
	            while((validWord = br.readLine()) != null)
	            {
	                //store the dictionary word in the hash table
	                myHash.insert(validWord);
	                //store the prefix of the word in the hash table
	                int startIdx = 0;
	                for (int endIdx=1; endIdx<validWord.length();endIdx++){
	                	String prefix = validWord.substring(startIdx,endIdx);
	                	myHash.insert(prefix,false);
	                }
	            }
	            br.close();
	            System.out.println("Completed loading dictionary file");
	        }
	        catch(IOException e)
	        {
	            System.out.println("File not found");
	            e.printStackTrace();
	        }
	    System.out.println("The total amount of words in hash table "+myHash.size());
	}

	private void createGrid(int num){
		for(int row = 0; row < num; row++){
	         for(int col = 0; col < num; col++){
	             grid[row][col] = (char)(int)(Math.random() * 26 + 'a');
	         }
	       }
	}

	/**
	 * Use the algorithm described as the "second" algorithm in 1.1
	 * to solve the Word Puzzle problem
	 * @return the list of words found in the Word Puzzle
	 */
	public ArrayList<String> solvePuzzle(){
		ArrayList<String> words = new ArrayList<String>();
		this.setOrientations(8);
		for(int row = 0; row < gridSize; row++){
	         for(int col = 0; col < gridSize; col++){
	        	 String letter = Character.toString(this.grid[row][col]);
	        	 if (myHash.containsWord(letter)) words.add(letter);
	             for (int[] orientation: orientations){
	            	int charNum = 1;
	            	StringBuilder sb = new StringBuilder(letter);
					char chr = getNextCharInGrid(row,col,orientation,charNum);
	            	while(chr !='!' && charNum<45){
	            		sb.append(chr);
	            		String str = sb.toString();
	            		if (myHash.containsWord(str)) words.add(str);
	            		chr = getNextCharInGrid(row,col,orientation,++charNum);
	            	}
	             }
	         }
	       }
		return words;
	}

	/**
	 * Check whether the prefix of words in hashtable
	 * first to enhancement the performance of solve puzzle
	 * @return the list of words found in the Word Puzzle
	 */
	public ArrayList<String> effcientSolvePuzzle(){
		ArrayList<String> words = new ArrayList<String>();
		this.setOrientations(8);
		for(int row = 0; row < gridSize; row++){
	         for(int col = 0; col < gridSize; col++){
	        	 String letter = Character.toString(this.grid[row][col]);
	        	 if (myHash.containsWord(letter)) words.add(letter);
	        	 if (!myHash.contains(letter)) break;
	             for (int[] orientation: orientations){
	            	int charNum = 1;
	            	StringBuilder sb = new StringBuilder(letter);
					char chr = getNextCharInGrid(row,col,orientation,charNum);
	            	while(chr !='!' && charNum<45){
	            		sb.append(chr);
	            		if (!myHash.contains(sb.toString())) break;
	            		if (myHash.containsWord(sb.toString())){
	            			words.add(sb.toString());
	            		}
	            		chr = getNextCharInGrid(row,col,orientation,++charNum);
	            	}
	             }
	         }
	       }
		return words;
	}

    /**
     * Print out the generated grid
     * used in the Word Puzzle
     */
	public void printGrid(){
		int num = gridSize;
		for(int row = 0; row < num; row++)
	       {
	         for(int col = 0; col < num; col++)
	         {
	             System.out.print(grid[row][col] + " ");
	         }
	         System.out.print('\n');
	       }
	}



	private void setOrientations(int directions){
		if (directions == 8)
		    orientations = new ArrayList<int[]>(Arrays.asList(UP,DOWN,LEFT,RIGHT,UPPER_LEFT ,UPPER_RIGHT,
		    		                                         BOTTOM_LEFT,BOTTOM_RIGHT ));
		else if (directions == 4)
			orientations = new ArrayList<int[]>(Arrays.asList(UP,DOWN,LEFT,RIGHT));

	}

    private char getNextCharInGrid(int row, int col, int[] orientation,int charNum){
    	int tarRow = row + orientation[1]*charNum;
		int tarCol = col + orientation[0]*charNum;
    	if (tarRow<0||tarRow>=gridSize) return '!';
    	if (tarCol<0||tarCol>=gridSize) return '!';

        return grid[tarRow][tarCol];
	}



	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
	    System.out.println("Please input a value for the rows and columns of the grid: ");
	    int num = sc.nextInt();
	    WordPuzzle wp = new WordPuzzle(num);
	    wp.printGrid();
	    //String filename = "C:/Users/arkpan25/Desktop/dictionary.txt";
	    String filename = "src/project3/dictionary.txt";
	    wp.loadDict(filename);
	    long startTime = System.currentTimeMillis( );
	    ArrayList<String> words = wp.solvePuzzle();
	    long endTime = System.currentTimeMillis( );
	    long runningTime1 = endTime - startTime;
	    System.out.println( "Total number of words find in Word Puzzle: "+ words.size());
	    int count = 0;
	    for (String word: words){
	    	System.out.print(word+" ");
	    	if( ++count % 25 == 0) System.out.print("\n");
	    }
	    startTime = System.currentTimeMillis( );
	    words = wp.effcientSolvePuzzle();
	    endTime = System.currentTimeMillis( );
	    System.out.print("\n");
	    long runningTime2 = endTime - startTime;
	    System.out.println( "Total number of words find in Word Puzzle: "+ words.size());
	    count = 0;
	    for (String word: words){
	    	System.out.print(word+" ");
	    	if( ++count % 25 == 0) System.out.print("\n");
	    }
	    System.out.println( "\n"+"Elapsed time for normal method : " + (runningTime1) );
	    System.out.println( "Elapsed time for enhancement method: " + (runningTime2) );
	}

}
