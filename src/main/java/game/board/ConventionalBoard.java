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
package game.board;

import game.Board;
import game.piece.Piece;
import game.PieceContract;
import game.piece.PieceState;

public class ConventionalBoard implements Board{
    
    private final PieceContract[][] pieces;
    private BoardType boardType;
    private PieceContract[] fullPieces;
    private final int boardSize;


    public ConventionalBoard(BoardType boardType) {
        this.boardSize = 7;
        this.boardType = boardType;
        this.pieces = new PieceContract[7][7];
        instancePieces();

        startBoard();
    }

    private void instancePieces() {
        int p = 0;
        for (int iX = 0; iX < this.boardSize; iX++) {
            for (int iY = 0; iY < this.boardSize; iY++) {
                pieces[iX][iY] = new Piece(PieceState.NOTEXIST, iX, iY);
            }
        }
    }

    public BoardType getBoardType() {
        return boardType;
    }

    @Override
    public int getFullPiecesCount() {
        return fullPieces.length;
    }
    @Override
    public PieceContract[][] getPieces() {
        return pieces;
    }
    @Override
    public PieceContract getPiece(int x, int y) {
        if (x >= boardSize || x < 0 || y >= boardSize || y < 0) {
            return null;
        }
        return pieces[x][y];
    }
    @Override
    public PieceContract[] getFullPieces() {
        return fullPieces;
    }

    public void setFullPieces(PieceContract[] fullPieces) {
        this.fullPieces = fullPieces;
    }


    private void startBoard() {

        int[][] table;

        int fullPiecesCount = 0;

        switch (this.boardType) {
            case DEFAULT:
                table = new int[][]{{0, 0, 1, 1, 1, 0, 0},
                                    {0, 0, 1, 1, 1, 0, 0},
                                    {1, 1, 1, 1, 1, 1, 1},
                                    {1, 1, 1, 2, 1, 1, 1},
                                    {1, 1, 1, 1, 1, 1, 1},
                                    {0, 0, 1, 1, 1, 0, 0},
                                    {0, 0, 1, 1, 1, 0, 0}
                };
                fullPiecesCount = 32;
                break;
            case FULL:
                table = new int[][]{{1, 1, 1, 1, 1, 1, 1},
                                    {1, 1, 1, 1, 1, 1, 1},
                                    {1, 1, 1, 1, 1, 1, 1},
                                    {1, 1, 1, 2, 1, 1, 1},
                                    {1, 1, 1, 1, 1, 1, 1},
                                    {1, 1, 1, 1, 1, 1, 1},
                                    {1, 1, 1, 1, 1, 1, 1}
                };
                fullPiecesCount = 48;
                break;
            default:
                table = new int[][]{{0, 0, 1, 1, 1, 0, 0},
                                    {0, 0, 1, 1, 1, 0, 0},
                                    {1, 1, 1, 1, 1, 1, 1},
                                    {1, 1, 1, 2, 1, 1, 1},
                                    {1, 1, 1, 1, 1, 1, 1},
                                    {0, 0, 1, 1, 1, 0, 0},
                                    {0, 0, 1, 1, 1, 0, 0}
                };
                fullPiecesCount = 32;
                break;

        }

        fullPieces = new PieceContract[fullPiecesCount];

        int p = 0;

        for (int iX = 0; iX < 7; iX++) {
            for (int iY = 0; iY < 7; iY++) {
                pieces[iX][iY].setState(PieceState.valueOf(table[iX][iY]));
                pieces[iX][iY].setXcoord(iX);
                pieces[iX][iY].setYcoord(iY);
                if (table[iX][iY] == 1) {
                    fullPieces[p] =  pieces[iX][iY];
                    p++;
                }
            }
        }

    }

    /**
     * Print Board;
     */
    @Override
    public String toStringBoard(){
        String result = "";
        result += " 0 1 2 3 4 5 6 \n \n";
        int it = 0;
        for (PieceContract[] ipieces : this.pieces) {
            String table = "";
            for (PieceContract ipiece : ipieces) {
                switch (ipiece.getState()) {
                    case EXIST:
                        table += " •";
                        break;
                    case NOTEXIST:
                        table += "  ";
                        break;
                    case DEATH:
                        table += " ~";
                        break;
                    default:
                        table += "  ";
                        break;

                }
            }
            table += "  " + it;
            it++;
            result += table + "\n";
        }
        return result;
    }
    

}
