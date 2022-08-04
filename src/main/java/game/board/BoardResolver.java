/*
 * The MIT License
 *
 * Copyright 2022 wghat.
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

import game.PieceContract;
import game.piece.PieceCoord;
import game.piece.PiecePair;
import game.piece.PieceSides;
import game.piece.PieceState;

/**
 *
 * @author wghat
 */
public class BoardResolver {

    private BoardManager boardManager;
    private boolean isCentred;
    private int piecesRemainsCount;
    private int piecesEatsCount;
    MovementsMemory[] movesList;


    public BoardResolver(BoardManager boardManager) {
        this.boardManager = boardManager;
        movesList = new MovementsMemory[0];
    }

    public boolean isIsCentred() {
        return isCentred;
    }

    public int getPiecesRemainsCount() {
        return piecesRemainsCount;
    }

    public int getPiecesEatsCount() {
        return piecesEatsCount;
    }

    public MovementsMemory[] getMovesList() {
        return movesList;
    }

    public boolean resolve(int pieceEats, int piecesEnd, int mustBeLeft, boolean needToBeCentred) {

        return this.playAllPossibilities(pieceEats, piecesEnd, mustBeLeft, needToBeCentred, boardManager.getMoveList(), boardManager.getBoard().getFullPieces());
    }

    private boolean playAllPossibilities(int pieceEats, int piecesEnd, int mustBeLeft, boolean needToBeCentred, MovementsMemory[] movesListReceived, PieceContract[] fullPiecesReceived) {

        int fullPiecesReceivedCount = fullPiecesReceived.length;
        int fullPiecesCountOnInitBoard = boardManager.getBoard().getFullPiecesCount();
        if (fullPiecesReceivedCount <= 0) {
            return true;
        }

        if ((fullPiecesCountOnInitBoard - pieceEats) < piecesEnd) {
            boardManager.sumTurn();
            return true;
        }

        boolean isMove = false;

        for (PieceContract basePiece :  fullPiecesReceived) {

            for (PieceSides side : PieceSides.values()) {

                if (boardManager.movePiece(basePiece, side)) {
        
                    isMove = true;

                    PiecePair relationsBasePiece = boardManager.selectRelationPiecesBySide(basePiece, side);
                    PieceContract sideMidlePiece = relationsBasePiece.midle;
                    PieceContract sideEndPiece = relationsBasePiece.end;

                    MovementsMemory[] moveListRec = this.createNewMoveListPiecesList(movesListReceived, basePiece, sideMidlePiece, sideEndPiece);
                    PieceContract[] fullPiecesRecord = createNewFullPiecesList(fullPiecesReceived,
                            basePiece, sideMidlePiece, sideEndPiece);

                    boolean last = playAllPossibilities(pieceEats + 1, piecesEnd, mustBeLeft, needToBeCentred, moveListRec, fullPiecesRecord);

                    if (last == false) {
                        return false;
                    }

                    boardManager.unMovePiece(basePiece, side);

                } else {
                    String teste = "invalid";
                }

            }

        }

        if (isMove == false) {
            boardManager.sumTurn();
        }

        if ((fullPiecesCountOnInitBoard - piecesEnd) == pieceEats && isMove == false) {

            isCentred = boardManager.checkSymetricBoard(piecesEnd);
            piecesRemainsCount = piecesEnd;
            movesList = movesListReceived;
            piecesEatsCount = pieceEats;

            if (needToBeCentred) {
                if (!isCentred) {
                    return true;
                }
            } else {
                if (isCentred) {
                    return true;
                }
            }

            return false;
        }

        return true;

    }

    private PieceContract[] createNewFullPiecesList(PieceContract[] fullPiecesReceived, PieceContract basePiece, PieceContract midlePiece, PieceContract endPiece) {

        PieceCoord basePieceCoord = new PieceCoord(basePiece);
        PieceCoord midlePieceCoord = new PieceCoord(midlePiece);
        PieceCoord endPieceCoord = new PieceCoord(endPiece);

        int lastSize = fullPiecesReceived.length;
        PieceContract[] actualPieceCoord = new PieceContract[lastSize - 1];

        if (lastSize > 0) {
            int r = 0;
            for (PieceContract piece : fullPiecesReceived) {
                PieceCoord coord = new PieceCoord(piece);
                if (!coord.compare(midlePieceCoord) && !coord.compare(basePieceCoord)) {
                    actualPieceCoord[r] = piece;
                    r++;
                }
            }
            r = actualPieceCoord.length;

            actualPieceCoord[r - 1] = endPiece;
        }
        return actualPieceCoord;
    }

    private MovementsMemory[] createNewMoveListPiecesList(MovementsMemory[] movesListReceived, PieceContract basePiece, PieceContract midlePiece, PieceContract endPiece) {

        int lastSize = movesListReceived.length;
        MovementsMemory[] result = new MovementsMemory[lastSize + 1];
        System.arraycopy(movesListReceived, 0, result, 0, lastSize);

        int i = 0;
        for(MovementsMemory move: movesListReceived){
            result[i] = move;
            i++;
        }

        result[result.length - 1] = new MovementsMemory(basePiece, midlePiece, endPiece);
        result[result.length - 1].setBoardString(boardManager.getBoard().toStringBoard());
        return result;
    }
    
}
