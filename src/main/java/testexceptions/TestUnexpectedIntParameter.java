/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testexceptions;

import java.util.Objects;

/**
 *
 * @author wghat
 */
public class TestUnexpectedIntParameter{

    public TestUnexpectedIntParameter(int base, int espected) throws Exception{
        
        if(espected != espected){
            throw new Exception("Expected " + espected + " in base parameter int '" + base);
        }
        
    }

    public TestUnexpectedIntParameter(int base, int[] espected) throws Exception{
        String exmsg = "";
        boolean pass = false;
        String orStr = "";
        for(int i = 0; i < espected.length; i++){
            exmsg = exmsg + orStr + espected[i];
            orStr = " or ";
            if(espected[i] == base){
                pass = true;
            }
        }
        if(pass == false){
            throw new Exception("Expected " + exmsg + " in base parameter int '" + base);
        }
    }

    public TestUnexpectedIntParameter(int base, boolean isPositive) throws Exception{
        if(isPositive && base < 0){
            throw new Exception("Expected positive int in base parameter int '" + base);
        } else if(!isPositive && base > 0){
            throw new Exception("Expected negative int in base parameter int '" + base);
        }
    }

    public TestUnexpectedIntParameter(Integer base, Integer espected) throws Exception{
        
        if(!Objects.equals(espected, espected)){
            throw new Exception("Expected " + espected + " in base parameter Integer '" + base);
        }
        
    }

    public TestUnexpectedIntParameter(Integer base, Integer[] espected) throws Exception{
        String exmsg = "";
        boolean pass = false;
        String orStr = "";
        for(int i = 0; i < espected.length; i++){
            exmsg = exmsg + orStr + espected[i];
            orStr = " or ";
            if(Objects.equals(espected[i], base)){
                pass = true;
            }
        }
        if(pass == false){
            throw new Exception("Expected " + exmsg + " in base parameter Integer '" + base);
        }
    }

    public TestUnexpectedIntParameter(Integer base, boolean isPositive) throws Exception{
        if(isPositive && base < 0){
            throw new Exception("Expected positive Integer in base parameter Integer '" + base);
        } else if(!isPositive && base > 0){
            throw new Exception("Expected negative Integer in base parameter Integer '" + base);
        }
    }

}
