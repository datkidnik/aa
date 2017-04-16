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
        Node closestNode, headNode;
        List<Point> returnArrayList = new ArrayList<Point>();
        int numberOfNeighbours = (k-1);
        String thisCat = searchTerm.cat.name();

        if (thisCat == "RESTAURANT") {
            headNode = restaurantRoot;
        }
        else if (thisCat == "EDUCATION") {
            headNode = educationRoot;
        }
        else {
            headNode = hospitalRoot;
        }
        int i = 0;
        while(returnArrayList.size() < k){
            
        closestNode = findClosest(searchTerm, headNode, returnArrayList);
        returnArrayList.add(i, closestNode.point);
        i++;
        System.out.println("Node: "+ closestNode.point.id + " added to return array list. Distance: " + searchTerm.distTo(closestNode.getPoint()));
    }
        return returnArrayList;
    }

    
    public Node findClosest(Point searchTerm, Node parent, List<Point> winners) {
        // To be implemented.
        double x, y;
        x = searchTerm.lat;
            y = searchTerm.lon;
        int jon = 1;
        List<Point> returnArrayList = new ArrayList<Point>();
        Node tempNode = parent, leftChild = null, rightChild = null;
        //if (tempNode.getLeftChild().getLeftChild() == null) {
          //      System.out.println("left child  null");
            //}
        if (tempNode.getLeftChild() == null && tempNode.getRightChild() == null){
                return tempNode;
            }
            //System.out.println("Node has children");
            //x axis shit
            if (tempNode.getLeftChild()!=null) {
                leftChild = findClosest(searchTerm, tempNode.getLeftChild(), winners);    
            }
            if (tempNode.getRightChild()!=null) {
                rightChild = findClosest(searchTerm, tempNode.getRightChild(), winners);
            }
                
            if (winners.contains(leftChild.getPoint())) {
                //System.out.println("Node alreeady on list");
                leftChild=tempNode;
            }        
            if (winners.contains(rightChild.getPoint())) {
                //System.out.println("Node alreeady on list");
                rightChild=tempNode;
            }



        //System.out.println("tempNode distance: " + searchTerm.distTo(tempNode.getPoint()));
        Node closestNode = tempNode;
        //System.out.println("comparing: " + searchTerm.distTo(leftChild.getPoint())+ " with " + searchTerm.distTo(rightChild.getPoint()) + " with " + searchTerm.distTo(tempNode.getPoint()));
            if (searchTerm.distTo(leftChild.getPoint()) < searchTerm.distTo(tempNode.getPoint())) {
                //System.out.println("closest: " + searchTerm.distTo(leftChild.getPoint()));
                closestNode=leftChild;
            }
             if (searchTerm.distTo(rightChild.getPoint())<searchTerm.distTo(closestNode.getPoint())) {
                    //System.out.println("closest: " + searchTerm.distTo(rightChild.getPoint()));
                    closestNode=rightChild;
                }
                //System.out.println("returning: " + tempNode.getPoint());
                //System.out.println("distance: " + searchTerm.distTo(closestNode.getPoint()));
            return closestNode;

/*







        if(jon == 1){
            double x, y;
            boolean start, closestFound;
            
            start = true;
            closestFound = false;

            x = searchTerm.lat;
            y = searchTerm.lon;
            int i = 0;
            
            while(returnArrayList.size()<k){
                Node closestNode = null;
                while(closestFound==false){
                    
                if (tempNode.getLeftChild() == null && tempNode.getRightChild() == null){
                    
                    if(closestFound==false){
                        
                        if (returnArrayList.size() == 0) {
                            closestNode = tempNode;
                            closestFound=true;
                        }
                        
                        if (searchTerm.distTo(returnArrayList.get(i))<tempNode.getPoint().distTo(returnArrayList.get(i))) {
                            
                            closestFound=false;
                        }
                        
                    }
                }
                System.out.println("Node has children");
                //x axis shit
                if(tempNode.getDimension() == true){
                    System.out.println("going left");
                    if(x > tempNode.getPoint().lat){
                        tempNode=tempNode.getRightChild();
                    }
                    else{
                        tempNode=tempNode.getLeftChild();
                    }

                }
                //y axis shit
                else{
                    System.out.println("going right");
                    if(y > tempNode.getPoint().lon){
                        tempNode=tempNode.getRightChild();
                    }
                    else{
                        tempNode=tempNode.getLeftChild();
                    }
                } 
            }

            returnArrayList.add(i, closestNode.point);
            i++;
            }
            System.out.println("closest found");
        }
        else{
            tempNode = headNode;
        }
        

        System.out.println("number of nodes" + returnArrayList.size() + "; " + returnArrayList);
        */
        
    }













    //DEPTH FIRST SEARCH NEEDS TO BE IMPLEMENTED HERE TO ADD ALL NODES (UNDER THE CLOSEST NODE) INTO ARRAYLIST AND THEN RECALL BUILDTREE FUNCTION USING THE NODE THAT
    //WE WANT TO ADD AS THE ROOT
    @Override
    public boolean addPoint(Point point) {
        System.out.println("Adding point");
        Node tempNode;
        Point tempPoint;
        System.out.println(point.cat.name());
        String thisCat = point.cat.name();
        boolean done = false, dim;

        if (thisCat == "RESTAURANT") {
            tempNode = restaurantRoot;
        }
        else if (thisCat == "EDUCATION") {
            tempNode = educationRoot;
        }
        else{
            tempNode = hospitalRoot;
        }

        while(done == false){
            System.out.println("searching");
            System.out.println(tempNode.point);
            //check if no kids, if none; set dimension to opposite of parents and set node as left or right child
            if (tempNode.getLeftChild() == null && tempNode.getRightChild() == null) {
                System.out.println("no children");
                boolean parentSplit = tempNode.getParent().getDimension();
                if (parentSplit == true){
                    tempNode.setDimension(false);
                    if (tempNode.getPoint().lat > point.lat) {
                        tempNode.leftChild = new Node(point);
                        System.out.println("left child set");
                        System.out.println("parent: "+tempNode.point);
                        System.out.println("leftChild: "+tempNode.leftChild.point);
                        System.out.println("rightChild: "+tempNode.rightChild.point);
                        done = true;
                    } 
                }
                else{
                    tempNode.setDimension(true);
                    if (tempNode.getPoint().lon > point.lat) {
                        tempNode.rightChild = new Node(point);
                        System.out.println("right child set");
                        System.out.println("parent: "+tempNode.point);
                        System.out.println("leftChild: "+tempNode.leftChild);
                        System.out.println("rightChild: "+tempNode.rightChild);
                        done = true;
                    }
                }
            }
            if (done == true) {
                return true;
            }
            //if no left child set as left and vice versa for right
            System.out.println("lefty" + tempNode.getLeftChild().point);
            if (tempNode.getLeftChild() == null) {
                System.out.println("no lefty");
                tempNode.leftChild = new Node(point);
                return true;
            }
            System.out.println("righty" + tempNode.getRightChild().point);
            if (tempNode.getRightChild() == null) {
                System.out.println("no righty");
                tempNode.rightChild = new Node(point);
                return true;
            }


            //check dimension of node with children, follow correct path
            if (tempNode.getDimension() == true){
                if(tempNode.getPoint().lat > tempNode.getRightChild().getPoint().lat){
                    tempNode = tempNode.getRightChild();
                }
                else{
                    tempNode = tempNode.getLeftChild();
                }
            }
            else{
                if(tempNode.getPoint().lon > tempNode.getRightChild().getPoint().lon){
                    tempNode = tempNode.getRightChild();
                }
                else{
                    tempNode = tempNode.getLeftChild();
                }
            }
        }

        return true;
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
            return null;
        }
        else{
            // find the median from sorted points 
            if (parent != null) {
            if (bXDim == true) {
                parent.setDimension(true);    
            }
            else{
                parent.setDimension(false);
            }
            }
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
        private boolean dimension; 

        public Node(Point x){
            this.point = x;
            this.parent = null;
            this.leftChild = null;
            this.rightChild = null;
            this.dimension = true;
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

        public void setDimension(boolean x){
            this.dimension = x;
        }

        public boolean getDimension(){
            return this.dimension;
        }
               
    }

}
