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
public class Search {
    //looks for a crossing and tells you whether it's positive
    public boolean crossing(ClosedCurve.Info[] arr, int num){
        for (ClosedCurve.Info i: arr){
            if (i.num == num) return i.right;
        }
        System.out.println("no crossing found with id " + num);
        return false;
    }
}
