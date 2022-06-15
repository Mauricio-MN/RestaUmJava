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

import vectorutils2dsymmetric.Listed;
import vectorutils2dsymmetric.Simetry;

public class Board {

    public Piece[][] pieces;
    public int fullPiecesCount;
    public int countTurn = 0;
    public int countMoves = 0;
    public final String[] typeTable;

    private Coords[] fullPieces;


    public Board() {
        pieces = new Piece[7][7];
        typeTable = new String[]{"padrão", "full"};
    }

    public void startBoard(int tableNumber) {

        int[][] table;

        switch (tableNumber) {
            case 0:
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
            case 1:
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

        fullPieces = new Coords[fullPiecesCount];

        int p = 0;

        for (int iX = 0; iX < 7; iX++) {
            for (int iY = 0; iY < 7; iY++) {
                pieces[iX][iY] = new Piece(table[iX][iY], iX, iY, pieces);
                if (table[iX][iY] == 1) {
                    fullPieces[p] = new Coords(iX, iY);
                    p++;
                }
            }
        }

    }

    public boolean allPossibilities(int pieceEats, int piecesEnd, int remains, boolean centred, int moveList[][]){
        return allPossibilities(pieceEats, fullPieces, piecesEnd, remains, centred, moveList);
    }

    private boolean allPossibilities(int pieceEats, Coords[] fullList, int piecesEnd, int remains, boolean centred, int moveList[][]) {

        if (fullList.length <= 0) {
            return true;
        }

        if ((fullPiecesCount - pieceEats) < piecesEnd) {
            countTurn++;
            return true;
        }

        boolean checkMovesOnfullList = true;

        for (int i = 0; i < fullList.length; i++) {

            Coords full = fullList[i];

            Piece basePiece = pieces[full.x][full.y];

            for (int c = 0; c < 4; c++) {
                boolean validMove;
                switch (c) {
                    case 0:
                        validMove = basePiece.right();
                        break;
                    case 1:
                        validMove = basePiece.left();
                        break;
                    case 2:
                        validMove = basePiece.up();
                        break;
                    case 3:
                        validMove = basePiece.down();
                        break;
                    default:
                        validMove = false;
                }

                if (validMove) {

                    checkMovesOnfullList = false;

                    basePiece.movePiece();
                    Piece sideMidlePiece = basePiece.sideMidlePiece;
                    Piece sideEndPiece = basePiece.sideEndPiece;

                    int[][] moveListRec = new int[moveList.length + 1][4];
                    System.arraycopy(moveList, 0, moveListRec, 0, moveList.length);
                    moveListRec[moveListRec.length - 1] = new int[]{basePiece.x, basePiece.y, basePiece.sideEndPiece.x, basePiece.sideEndPiece.y};

                    Coords sidecoords = new Coords(basePiece.sideEndPiece.x, basePiece.sideEndPiece.y);
                    Coords sidecoordsM = new Coords(basePiece.sideMidlePiece.x, basePiece.sideMidlePiece.y);
                    Coords basePieceCoord = new Coords(basePiece.x, basePiece.y);

                    Coords[] fullListRec = new Coords[fullList.length - 1];

                    if (fullListRec.length > 0) {
                        int r = 0;
                        for (int p = 0; p < fullList.length; p++) {
                            if ( !fullList[p].compare(sidecoordsM) && !fullList[p].compare(basePieceCoord) ) {
                                fullListRec[r] = fullList[p];
                                r++;
                            }
                        }
                        r = fullListRec.length;

                        fullListRec[r - 1] = new Coords(sidecoords.x, sidecoords.y);
                    }

                    countMoves++;
                    boolean last = allPossibilities(pieceEats + 1, fullListRec, piecesEnd, remains, centred, moveListRec);
                    if (last == false) {
                        return false;
                    }

                    basePiece.sideMidlePiece = sideMidlePiece;
                    basePiece.sideEndPiece = sideEndPiece;

                    basePiece.unMovePiece();

                }

            }

        }

        if (checkMovesOnfullList) {
            countTurn++;
        }
        if ((fullPiecesCount - piecesEnd) == pieceEats && checkMovesOnfullList) {

            Integer[][] checkVector = new Integer[7][7];
            for (int i = 0; i < pieces.length; i++) {
                for (int c = 0; c < pieces.length; c++) {
                    checkVector[i][c] = pieces[i][c].full ? 1 : 0;
                }
            }
            Simetry<Object> simetry = new Simetry<>(checkVector);
            Listed<Object> listed = new Listed<>(checkVector);

            simetry.verifySlashBackSlashSimetry();
            listed.verifyListed(1, 2, 4);

            boolean center = listed.onlist && simetry.symmetric;

            if (centred) {
                if (!center) {
                    return true;
                }
            } else {
                if (center) {
                    return true;
                }
            }

            System.out.println("Sobrou " + piecesEnd);

            int t = 0;
            for (int[] item : moveList) {
                if (t < (fullPiecesCount - remains)) {
                    System.out.println(" X: " + item[0] + " Y: " + item[1] + " moveu para " + " X: " + item[2] + " Y: " + item[3]);
                }
                t++;
            }
            System.out.println(pieceEats + " peças capturadas");
            if (center) {
                System.out.println("Centrado");
            }

            return false;
        }

        return true;

    }
    

}
