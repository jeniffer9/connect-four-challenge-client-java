package ch.lukasakermann.connectfourchallenge.game.strategy;

import ch.lukasakermann.connectfourchallenge.connectFourService.Game;
import ch.lukasakermann.connectfourchallenge.connectFourService.Player;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DragonsStrategy implements ConnectFourStrategy {
    private static final String EMPTY_CELL = "EMPTY";
    private static final String OWN_TEAM = "Dragons";
    //private static String OWN_COLOR;
    private static String OWN_COLOR = "RED";
    private static List<List<String>> board;

    @Override
    public int dropDisc(Game game) {
    	this.board = game.getBoard();
        List<Player> players = game.getPlayers();
        for (Player p: players) {
            if (p.getPlayerId().equals(OWN_TEAM)) OWN_COLOR = p.getDisc();
        }

        if(isEmpty()) return 3;
        
        List<String> columns = board.get(0);

        List<Integer> possibilities = getVertical();
        List<Integer> validMoves = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> columns.get(column).equals(EMPTY_CELL))
                .collect(Collectors.toList());

        Random rand = new Random();
        //freePlaces(board);

        if (possibilities.size() > 0) {
            return possibilities.get(rand.nextInt(possibilities.size()));
        } else {
            return validMoves.get(rand.nextInt(validMoves.size()));
        }
    }

    /**
     * Returns all the columns where OUR player has the top stone.
     * @param board
     * @return
     */
     private static List<Integer> getVertical() {
    	 List<Integer> possibilities = new ArrayList<>();
    	 Map<Integer, Integer> freePlaces = freePlaces(board);
    	 for (Entry<Integer, Integer> free : freePlaces.entrySet()) {
    		 int columnNum = free.getKey();
    		 int row = free.getValue();
    		 if(row != 5) {
    			 if (board.get(row+1).get(columnNum).equals(OWN_COLOR)) {   				
    				 possibilities.add(columnNum);
    			 }   			
    		 }
    	 }
         return possibilities;
    }
     /**
      * Checks whether the board is empty
      * @param board
      * @return
      */
     private static boolean isEmpty() {
    	 for(int i=0;i<7;i++) {
    		 if(!board.get(5).get(i).equals(EMPTY_CELL)) {
    			 return false;
    		 }
    	 }
    	 return true;
     }

     /**
      * Check whether the coordinate to the left has OWN_COLOR
      * @param board
      * @param coordinate
      * @return
      */
     private static boolean getLeftOne(List<List<String>> board, Map.Entry<Integer, Integer> coordinate) {
    	 if(coordinate.getValue() == 0) {
    		 return false;
    	 } else if (board.get(coordinate.getValue()).get(coordinate.getKey()-1).equals(OWN_COLOR)){
    		 return true;
    	 }
    	 else {
    		 return false;
    	 }
     }
     
     /**
      * Check whether the coordinate to the right has OWN_COLOR
      * @param board
      * @param coordinate
      * @return
      */
     private static boolean getRightOne(List<List<String>> board, Map.Entry<Integer, Integer> coordinate) {
    	 if(coordinate.getValue() == 6) {
    		 return false;
    	 } else if (board.get(coordinate.getValue()).get(coordinate.getKey()+1).equals(OWN_COLOR)){
    		 return true;
    	 }
    	 else {
    		 return false;
    	 }
     }
     
     private static boolean horizontalWin(List<List<String>> board, Map.Entry<Integer, Integer> coordinate) {
    	 int countLeft = 0;
    	 int countRight = 0;
    	 while(getLeftOne(board, coordinate)){
    		 countLeft++;
    	 }
    	 while(getRightOne(board,coordinate)) {
    		 countRight++;
    	 }
    	 
    	 if ((countLeft == 3 || countRight == 3) || (countLeft == 2 && countRight == 1) || (countLeft == 1 && countRight == 2)) {
    		 return true;
    	 } else {
    		 return false;
    	 }  	 
     }
     
    private static Map<Integer, Integer> freePlaces(List<List<String>> board){
        List<String> columns = board.get(0);

        List<Integer> validMoves = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> columns.get(column).equals(EMPTY_CELL))
                .collect(Collectors.toList());

        Map<Integer, Integer> free = new HashMap<>();
        for (int r = 0; r < validMoves.size(); ++r) {
            int key = validMoves.get(r);
            for (int i = 1; i < 6 && !free.containsKey(key); ++i){
                if (!board.get(i).get(key).equals(EMPTY_CELL))
                    free.put(key, i-1);
            }
            if (!free.containsKey(key)) {
            	free.put(key, 5);
            }
        }
        for (Map.Entry<Integer, Integer> e : free.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
        return free;
    }

    private List<Integer> getHorizontal(List<List<String>> board) {
        return null;
    }
    
    public static void main(String[] args) {
    	String EMPTY_CELL = "EMPTY";
    	List<List<String>> testBoard = new ArrayList<>();
    	List<String> row_0 = new ArrayList<>();
    	List<String> row_1 = new ArrayList<>();
    	List<String> row_2 = new ArrayList<>();
    	List<String> row_3 = new ArrayList<>();
    	List<String> row_4 = new ArrayList<>();
    	List<String> row_5 = new ArrayList<>();
    	for (int i=0;i<7;i++) {
    		row_0.add(EMPTY_CELL);
    		row_1.add(EMPTY_CELL);
    		row_2.add(EMPTY_CELL);
    		row_3.add(EMPTY_CELL);
    		row_4.add(EMPTY_CELL);
    		row_5.add(EMPTY_CELL);
    		
    	}
    	
    	testBoard.add(row_0);
    	testBoard.add(row_1);
    	testBoard.add(row_2);
    	testBoard.add(row_3);
    	testBoard.add(row_4);
    	testBoard.add(row_5);

    	
    	testBoard.get(5).set(2, "RED");
    	testBoard.get(5).set(3, "YELLOW");
    	testBoard.get(4).set(3, "YELLOW");
    	
    	System.out.println(testBoard.get(0));
    	System.out.println(testBoard.get(1));
    	System.out.println(testBoard.get(2));
    	System.out.println(testBoard.get(3));
    	System.out.println(testBoard.get(4));
    	System.out.println(testBoard.get(5));
    
    	
    	System.out.println(freePlaces(testBoard));
    	
    	Map<Integer,Integer> coordinates = new HashMap<Integer, Integer>();
    	coordinates.put(1,5);
    	Map.Entry<Integer, Integer> entry = coordinates.entrySet().iterator().next();
    	System.out.println(getRightOne(testBoard, entry));
    	//System.out.println(getVertical(testBoard));
    	
    	//System.out.println(isEmpty(testBoard));

	}
}
