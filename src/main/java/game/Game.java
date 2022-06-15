/*
 * The MIT License
 *
 * Copyright 2022 Maurício Moraes Nantes.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package game;

import java.util.Scanner;

/**
 *
 * @author wghat
 */
public class Game {

    public static void main(String[] args) {

        Board table = new Board();
        Scanner sc = new Scanner(System.in);

        int[][] emptyList = new int[1][2];

        emptyList[0] = new int[]{3, 3};

        int[][] moveList = new int[0][0];

        int typeTableN = -1;
        while (typeTableN < 0 || typeTableN > table.typeTable.length - 1) {
            System.out.println("Selecione o formato do jogo: ");
            for (int i = 0; i < table.typeTable.length; i++) {
                System.out.println(i + " para " + table.typeTable[i]);
            }
            typeTableN = sc.nextInt();
        }

        table.startBoard(typeTableN);

        int pieceCount = 0;
        while (pieceCount < 1 || pieceCount > table.fullPiecesCount) {
            System.out.println("Quantidade de peças restantes (> 0, 1 para ganhar): ");
            pieceCount = sc.nextInt();
        }

        int pieceCountStop = 0;
        while (pieceCountStop < 1 || pieceCountStop > table.fullPiecesCount) {
            System.out.println("Quantidade de peças a restar (> 0, o jogo para antes de ganhar): ");
            pieceCountStop = sc.nextInt();
        }

        char[] awns = new char[1];
        awns[0] = 'E';
        while (awns[0] != 's' && awns[0] != 'n') {
            System.out.println("Centrado? (s/n): ");
            awns = sc.next().toCharArray();
        }
        boolean centred = false;
        if (awns[0] == 's') {
            centred = true;
        }

        table.allPossibilities(0, pieceCount, pieceCountStop, centred, moveList);

        System.out.println(table.countTurn + " rodadas");
        System.out.println(table.countMoves + " movimentos");

    }
}
