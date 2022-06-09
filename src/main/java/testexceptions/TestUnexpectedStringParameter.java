/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testexceptions;

/**
 *
 * @author wghat
 */
public class TestUnexpectedStringParameter{
    public TestUnexpectedStringParameter(String base, String espected) throws Exception{
        
        if(!espected.equals(base)){
            throw new Exception("Expected " + espected + " in base parameter String '" + base);
        }
        
    }
    public TestUnexpectedStringParameter(String base, String[] espected) throws Exception{
        String exmsg = "";
        boolean pass = false;
        String orStr = "";
        for(int i = 0; i < espected.length; i++){
            exmsg = exmsg + orStr + espected[i];
            orStr = " or ";
            if(espected[i].equals(base)){
                pass = true;
            }
        }
        if(pass == false){
            throw new Exception("Expected " + exmsg + " in base parameter String '" + base);
        }
    }
}
