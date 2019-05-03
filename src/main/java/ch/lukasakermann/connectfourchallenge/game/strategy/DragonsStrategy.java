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
    private static String OWN_COLOR;
    //private static String OWN_COLOR = "RED";

    @Override
    public int dropDisc(Game game) {
        List<Player> players = game.getPlayers();
        for (Player p: players) {
            if (p.getPlayerId().equals(OWN_TEAM)) OWN_COLOR = p.getDisc();
        }

        List<List<String>> board = game.getBoard();
        if(isEmpty(board)) return 3;
        
        List<String> columns = board.get(0);

        List<Integer> possibilities = getVertical(board);
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
     private static List<Integer> getVertical(List<List<String>> board) {
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
     
     private static boolean isEmpty(List<List<String>> board) {
    	 for(int i=0;i<7;i++) {
    		 if(!board.get(5).get(i).equals(EMPTY_CELL)) {
    			 return false;
    		 }
    	 }
    	 return true;
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
    
    	
    	//System.out.println(freePlaces(testBoard));
    	System.out.println(getVertical(testBoard));
    	
    	System.out.println(isEmpty(testBoard));

	}
}
