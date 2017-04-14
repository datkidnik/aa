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
                if(whichAxis == 0){
                    if(unSortedList.get(j).lat < unSortedList.get(min).lat){
                        min = j;
                    } 
                }
                else if(whichAxis == 1){
                    if(unSortedList.get(j).lon < unSortedList.get(min).lon){
                        min = j;
                    } 
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
    
    public int findMedium(List<Point> findMediumForList){
        int numberOfPoints = findMediumForList.size();
        int s;
        if((numberOfPoints % 2) == 0){
            s = (numberOfPoints/2)+1;
            return s;
        }
        else{
            s = ((numberOfPoints-1)/2)+1;
            return s;
        }
    }
    
    public Node buildNode(Point median){
        Node newNode;
        newNode.setCurrPoint(median);
        return newNode;
    }
    
    public boolean flip(boolean ans){
        switch(ans){
            case true:
                return false;
            case false:
                return true;
        } 
    }

    public Node buildTree(List<Points> points, boolean bXDim) { 
        List<Point> sortedPoints = new ArrayList<Point>();
        int median;
        Node currParent, leftChild, rightChild;
        sortedPoints = sortTree(points, bXDim); 
        // find the median from sorted points 
        median = findMedium(sortedPoints); 
        // construct a node for the median point 
        currParent = buildNode(sortedPoints[median]); 
        leftChild = null; 
        rightChild = null; 
        // Check if there is a left partition (indexing starts at 0).  If so, recursively partition it
        if(median > 0){
            // flip() inverts the boolean value (effectively changing the dimension we split on next) 
            leftChild = buildTree(sortedPoints[0..median-1], flip(bXDim)); 
        } 
            
        // check if there is a right partition 
        if(median < length(points)){
            // flip() inverts the boolean value (effectively changing the dimension we split on next) 
            rightChild = buildTree(sortedPoints[median+1...length(points)-1], flip(bXDim)); 
        }       
        currParent.setLeftChild(leftChild); 
        currParent.setLeftChild(rightChild); 
 
        return currRoot; 
    }
}
