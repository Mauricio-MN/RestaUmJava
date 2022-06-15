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

public class Piece{

    public boolean valid;
    public boolean full;
    public int x;
    public int y;

    public Piece sideMidlePiece;
    public Piece sideEndPiece;

    Piece[][] pieces;

    public Piece(int i, int iX, int iY, Piece[][] myPieces) {

        switch (i) {
            case 0:
                valid = false;
                full = false;
                break;
            case 1:
                valid = true;
                full = true;
                break;
            case 2:
                valid = true;
                full = false;
                break;
            default:
                valid = false;
                full = false;
        }

        x = iX;
        y = iY;

        pieces = myPieces;

    }

    private boolean isValidCoord(int iX, int iY) {
        if (iX < 7 && iY < 7 && iX >= 0 && iY >= 0) {
            return true;
        }
        return false;
    }

    public void unMovePiece() {
            sideEndPiece.full = false;
            sideMidlePiece.full = true;
            full = true;
    }

    public void movePiece() {
            sideEndPiece.full = true;
            sideMidlePiece.full = false;
            full = false;
    }

    private boolean moveCheck(Coords sideMidleCoords, Coords sideEndCoords) {

        if (isValidCoord(sideMidleCoords.x, sideMidleCoords.y) && isValidCoord(sideEndCoords.x, sideEndCoords.y)) {

            sideEndPiece = pieces[sideEndCoords.x][sideEndCoords.y];
            sideMidlePiece = pieces[sideMidleCoords.x][sideMidleCoords.y];

            if (sideMidlePiece.valid == true && sideMidlePiece.full == true) {
                if (sideEndPiece.valid == true && sideEndPiece.full == false) {
                    if(valid == true && full == true){
                        return true;
                    }
                }
            }
        }
        return false;
    }


        public boolean right() {

        Coords sideEndCoords = new Coords(x + 2, y);
        Coords sideMidleCoords = new Coords(x + 1, y);
        return moveCheck(sideMidleCoords, sideEndCoords);

    }

    public boolean left() {

        Coords sideEndCoords = new Coords(x - 2, y);
        Coords sideMidleCoords = new Coords(x - 1, y);
        return moveCheck(sideMidleCoords, sideEndCoords);
    }

    public boolean up() {

        Coords sideEndCoords = new Coords(x, y - 2);
        Coords sideMidleCoords = new Coords(x, y - 1);
        return moveCheck(sideMidleCoords, sideEndCoords);
    }

    public boolean down() {

        Coords sideEndCoords = new Coords(x, y + 2);
        Coords sideMidleCoords = new Coords(x, y + 1);
        return moveCheck(sideMidleCoords, sideEndCoords);
    }

}
