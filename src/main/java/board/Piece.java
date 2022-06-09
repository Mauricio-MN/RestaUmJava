/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package board;

/**
 *
 * @author wghat
 */
public class Piece {

public Piece(int i){

    if(i == 0){
         valid = false;
         full = false;
    } else if(i == 1){
         valid = true;
         full = true;
    } else if(i == 2){
         valid = true;
         full = false;
    }


}

public boolean valid;
public boolean full;
    
}
