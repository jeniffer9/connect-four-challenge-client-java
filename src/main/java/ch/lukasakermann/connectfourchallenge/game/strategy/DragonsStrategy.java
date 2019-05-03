package ch.lukasakermann.connectfourchallenge.game.strategy;

import ch.lukasakermann.connectfourchallenge.connectFourService.Game;
import ch.lukasakermann.connectfourchallenge.connectFourService.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DragonsStrategy implements ConnectFourStrategy {
    private static final String EMPTY_CELL = "EMPTY";
    private String OWN_COLOR;

    @Override
    public int dropDisc(Game game) {
        List<Player> players = game.getPlayers();
        for (Player p: players) {
            if (p.getPlayerId().equals("Alice")) OWN_COLOR = p.getDisc();
        }

        List<List<String>> board = game.getBoard();
        List<String> columns = board.get(0);


        List<Integer> validMoves = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> columns.get(column).equals(EMPTY_CELL))
                .collect(Collectors.toList());

        Random rand = new Random();
        List<Integer> verticalPossibilities = getVertical(board);

        freePlaces(board);

        if (verticalPossibilities.size() > 0) {
            return verticalPossibilities.get(rand.nextInt(verticalPossibilities.size()));
        } else {
            return validMoves.get(rand.nextInt(validMoves.size()));
        }
    }

     private List<Integer> getVertical(List<List<String>> board) {
        List<String> colors = new ArrayList<>(Collections.nCopies(7, EMPTY_CELL));
        Set<Integer> taken = new HashSet<>();
        for (int i = 0; i < board.size(); ++i) {
            List<String> row = board.get(i);
            for (int j = 0; j < row.size() && !taken.contains(j); ++j) {
                if (!row.get(j).equals(EMPTY_CELL)) {
                    colors.add(i, row.get(j));
                    taken.add(i);
                }
            }
        }

        return IntStream.range(0, colors.size())
                .boxed()
                .filter(column -> colors.get(column).equals(OWN_COLOR))
                .collect(Collectors.toList());
    }

    private Map<Integer, Integer> freePlaces(List<List<String>> board){
        List<String> columns = board.get(0);

        List<Integer> validMoves = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> columns.get(column).equals(EMPTY_CELL))
                .collect(Collectors.toList());

        List<String> colors = new ArrayList<>(Collections.nCopies(7, EMPTY_CELL));
        Map<Integer, Integer> free = new HashMap<>();
        for (int r = 0; r < validMoves.size(); ++r) {
            for (int i = 1; i < 6 && !free.containsKey(r); ++i){
                if (!board.get(r).get(i).equals(EMPTY_CELL))
                    free.put(r, i-1);
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
}
