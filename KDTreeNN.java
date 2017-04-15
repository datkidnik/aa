package nearestNeigh;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is required to be implemented.  Kd-tree implementation.
 *
 * @author Jeffrey, Youhan
 */
public class KDTreeNN implements NearestNeigh{

    List<Point> restaurantStructure = new ArrayList<Point>();
    List<Point> hospitalStructure = new ArrayList<Point>();
    List<Point> educationStructure = new ArrayList<Point>();
    Node parent, hospitalRoot, restaurantRoot, educationRoot;

    @Override
    public void buildIndex(List<Point> points) {
        List<Point> sortedPoints = new ArrayList<Point>();
        int length, i, median;
        
        length = points.size();
        for(i=0; i<length; i++){
            switch(points.get(i).cat){
                case RESTAURANT:
                    restaurantStructure.add(points.get(i));
                    break;
                case EDUCATION:
                    educationStructure.add(points.get(i));
                    break;
                case HOSPITAL:
                    hospitalStructure.add(points.get(i));
                    break;
                default:
                    break;
            }
        }
        
          
        restaurantRoot = buildTree(restaurantStructure, true, null);
        System.out.println("work");
        System.out.println(restaurantRoot.getPoint());
        System.out.println(restaurantRoot.getLeftChild().getPoint());
        System.out.println(restaurantRoot.getRightChild().getPoint());
          
        educationRoot = buildTree(educationStructure, true, null);
        
         
        hospitalRoot = buildTree(hospitalStructure, true, null);
     
    }

    @Override
    public List<Point> search(Point searchTerm, int k) {
        // To be implemented.
        int jon = 1;
        List<Point> returnArrayList = new ArrayList<Point>();
        Node tempNode, headNode;
        int numberOfNeighbours = (k-1);
        if(/*searchTerm.cat == RESTAURANT*/ jon == 1){
            double x, y;
            boolean start, looper;
            
            start = true;
            looper = false;

            x = searchTerm.lat;
            y = searchTerm.lon;
            headNode = restaurantRoot;
            tempNode = restaurantRoot;
            while(looper == false){
                //x axis shit
                if(start == true){
                    if(x > tempNode.getPoint().lat){
                        if(tempNode.getRightChild() == null){
                            looper = true;
                        }
                        else{
                            tempNode = tempNode.getRightChild();
                            start = false;
                        }
                    }
                    else if(x < tempNode.getPoint().lat){
                        if(tempNode.getLeftChild() == null){
                            looper = true;
                        }
                        else{
                            tempNode = tempNode.getLeftChild();
                            start = false;
                        }
                    }
                    else{
                        looper = true;
                    }
                }
                //y axis shit
                else{
                    if(y > tempNode.getPoint().lon){
                        if(tempNode.getRightChild() == null){
                            looper = true;
                        }
                        else{
                            tempNode = tempNode.getRightChild();
                            start = true;
                        }
                    }
                    else if(y < tempNode.getPoint().lon){
                        if(tempNode.getLeftChild() == null){
                            looper = true;
                        }
                        else{
                            tempNode = tempNode.getLeftChild();
                            start = true;
                        }
                    }
                    else{
                        looper = true;
                    }
                } 
            }
            while(numberOfNeighbours != 0){           
                Node closestNode = tempNode;
                returnArrayList.add(closestNode.getPoint());
                if(closestNode.getParent() != null){
                    if(returnArrayList.contains(closestNode.getParent()) == false){
                        returnArrayList.add(closestNode.getParent().getPoint());
                        numberOfNeighbours--;
                    }  
                }
                if(closestNode.getLeftChild() != null){
                    returnArrayList.add(closestNode.getLeftChild().getPoint());
                    numberOfNeighbours--;
                }
                if(closestNode.getRightChild() != null){
                    returnArrayList.add(closestNode.getRightChild().getPoint());
                    numberOfNeighbours--;
                }
                closestNode = closestNode.getLeftChild();
            }
        }
        else{
            tempNode = restaurantRoot;
        }
        int sizeeee = returnArrayList.size();
        int t;
        for(t=0; t<sizeeee; t++){
            System.out.println(returnArrayList.get(t));
        }
        
        return returnArrayList;
    }

    //DEPTH FIRST SEARCH NEEDS TO BE IMPLEMENTED HERE TO ADD ALL NODES INTO ARRAYLIST AND THEN RECALL BUILDTREE FUNCTION USING THE NODE THAT
    //WE WANT TO ADD AS THE ROOT
    @Override
    public boolean addPoint(Point point) {
        // To be implemented.
        List<Point> returnArrayList = new ArrayList<Point>();
        Node tempNode, headNode;
     

        double x, y;
        boolean start, looper, looper2, looper3;
        
        start = true;
        looper = false;
        looper2 = false;
        looper3 = false;

        x = point.lat;
        y = point.lon;
        headNode = restaurantRoot;
        tempNode = restaurantRoot;
        while(looper == false){
            //x axis shit
            if(start == true){
                if(x > tempNode.getPoint().lat){
                    if(tempNode.getRightChild() == null){
                        looper = true;
                    }
                    else{
                        tempNode = tempNode.getRightChild();
                        start = false;
                    }
                }
                else if(x < tempNode.getPoint().lat){
                    if(tempNode.getLeftChild() == null){
                        looper = true;
                    }
                    else{
                        tempNode = tempNode.getLeftChild();
                        start = false;
                    }
                }
                else{
                    looper = true;
                }
            }
            //y axis shit
            else{
                if(y > tempNode.getPoint().lon){
                    if(tempNode.getRightChild() == null){
                        looper = true;
                    }
                    else{
                        tempNode = tempNode.getRightChild();
                        start = true;
                    }
                }
                else if(y < tempNode.getPoint().lon){
                    if(tempNode.getLeftChild() == null){
                        looper = true;
                    }
                    else{
                        tempNode = tempNode.getLeftChild();
                        start = true;
                    }
                }
                else{
                    looper = true;
                }
            } 
        }

        headNode = tempNode;

        while(looper2 == false;){

            if(tempNode.getRightChild() != null){
                returnArrayList.add(tempNode.getRightChild().getPoint());
            }
            if(tempNode.getLeftChild() != null){
                returnArrayList.add(tempNode.getLeftChild().getPoint());
                tempNode = getLeftChild();
            } 
            if(tempNode.getLeftChild() != null){
                looper2 = true;
            }
        }

        tempNode = headNode;

        while(looper3 == false;){

            if(tempNode.getRightChild() != null){
                returnArrayList.add(tempNode.getRightChild().getPoint());
                tempNode = getLeftChild();
            }
            else{
                looper2 = true;
            }
        }

        return false;
    }

    public
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

    
    
    public List<Point> sortTree(List<Point> unSortedList, boolean bXDim){
        Point temp;
        int i, j, min;
        int sizeOfList = unSortedList.size();

        for (i=0; i<sizeOfList-1; i++){  
            min = i;   //initialize to subscript of first element

            for(j=i+1; j<sizeOfList-1; j++){   //locate smallest element between positions 1 and i.        
                if(bXDim == true){
                    if(unSortedList.get(j).lat < unSortedList.get(min).lat){
                        min = j;
                    } 
                }
                else if(bXDim == false){
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
            s = (numberOfPoints/2);
            return s;
        }
        else{
            s = ((numberOfPoints-1)/2);
            return s;
        }
    }
    
    /*public buildNode(Point median){
        newNode.setParent(null);
        newNode.setLeftChild(null);
        newNode.setRightChild(null);
        newNode.setCurrPoint(median);
    }*/

    public boolean flip(boolean ans){
        if(ans == true){
            return false;
        }
        else if(ans == false){
            return true;
        } 
        else{
            return false;
        }
    }

    public Node buildTree(List<Point> points, boolean bXDim, Node parent){ 
        List<Point> sortedPoints = new ArrayList<Point>();
        List<Point> tempTree = new ArrayList<Point>();
        List<Point> leftTree = new ArrayList<Point>();
        List<Point> rightTree = new ArrayList<Point>();
        int median, sizeOfTree;
        Node currParent, leftChild, rightChild, currRoot;
        sortedPoints = sortTree(points, bXDim);
        sizeOfTree = sortedPoints.size();

        if((points.size()) == 0){
            return parent;
        }
        else{
            // find the median from sorted points 
            median = findMedium(sortedPoints); 

            // construct a node for the median point 
            currParent = new Node(sortedPoints.get(median));
            if (sortedPoints.size() == 2) {        
                leftChild = new Node(sortedPoints.get(0));
                rightChild = new Node(sortedPoints.get(1));
                currParent.setRightChild(rightChild);
                currParent.setLeftChild(leftChild);
                return currParent; 
            }
            else{
                if(parent != null){
                    currParent.setParent(parent); 
                } 
                leftChild = null; 
                rightChild = null; 

                

                // Check if there is a left partition (indexing starts at 0).  If so, recursively partition it
                
                // flip() inverts the boolean value (effectively changing the dimension we split on next) 
                leftTree = sortedPoints;
                leftTree = leftTree.subList(0, median);
                leftChild = buildTree(leftTree, flip(bXDim), currParent); 
                
                    
                // check if there is a right partition 
               
                // flip() inverts the boolean value (effectively changing the dimension we split on next) 
                rightTree = sortedPoints;
                rightTree = rightTree.subList(median+1, sizeOfTree);
                rightChild = buildTree(rightTree, flip(bXDim), currParent); 
                     
                currParent.setLeftChild(leftChild); 
                currParent.setRightChild(rightChild); 
         
                return currParent;
            }
            
        }
         
    }

public class Node
    {
        private Node parent;
        private Node leftChild;
        private Node rightChild;
        private Point point;

        public Node(Point x){
            this.point = x;
            this.parent = null;
            this.leftChild = null;
            this.rightChild = null;
        }

        public void setParent(Node parent){
            this.parent = parent;
        }

        public void setLeftChild(Node leftChild){
            this.leftChild = leftChild;
        }

        public void setRightChild(Node rightChild){
            this.rightChild = rightChild;
        }

        public void setCurrPoint(Point obj){
            this.point = obj;
        }

        public Point getPoint(){
            return point;
        }

        public Node getLeftChild(){
            return leftChild;
        }

        public Node getRightChild(){
            return rightChild;
        }

        public Node getParent(){
            return parent;
        }
               
    }

}
