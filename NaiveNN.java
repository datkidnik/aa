package nearestNeigh;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is required to be implemented.  Naive approach implementation.
 *
 * @author Jeffrey, Youhan
 */
public class NaiveNN implements NearestNeigh{
    
    List<Point> naiveDataStructure = new ArrayList<Point>();

    @Override
    public void buildIndex(List<Point> points) {
        // To be implemented.     
        naiveDataStructure = points;
    }

    @Override
    public List<Point> search(Point searchTerm, int k) {
        // To be implemented.
        List<Point> searchArrayList = new ArrayList<Point>();
        List<Point> returnArrayList = new ArrayList<Point>();
        Point temp;
        int counter, i, j, min, n; ;
        int sizeOfDataStructure = naiveDataStructure.size(); 
        int sizeOfCatArray = searchArrayList.size();
          

        for(counter=0; counter<(sizeOfDataStructure); counter++){     

            if(naiveDataStructure.get(counter).cat == searchTerm.cat){
                searchArrayList.add(naiveDataStructure.get(counter));          
            }      
        }
        for (i=0; i<sizeOfCatArray-1; i++){  
            min = i;   //initialize to subscript of first element

            for(j=i+1; j<sizeOfCatArray-1; j++){   //locate smallest element between positions 1 and i.        
                if((searchTerm.distTo(searchArrayList.get(j))) < (searchTerm.distTo(searchArrayList.get(min))) ){
                    min = j;
                }                                    
            }
            if(min != i){
                temp = searchArrayList.get(min);   //swap smallest found with element in position i.
                searchArrayList.add(min, searchArrayList.get(i));
                searchArrayList.remove((min+1));
                searchArrayList.add(i, temp); 
                searchArrayList.remove((i+1));
            }       
        }
        
        for(n=0; n<k; n++){
            returnArrayList.add(n, searchArrayList.get(n));
        }
        return returnArrayList;
    }

    @Override
    public boolean addPoint(Point point) {
        // To be implemented. 
        int counter;
        int sizeOfDataStructure = naiveDataStructure.size();
        for(counter=0; counter<(sizeOfDataStructure-1); counter++){
            Point potential;
            potential = naiveDataStructure.get(counter);
            if(point.equals(potential)){    
                System.out.println("no");  
                return false;
            }
            else{
                System.out.println("yes");
                naiveDataStructure.add(point);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletePoint(Point point) {
        // To be implemented. 
        int counter;
        int sizeOfDataStructure = naiveDataStructure.size();
        for(counter=0; counter<(sizeOfDataStructure-1); counter++){
            Point potential;
            potential = naiveDataStructure.get(counter);
            if(point.equals(potential)){    
                System.out.println("yes");
                naiveDataStructure.remove(point);  
                return true;
            }
            else{
                System.out.println("no");
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean isPointIn(Point point) {
        // To be implemented.
        int counter;
        int sizeOfDataStructure = naiveDataStructure.size();
        for(counter=0; counter<(sizeOfDataStructure-1); counter++){
            Point potential;
            potential = naiveDataStructure.get(counter);
            if(point.equals(potential)){    
                System.out.println("yes"); 
                return true;
            }
            else{
                System.out.println("no");
                return false;
            }
        }
        return false;
    }

}
