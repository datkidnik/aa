package nearestNeigh;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is required to be implemented.  Kd-tree implementation.
 *
 * @author Jeffrey, Youhan
 */
public class KDTreeNN implements NearestNeigh{

    @Override
    public void buildIndex(List<Point> points) {
        // To be implemented.
    }

    @Override
    public List<Point> search(Point searchTerm, int k) {
        // To be implemented.
        return new ArrayList<Point>();
    }

    @Override
    public boolean addPoint(Point point) {
        // To be implemented.
        return false;
    }

    @Override
    public boolean deletePoint(Point point) {
        // To be implemented.
        return false;
    }

    @Override
    public boolean isPointIn(Point point) {
        // To be implemented.
        return false;
    }

    public class Node
    {
        private Node parent;
        private Node leftChild;
        private Node rightChild;
        private Point point;

        public Node setParent(Node parent){
            this.parent = parent;
        }

        public Node setLeftChild(Node leftChild){
            this.leftChild = leftChild;
        }

        public Node setRightChild(Node rightChild){
            this.rightChild = rightChild;
        }

        public Point setCurrPoint(Point obj){
            this.point = obj;
        }

        public void getPoint(){
            return point;
        }
               
    }
    
    public List<Point> sortTree(List<Point> unSortedList, int whichAxis){
        Point temp;
        int i, j, min;
        int sizeOfList = unSortedList.size();

        for (i=0; i<sizeOfList-1; i++){  
            min = i;   //initialize to subscript of first element

            for(j=i+1; j<sizeOfList-1; j++){   //locate smallest element between positions 1 and i.        
                
                if(unSortedList.get(j).lat < unSortedList.get(min).lat){
                    min = j;
                }   
                              
            }
            if(min != i){
                temp = unSortedList.get(min);   //swap smallest found with element in position i.
                unSortedList.add(min, unSortedList.get(i));
                unSortedList.remove((min+1));
                unSortedList.add(i, temp); 
                unSortedList.remove((i+1));
            }       
        }
        return unSortedList;
    }
}
