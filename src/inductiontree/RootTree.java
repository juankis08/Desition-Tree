/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inductiontree;

import java.util.Vector;

/**
*
* @author
* NAME:		  		   PANTHER ID:		 	SECTION:             DATE* 
* Juan Morales			6107367			1191 - Spring 2019		04/07/2019
*/


public class RootTree {
    
	public RootTree []childrenNode;

    public RootTree parentNode;
	
	public double head;

    public Vector data;

    public int newAttribute;

    public int newValue;    

    public RootTree() {

        data = new Vector();
    }
   
}
