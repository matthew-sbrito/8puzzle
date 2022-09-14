package com.game.puzzle;

import java.util.*;
import java.util.stream.Collectors;

public class Puzzle {
    public int dimension = 3;

    // Bottom, left, top, right 
    int[] row = {1, 0, -1, 0};
    int[] col = {0, -1, 0, 1};

    PriorityQueue<Node> priorityQueue;

    // Calcula quantos movimentos são necessários para cumprir a meta final
    public int calculateCost(int[][] initial, int[][] goal) {
        int count = 0;
        int initialLength = initial.length;

        for (int i = 0; i < initialLength; i++) {
            for (int j = 0; j < initialLength; j++) {
                if (initial[i][j] != 0 && initial[i][j] != goal[i][j]) count++;
            }
        }

        return count;
    }

    // Exibe em tela matrix de forma organizada
    public void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {

            // Junta os numeros do array e gera uma linha exemplo:
            // De: {4, 5, 6}
            // Para: "4 5 6"
            String row = Arrays
                    .stream(ints)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" "));

            System.out.println(row);
        }
    }

    // Verifica se o próximo nó consegue ser colocado na dimensão da matrix
    public boolean isSafe(int x, int y) {
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }

    // Metodo recursivo para exibe os nós em tela
    public void printPath(Node node) {
        // Quebra a recursividade, parando de printar quando for o primeiro node, o root!
        if (node == null) return;

        // Chama a função recursivamente
        printPath(node.parent);

        // Exibe a matrix atual em tela
        printMatrix(node.matrix);

        // Quebra linha
        System.out.println();
    }

    // Verifica se é uma matrix solucionável
    public boolean isSolvable(int[][] matrix) {
        int count = 0;

        List<Integer> array = new ArrayList<>();

        int length = matrix.length;

        // adicionando todos os valores no novo array, exemplo:
        // De: {{1, 8, 2}, {0, 4, 3}, {7, 6, 5}}
        // Para: {1, 8, 2, 0, 4, 3, 7, 6, 5}
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                array.add(matrix[i][j]);
            }
        }

        int arrayLength = array.size();

        // Laço para verificar cada item do array
        for (int i = 0; i < arrayLength - 1; i++) {
            for (int j = i + 1; j < arrayLength; j++) {
                // Verifica se o número atual do array e o próximo número são diferentes de 0
                // Verifica se o número atual é maior que o próximo número
                // Soma o contador apoś verificar as condicionais
                if (array.get(i) != 0 && array.get(j) != 0 && array.get(i) > array.get(j)) count++;
            }
        }

        // Se o resto da divisão for 0, ele consegue resolver
        return count % 2 == 0;
    }

    // Soluciona a matrix inicial para a final
    public void solve(int[][] initial, int[][] goal, int x, int y) {
        // Definindo uma fila de prioridade para execução da tarefa, comparando pelo número de peças fora do lugar
        priorityQueue = new PriorityQueue<>(
                1000,
                Comparator.comparingInt(a -> (a.cost + a.level))
        );

        // Primeiro nó, ponto inicial da tarefa
        Node first = new Node(initial, x, y, x, y, 0, null);

        // Adicionando nó na fila de prioridade
        addNode(goal, first);

        // Laço de repetição para verificar se ainda existe Item na fila para ser executado
        while (!priorityQueue.isEmpty()) {
            // Nó com menor número de movimentações, de acordo com a prioridade da fila
            Node min = priorityQueue.poll();

            // Condicional para quebrar o laço de repetição, indicando que foi cincluido a task
            if (min.cost == 0) {
                printPath(min);
                break;
            }

            for (int i = 0; i < 4; i++) {

                // Verificando se é seguro colocar o novo nó
                if (!isSafe(min.x + row[i], min.y + col[i])) continue;

                Node child = new Node(
                        min.matrix,
                        min.x, min.y,
                        min.x + row[i],
                        min.y + col[i],
                        min.level + 1,
                        min
                );

                addNode(goal, child);
            }
        }
    }

    // Calcula e adiciona o nó na fila
    public void addNode(int[][] goal, Node node) {
        node.cost = calculateCost(node.matrix, goal);
        priorityQueue.add(node);
    }
}
