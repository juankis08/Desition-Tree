/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inductiontree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

/**
*
* @author
* NAME:		  		   PANTHER ID:		 	SECTION:             DATE
* Juan Morales			6107367			1191 - Spring 2019		04/07/2019
*/
public class Function {
    int counter;

    String []functionAttribute;

    Vector []vectors;

    RootTree root = new RootTree();

    public int getSymbolValue(int attribute, String symbol) {

        int index = vectors[attribute].indexOf(symbol);

        if (index < 0) {

            vectors[attribute].addElement(symbol);

            return vectors[attribute].size() -1;

        }

        return index;

    }
    
    public int []getAttributes(Vector data, int attribute) {

        Vector values = new Vector();

        int num = data.size();

        for (int i=0; i< num; i++) {

            DataSet point = (DataSet)data.elementAt(i);

            String symbol =

            (String)vectors[attribute].elementAt(point.attValue[attribute] );

            int index = values.indexOf(symbol);

            if (index < 0) {

                values.addElement(symbol);

            }

        }
        int []array = new int[values.size()];

        for (int i=0; i< array.length; i++) {

            String symbol = (String)values.elementAt(i);

            array[i] = vectors[attribute].indexOf(symbol);

        }

        values = null;

        return array;

    }
    
    public Vector getDate(Vector data, int attribute, int value) {

        Vector subset = new Vector();

        int num = data.size();

        for (int i=0; i< num; i++) {

            DataSet point = (DataSet)data.elementAt(i);

            if (point.attValue[attribute] == value) 
                subset.addElement(point);

        }

        return subset;

    }
    
    public double findDataPoint(Vector data) {

        int numdata = data.size();

        if (numdata == 0) 
            return 0;

        int attribute = counter-1;

        int numvalues = vectors[attribute].size();

        double sum = 0;

        for (int i=0; i< numvalues; i++) {

            int count=0;

            for (int j=0; j< numdata; j++) {

                DataSet point = (DataSet)data.elementAt(j);

                if (point.attValue[attribute] == i) 
                    count++;

            }

            double probability = 1.*count/numdata;

            if (count > 0) 
                sum += -probability*Math.log(probability);

        }

        return sum;

    }

    public boolean classifyValue(RootTree node, int attribute) {

        if (node.childrenNode != null) {

            if (node.newAttribute == attribute )

                return true;

        }

        if (node.parentNode == null) 
            return false;

        return classifyValue(node.parentNode, attribute);

    }

    public void trainData(RootTree node) {

        double bestLeaf=0;

        boolean selected=false;

        int selectedLeaf=0;

        int numdata = node.data.size();

        int numinputattributes = counter-1;

        node.head = findDataPoint(node.data);

        if (node.head == 0) return;



        for (int i=0; i< numinputattributes; i++) {

            int numvalues = vectors.length;

            if ( classifyValue(node, i) ) continue;

            double average = 0;

            for (int j=0; j< numvalues; j++) {

                Vector subset = getDate(node.data, i, j);

                if (subset.size() == 0) continue;

                double newpoint = findDataPoint(subset);

                average += newpoint * subset.size();

            }

            average = average / numdata; 

            if (selected == false) {

                selected = true;

                bestLeaf = average;

                selectedLeaf = i;

            } else {

                if (average < bestLeaf) {

                    selected = true;

                    bestLeaf = average;

                    selectedLeaf = i;

                }

            }

        }

        if (selected == false) return;

        int numvalues = vectors[selectedLeaf].size();

        node.newAttribute = selectedLeaf;

        node.childrenNode = new RootTree [numvalues];

        for (int j=0; j< numvalues; j++) {

            node.childrenNode[j] = new RootTree();

            node.childrenNode[j].parentNode = node;

            node.childrenNode[j].data = getDate(node.data,

            selectedLeaf, j);

            node.childrenNode[j].newValue = j;

        }


        for (int j=0; j< numvalues; j++) {

            trainData(node.childrenNode[j]);

        }


        node.data = null;

    }
    
     public int getValues(String[] tuples){

        String input;
        

        input = tuples[0];
     
        StringTokenizer tokenizer = new StringTokenizer(input);

        counter = tokenizer.countTokens();

        if (counter <= 1) {
            System.err.println( "Wrong val ");
            return 0;
        }
        vectors = new Vector[counter];

        for (int i=0; i < counter; i++) 
            vectors[i] = new Vector();

        functionAttribute = new String[counter];

        for (int i=0; i < counter; i++) {

            functionAttribute[i] = tokenizer.nextToken();

        }

        for (int tuple = 1; tuple < tuples.length; tuple++) {

            input = tuples[tuple];

            if (input == null) break;
            tokenizer = new StringTokenizer(input);
            int numtokens = tokenizer.countTokens();

            if (numtokens != counter) {

                System.err.println( "Too many arguments in line" + tuple);
                return 0;
            }
            DataSet point = new DataSet(counter);

            for (int i=0; i < counter; i++) {

                point.attValue[i] = getSymbolValue(i, tokenizer.nextToken());

            }

            root.data.addElement(point);

        }
            return 1;
    }
        
    public void display(RootTree node, String tab) {

        int outputattr = counter-1;

        if (node.childrenNode == null) {

        int []values = getAttributes(node.data, outputattr );

        if (values.length == 1) {

        System.out.println(tab + "\t" + functionAttribute[outputattr] + " -----> \"" +

        vectors[outputattr].elementAt(values[0]) + "\";");

        return;

        }

        System.out.print(tab + "\t" + functionAttribute[outputattr] + " = {");

        for (int i=0; i < values.length; i++) {

            System.out.print("\"" + vectors[outputattr].elementAt(values[i]) + "\" ");

            if ( i != values.length-1 ) 
                System.out.print( " , " );

        }

        System.out.println( " };");

        return;

        }

        int numvalues = node.childrenNode.length;

        for (int i=0; i < numvalues; i++) {

            System.out.println(tab + "if(" +

            functionAttribute[node.newAttribute] + " == \"" +

            vectors[node.newAttribute].elementAt(i)

            + "\") {" );

            display(node.childrenNode[i], tab + "\t");

            if (i != numvalues-1) 
                System.out.print(tab + "} else ");

            else 
                System.out.println(tab + "}");

        }

    }
    
    public String getBranches(String tuple){
        
        int outputattr = counter-1;
        RootTree temp = root;
        int element = 0;
       // 
        for (int i = 0; i < vectors[temp.newAttribute].size(); i++){
            if (tuple.contains(vectors[temp.newAttribute].elementAt(i).toString())){
                element = i;
            }
        }
        temp = temp.childrenNode[element];
        
        if (temp.childrenNode == null) {
            int []values = getAttributes(temp.data, outputattr );  
            return vectors[outputattr].elementAt(values[0]).toString();
        }
                
        for (int i = 0; i < vectors[temp.newAttribute].size(); i++){
            if (tuple.contains(vectors[temp.newAttribute].elementAt(i).toString())){
                element = i;
            }
        }
         temp = temp.childrenNode[element];
        
        if (temp.childrenNode == null) {
            int []values = getAttributes(temp.data, outputattr );  
            return vectors[outputattr].elementAt(values[0]).toString();
        }
                
        for (int i = 0; i < vectors[temp.newAttribute].size(); i++){
            if (tuple.contains(vectors[temp.newAttribute].elementAt(i).toString())){
                element = i;
            }
        }
              temp = temp.childrenNode[element];
        
        if (temp.childrenNode == null) {
            int []values = getAttributes(temp.data, outputattr );  
            return vectors[outputattr].elementAt(values[0]).toString();
        }
                
        
        return "no";
    }

        public void buildTree() {

        trainData(root);

        display(root, "");

    }




}
