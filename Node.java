package com.game.puzzle;

public class Node {

    // Pai do nó, ou seja o nó que vem antes dele
    public Node parent;

    // Forma atual do puzzle neste nó
    public int[][] matrix;

    // Coordenadas vazias
    public int x, y;

    // Custo de peças, numero de peças fora do lugar
    public int cost;

    // Numero total de movimetações desse nó
    public int level;

    public Node(int[][] matrix, int x, int y, int newX, int newY, int level, Node parent) {
        this.parent = parent;
        this.matrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = matrix[i].clone();
        }

        // Alterando valores
        this.matrix[x][y]       = this.matrix[x][y] + this.matrix[newX][newY];
        this.matrix[newX][newY] = this.matrix[x][y] - this.matrix[newX][newY];
        this.matrix[x][y]       = this.matrix[x][y] - this.matrix[newX][newY];

        // Definindo custo como maximo, pois será alterado
        this.cost = Integer.MAX_VALUE;

        // Novos valores resultantes do nó anterior
        this.level = level;
        this.x = newX;
        this.y = newY;
    }
}
