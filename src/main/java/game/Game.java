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

import game.board.BoardManager;
import game.board.BoardResolver;
import game.board.BoardType;
import game.board.MovementsMemory;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 *
 * @author wghat
 */
public class Game {

    public static void main(String[] args) throws UnsupportedEncodingException {

        Scanner sc = new Scanner(System.in);

        int[][] emptyList = new int[1][2];

        emptyList[0] = new int[]{3, 3};

        int[][] moveList = new int[0][0];

        int typeTableN = -1;
        BoardType boardType = BoardType.DEFAULT;
        while (typeTableN < 0 || typeTableN > BoardType.values().length - 1) {
            System.out.println("Selecione o formato do jogo: ");
            for (BoardType type: BoardType.values()) {
                System.out.println(type.getType() + " para " + type);
            }
            typeTableN = sc.nextInt();
            boardType = BoardType.valueOf(typeTableN);
        }

        BoardManager boardManager = new BoardManager(boardType);
        Board board = boardManager.getBoard();

        int pieceCount = 0;
        while (pieceCount < 1 || pieceCount > board.getFullPiecesCount()) {
            System.out.println("Quantidade de peças restantes (> 0, 1 para ganhar): ");
            pieceCount = sc.nextInt();
        }

        int pieceCountStop = 0;
        while (pieceCountStop < 1 || pieceCountStop > board.getFullPiecesCount()) {
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

        BoardResolver resolver = new BoardResolver(boardManager);

        System.out.println(boardManager.getBoard().toStringBoard());

        resolver.resolve(0, pieceCount, pieceCountStop, centred);

        System.out.println("Sobrou " + resolver.getPiecesRemainsCount());

            int iteratePiecesMovesCount = 0;
            for (MovementsMemory item : resolver.getMovesList()) {
                if (iteratePiecesMovesCount < (board.getFullPiecesCount() - pieceCountStop)) {
                    System.out.println("" + item.getFrom().toStringPiece() + " moveu para " + item.getTo().toStringPiece());
                    System.out.println(item.getBoardString());
                    iteratePiecesMovesCount++;
                } else {
                    break;
                }
            }

            System.out.println(resolver.getPiecesEatsCount() + " peças capturadas");
            if (resolver.isIsCentred()) {
                System.out.println("Centrado");
            }

        System.out.println(boardManager.getCountTurn() + " rodadas");
        System.out.println(boardManager.getCountMoves() + " movimentos");
    }
}
