package com.game.puzzle;

public class PuzzleApplication {
    public static void main(String[] args) {
        // Matrix inicial, desafio
        int[][] initial = {{1, 8, 2}, {0, 4, 3}, {7, 6, 5}};

        // Matrix final, resultado
        int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        // Coordenadas
        int x = 1, y = 0;

        Puzzle puzzle = new Puzzle();

        if (puzzle.isSolvable(initial)) {
            puzzle.solve(initial, goal, x, y);
            return;
        }

        System.out.println("Não é possivel resolver a inicial informada!");
    }
}
