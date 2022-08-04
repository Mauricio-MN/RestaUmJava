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

package vectorutils2dsymmetric;

import java.util.Arrays;

public class Simetry<K> {

    public boolean symmetric;

    public Simetry(K[][] vect){
    
        vector = vect;
        symmetric = false;
    }

    private K[][] vector;

    public void verifySlashBackSlashSimetry() {

        Object[][] vector1 = new Object[(vector.length - 1) / 2 + 1][(vector.length - 1) / 2 + 1];
        Object[][] vector2 = new Object[(vector.length - 1) / 2 + 1][(vector.length - 1) / 2 + 1];
        Object[][] vector3 = new Object[(vector.length - 1) / 2 + 1][(vector.length - 1) / 2 + 1];
        Object[][] vector4 = new Object[(vector.length - 1) / 2 + 1][(vector.length - 1) / 2 + 1];
        Object[][] vectorC = new Object[vector.length][vector.length];

        for(int i = 0; i < vector.length; i++){
            System.arraycopy(vector[i], 0, vectorC[i], 0, vector.length);
        }

        int ii = 0;
        int cc = 0;
        for (int i = 0; i <= (vector.length - 1) / 2; i++) {
            for (int c = 0; c <= (vector.length - 1) / 2; c++) {
                vector1[ii][cc] = vectorC[i][c];
                cc++;
            }
            cc = 0;
            ii++;
        }

        ii = 0;
        cc = 0;
        for (int i = (vector.length - 1) / 2; i <= (vector.length - 1); i++) {
            for (int c = (vector.length - 1) / 2; c <= (vector.length - 1); c++) {
                vector2[ii][cc] = vectorC[i][c];
                cc++;
            }
            cc = 0;
            ii++;
        }

        ii = 0;
        cc = 0;
        for (int i = 0; i <= (vector.length - 1) / 2; i++) {
            for (int c = (vector.length - 1) / 2; c <= (vector.length - 1); c++) {
                vector3[ii][cc] = vectorC[i][c];
                cc++;
            }
            cc = 0;
            ii++;
        }

        ii = 0;
        cc = 0;
        for (int i = (vector.length - 1) / 2; i <= (vector.length - 1); i++) {
            for (int c = 0; c <= (vector.length - 1) / 2; c++) {
                vector4[ii][cc] = vectorC[i][c];
                cc++;
            }
            cc = 0;
            ii++;
        }

        vector2 = Rotate.degree180(vector2);
        vector4 = Rotate.degree180(vector4);

        boolean check = true;

        if(!Arrays.deepEquals(vector1, vector2)){
             check = false;
        }
        if(!Arrays.deepEquals(vector3, vector4)){
             check = false;
        }

        symmetric = check;
    }

    public int getCountEqualsObjectsInRange(Object value, int xy, int toXY) {
        int result = 0;
        for (int i = xy; i < toXY; i++) {
            int cc = 0;
            for (int c = xy; c < toXY; c++) {
                if (vector[i][c].equals(value) || vector[i][c] == value) {
                    result++;
                }
            }
        }
        return result;
    }

}
