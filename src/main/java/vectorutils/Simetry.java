/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vectorutils;

import java.util.Arrays;

/**
 *
 * @author wghat
 */
public class Simetry<K> {

    public Simetry(K[][] vect){
    
        vector = vect;
    }

    private K[][] vector;

    public boolean verifySlashBackSlashSimetry() {

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

        return check;
    }

}
