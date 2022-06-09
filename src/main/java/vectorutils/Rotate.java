/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vectorutils;

/**
 *
 * @author wghat
 */
public class Rotate {
    public static Object[][] degree180(Object[][] vector){
        Object[][] vector4 = new Object[vector.length][vector.length];
        int ii = 0;
        int cc = 0;
        for(int i = vector.length - 1; i >= 0; i--){
            for(int c = vector.length - 1; c >= 0; c--){
                vector4[ii][cc] = vector[i][c];
                cc++;
            }
            cc = 0;
            ii++;
        }
        return vector4;
    }

    public static int[][] degree180(int[][] vector){
        int[][] vector4 = new int[vector.length][vector.length];
        int ii = 0;
        int cc = 0;
        for(int i = vector.length - 1; i >= 0; i--){
            for(int c = vector.length - 1; c >= 0; c--){
                vector4[ii][cc] = vector[i][c];
                cc++;
            }
            cc = 0;
            ii++;
        }
        return vector4;
    }
}
