package ch.lukasakermann.connectfourchallenge.game.strategy;

import ch.lukasakermann.connectfourchallenge.connectFourService.Game;
import ch.lukasakermann.connectfourchallenge.connectFourService.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DragonsStrategy implements ConnectFourStrategy {
    private static final String EMPTY_CELL = "EMPTY";
    private static final String OWN_TEAM = "Dragons";
    private String OWN_COLOR;

    @Override
    public int dropDisc(Game game) {
        List<Player> players = game.getPlayers();
        for (Player p: players) {
            if (p.getPlayerId().equals(OWN_TEAM)) OWN_COLOR = p.getDisc();
        }

        List<List<String>> board = game.getBoard();
        List<String> columns = board.get(0);
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
        System.out.println(OWN_COLOR);

        List<Integer> possibilities = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> colors.get(column).equals(OWN_COLOR))
                .collect(Collectors.toList());

        List<Integer> validMoves = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> columns.get(column).equals(EMPTY_CELL))
                .collect(Collectors.toList());

        Random rand = new Random();
        if (possibilities.size() > 0) {
            return possibilities.get(rand.nextInt(possibilities.size()));
        } else {
            return validMoves.get(rand.nextInt(validMoves.size()));
        }
    }
}
