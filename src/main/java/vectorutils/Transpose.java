/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vectorutils;

/**
 *
 * @author wghat
 */
public class Transpose {

public static int[][] transposeMatrix(int[][] matrix){
    int m = matrix.length;
    int n = matrix[0].length;

    int[][] transposedMatrix = new int[n][m];

    for(int x = 0; x < n; x++) {
        for(int y = 0; y < m; y++) {
            transposedMatrix[x][y] = matrix[y][x];
        }
    }

    return transposedMatrix;
}

public static Object[][] transposeMatrix(Object[][] matrix){
    int m = matrix.length;
    int n = matrix[0].length;

    Object[][] transposedMatrix = new Object[n][m];

    for(int x = 0; x < n; x++) {
        for(int y = 0; y < m; y++) {
            transposedMatrix[x][y] = matrix[y][x];
        }
    }

    return transposedMatrix;
}
    
}
