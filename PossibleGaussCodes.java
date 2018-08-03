/*
 * Aditi Talati - 22 July 2018
 * COSMOS Research Project - given one gauss code of a knot or link, compute all
                             other possible Gauss codes
 */
package possiblegausscodes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Aditi
 */
public class PossibleGaussCodes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please enter the pathname to your input file: ");
        String file = userInput.nextLine();
        
        Scanner input = null;
        PrintWriter output = null;
        
        boolean correctFile = false;
        while (!correctFile){
            try {
                input = new Scanner (new FileInputStream(file));
                correctFile = true;
            } catch (FileNotFoundException e){
                System.out.println("Input file not found.");
                System.out.print("Please enter a valid file pathname: ");
                file = userInput.nextLine();
                System.exit(0);
            }
        }
        try {
            output = new PrintWriter (new FileOutputStream("possiblecodes.txt"));
        } catch (FileNotFoundException e){
            System.out.println("Output file not found.");
            System.exit(0);
        }
        
        ClosedCurve c = new ClosedCurve(input.next());
        output.print(c);
        output.close();
    }
    
    
}
