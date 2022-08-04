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
import game.PieceContract;
import game.piece.PieceCoord;
import game.piece.PiecePair;
import game.piece.PieceSides;
import game.piece.PieceState;
import vectorutils2dsymmetric.Simetry;

public class BoardManager {
    private Board board;
    private int countTurn = 0;
    private int countMoves = 0;
    private MovementsMemory moveList[];

    public BoardManager(Board board){
        this.board = board;
        this.moveList = new MovementsMemory[0];
    }

    public BoardManager(BoardType boardType) {
        this.newGame(boardType);
        this.moveList = new MovementsMemory[0];
    }

    public void newGame(BoardType boardType) {
        board = new ConventionalBoard(boardType);
        this.sumTurn();
    }

    public void setMoveList(MovementsMemory[] moveList) {
        this.moveList = moveList;
    }

    public MovementsMemory[] getMoveList() {
        return moveList;
    }

    public int getCountTurn() {
        return countTurn;
    }

    public int getCountMoves() {
        return countMoves;
    }

    public void sumTurn() {
        countTurn++;
    }
    public void sumMoves() {
        countMoves++;
    }

    public boolean checkSymetricBoard(int lastPiecesCount) {
        Integer[][] checkVector = new Integer[7][7];

        for (int i = 0; i < checkVector.length; i++) {
            for (int c = 0; c < checkVector.length; c++) {
                checkVector[i][c] = board.getPieces()[i][c].getState() == PieceState.EXIST ? 1 : 0;
            }
        }

        Simetry<Object> simetry = new Simetry<>(checkVector);

        simetry.verifySlashBackSlashSimetry();
        boolean isMidleCenter = false;
        if (simetry.getCountEqualsObjectsInRange(1, 2, 4) >= (lastPiecesCount / 2)) isMidleCenter = true;

        return isMidleCenter && simetry.symmetric;
    }


    protected PiecePair selectRelationPiecesBySide(PieceContract pieceBase, PieceSides side){

        int x = pieceBase.getXcoord();
        int y = pieceBase.getYcoord();

        int[] midlePiecePieceCoordOffset;
        int[] endPiecePieceCoordOffset;

        switch (side) {
            case LEFT:
                midlePiecePieceCoordOffset = new int[]{-1, 0};
                endPiecePieceCoordOffset = new int[]{-2, 0};
                break;
            case RIGHT:
                midlePiecePieceCoordOffset = new int[]{1, 0};
                endPiecePieceCoordOffset = new int[]{2, 0};
                break;
            case UP:
                midlePiecePieceCoordOffset = new int[]{0, -1};
                endPiecePieceCoordOffset = new int[]{0, -2};
                break;
            case DOWN:
                midlePiecePieceCoordOffset = new int[]{0, 1};
                endPiecePieceCoordOffset = new int[]{0, 2};
                break;
            default:
                midlePiecePieceCoordOffset = new int[]{-1, 0};
                endPiecePieceCoordOffset = new int[]{-2, 0};
                break;
        }

        int midlePieceOffsetX = midlePiecePieceCoordOffset[0];
        int midlePieceOffsetY = midlePiecePieceCoordOffset[1];

        int endPieceOffsetX = endPiecePieceCoordOffset[0];
        int endPieceOffsetY = endPiecePieceCoordOffset[1];

        PieceCoord midle = new PieceCoord(x + midlePieceOffsetX, y + midlePieceOffsetY);
        PieceCoord end = new PieceCoord(x + endPieceOffsetX, y + endPieceOffsetY);

        PieceContract midlePiece = board.getPiece(midle.getX(), midle.getY());
        PieceContract endPiece = board.getPiece(end.getX(), end.getY());

        PiecePair pair = new PiecePair(midlePiece, endPiece);

        return pair;

    }

    public void unMovePiece(PieceContract pieceBase, PieceSides side) {

        PiecePair linkedPieces = selectRelationPiecesBySide(pieceBase, side);

        linkedPieces.end.setState(PieceState.DEATH);
        linkedPieces.midle.setState(PieceState.EXIST);
        pieceBase.setState(PieceState.EXIST);
    }

    public boolean movePiece(PieceContract pieceBase, PieceSides side) {
        if (!moveCheck(pieceBase, side)){
            return false;
        }

        PiecePair linkedPieces = selectRelationPiecesBySide(pieceBase, side);

        linkedPieces.end.setState(PieceState.EXIST);
        linkedPieces.midle.setState(PieceState.DEATH);
        pieceBase.setState(PieceState.DEATH);

        this.sumMoves();
        return true;
    }

    private boolean moveCheck(PieceContract pieceBase, PieceSides side) {

        PiecePair linkedPieces = selectRelationPiecesBySide(pieceBase, side);

        if (!linkedPieces.isIsValidPair())
            return false;

        PieceCoord pieceMidle = linkedPieces.midleCoord;
        PieceCoord pieceEnd = linkedPieces.endCoord;

        if (isValidPieceCoord(pieceMidle.getX(), pieceMidle.getY())
                && isValidPieceCoord(pieceEnd.getX(), pieceEnd.getY())) {

            if (linkedPieces.midle.getState() == PieceState.EXIST) {
                if (linkedPieces.end.getState() == PieceState.DEATH) {
                    if(pieceBase.getState() == PieceState.EXIST){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidPieceCoord(int x, int y) {
        if (x < 7 && y < 7 && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

}
