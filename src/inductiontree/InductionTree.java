/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inductiontree;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
*
* @author
* NAME:		  		   PANTHER ID:		 	SECTION:             DATE* 
* Juan Morales			6107367			1191 - Spring 2019		04/07/2019
*/


public class InductionTree {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        int tuple = 0;
        String[] tuples = new String[100];
        String newAge, newincome, newStudent, newCredit_rating, newBuys_computer, output;
        
        tuples[tuple++] = "age income student credit_rating buys_computer";
        tuples[tuple++] = "youth high no fair no";
        tuples[tuple++] = "youth high no excellent no";
        tuples[tuple++] = "middle_aged high no fair yes";
        tuples[tuple++] = "senior medium no fair yes";
        tuples[tuple++] = "senior low yes fair yes";
        tuples[tuple++] = "senior low yes excellent no";
        tuples[tuple++] = "middle_aged low yes excellent yes";
        tuples[tuple++] = "youth medium no fair no";
        tuples[tuple++] = "youth low yes fair yes";
        tuples[tuple++] = "senior medium yes fair yes";
        tuples[tuple++] = "youth medium yes excellent yes";
        tuples[tuple++] = "middle_aged medium no excellent yes";
        tuples[tuple++] = "middle_aged high yes fair yes";
        tuples[tuple++] = "senior medium no excellent no";
                
        newAge = JOptionPane.showInputDialog(null, "Please enter the new tuple's age as youth, middle_aged or senior");
        newincome = JOptionPane.showInputDialog(null, "Please enter the new tuple's income as low, high or medium");
        newStudent = JOptionPane.showInputDialog(null, "Please enter yes if tuple is a student, otherwise enter no");
        newCredit_rating = JOptionPane.showInputDialog(null, "Please enter the new tuple's credit_rating as fair or excellent");
       
        
        String newTuple = newAge + " " + newincome + " " + newStudent + " " + newCredit_rating;
        
        
        Function me = new Function();
        int status = me.getValues(tuples);
        if (status == 1)
            me.buildTree();
        output = "Will the user buy a computer?   " + me.getBranches(newTuple)+ " !!!";
        JOptionPane.showMessageDialog(null, output.toUpperCase());
        
    }
}
    
