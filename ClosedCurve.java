/*
 * Aditi Talati - 22 July 2018
 * COSMOS Research Project - given one gauss code of a knot or link, compute all
                             other possible Gauss codes
 */
package possiblegausscodes;

/**
 *
 * @author Aditi
 */

//add exceptions to throw if incorrect code
public class ClosedCurve {
    private String stringCode; //stores the Gauss code
    private Info[] usefulCode; //stores the code for ease of access
    private boolean[][] orientations; //possible crossing orientation combos
                                      // true if +, false if -
    
    public ClosedCurve(String gauss){
        stringCode = gauss;
        int numBreaks = 0; //find the number of breaks between links in order to
                           //separate the data into a readable form correctly
        int linkIndex = stringCode.indexOf('|');
        while (linkIndex != -1){
            numBreaks ++;
            linkIndex = stringCode.indexOf('|',linkIndex+1);
        }
        
        //store the information in a readable format
        int length = 0;
        if (stringCode.length() - numBreaks <= 54) 
            length = (stringCode.length()-numBreaks)/3;
        else if (stringCode.length() - numBreaks <= 594)
            length = 18 + (stringCode.length() - numBreaks - 54)/4;
        usefulCode = new Info[length];
        //System.out.println("length: " + length);
        int i = 0;
        int index = 0;
        while(i<stringCode.length()){
            boolean above;
            int num;
            boolean end;
            boolean right;
            
            //set above value
            if(stringCode.charAt(i) == 'a') above = true;
            else if(stringCode.charAt(i) == 'b') above = false;
            else {
                System.out.println(stringCode.charAt(i) + " is not a or b");
                above = false;
            }
            i++;
            
            //set crossing id number
            num = Integer.parseInt(stringCode.substring(i, i+1));
            i++;
            try {
                int unitsDigit = Integer.parseInt(stringCode.substring(i, i+1));
                num *= 10;
                num+= unitsDigit;
                i++;
                //System.out.println(num);
            } catch (NumberFormatException e){}
            
            //set "right" value (true if positive crossing, false if negative)
            if(stringCode.charAt(i) == '+') right = true;
            else if(stringCode.charAt(i) == '-') right = false;
            else {
                System.out.println(stringCode.charAt(i) + " is not + or -");
                right = false;
            }
            i++;
            
            //check if it's the end of a link
            if(i<stringCode.length() && stringCode.charAt(i) == '|'){
                end = true;
                i++;
            } else {
                end = false;
            }
            
            //save as Info object
            usefulCode[index] = new Info(above, num, end, right);
            index ++;
            //System.out.println(index-1 + ": " + usefulCode[index-1]);
        }
        
        //create the array of possible orientations
        //each row is one closed curve with each term in the row being
        //a crossing
        orientations = new boolean[(int)Math.pow(2, usefulCode.length/2)]
                                   [usefulCode.length/2];
        for (int j = 0; j < orientations[0].length; j++){
            orientations[0][j] = crossing(j+1);
        }
        
        testPossibilities(0, orientations.length, 0, 0);
    }
    
    //lists out the Gauss codes of all possible closed curves
    public void testPossibilities(int beginning, int end, int positionToChange,
                                  int prevIndex){
        int currentIndex = (beginning + end)/2;
        if(positionToChange<orientations[currentIndex].length){
            //copies all the values from the previous one
            for(int i = 0; i < orientations[currentIndex].length; i++){
                orientations[currentIndex][i] = orientations[prevIndex][i];
            }
            //flips the crossing for one position
            orientations[currentIndex][positionToChange] = 
                    !orientations[prevIndex][positionToChange];
            
            testPossibilities(beginning, currentIndex, positionToChange + 1, 
                                                       beginning);
            testPossibilities(currentIndex, end, positionToChange + 1, 
                                                 currentIndex);
                    
        }
    }
    
    private String[] writtenCodes(){
        String[] writtenCodes = new String[orientations.length];
        for (int i = 0; i < writtenCodes.length; i++){
            writtenCodes[i] = "";
            for (int j = 0; j < usefulCode.length; j++){
                usefulCode[j].swapOrientation(orientations[i]
                                                        [usefulCode[j].num-1]);
                writtenCodes[i] += usefulCode[j];
            }
        }
        return writtenCodes;
    }
    
    //looks for a crossing and tells you whether it's positive
    private boolean crossing(int id){
        for (Info i: usefulCode){
            if (i.num == id) return i.right;
        }
        System.out.println("no crossing found with id " + id);
        return false;
    }
    
    public String toString(){
        String[] output = writtenCodes();
        String outputString = "";
        for (String s: output){
            outputString += "$" + s + "$ \\\\ \\hline \n";
        }
        return outputString;
    }
    
    class Info{ //stores the information about each term in the Gauss code 
        boolean above; //true if gauss code for that term is "a"
        int num; //the id number of the crossing 
        boolean end; //true if the term is right before a link separator
        boolean right; //true if it is a right hand (+) crossing
        public Info(boolean above, int num, boolean end, boolean right){
            this.above = above;
            this.num = num;
            this.end = end;
            this.right = right;
        }
        public void swapOrientation(boolean right){
            //switches orientation if it needs to
            if (this.right != right){
                this.right = right;
                above = !above;
            }
        }
        public String toString(){
            String output = "";
            
            if (above) output += "a";
            else output += "b";
            
            output += num;
            
            if (right) output += "+";
            else output += "-";
            
            if(end) output += "|";
            
            return output;
        }
    }
}
