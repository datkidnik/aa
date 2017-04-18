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

//function for setting the root node if it is deleted
    public void setRoot(Node newRoot, String cat){
        Category category;
        category = Point.parseCat(cat);
        switch(category){
                case RESTAURANT:
                    restaurantRoot = newRoot;
                    break;
                case EDUCATION:
                    educationRoot = newRoot;
                    break;
                case HOSPITAL:
                    hospitalRoot = newRoot;
                    break;
                default:
                    break;
            }
    }


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
          
        educationRoot = buildTree(educationStructure, true, null);
         
        hospitalRoot = buildTree(hospitalStructure, true, null);
     
    }



    //search for a giving point, for this we find the right tree category and set the root accordingly, we then call findClosest 
    //which finds the closest node to the point provided. if this nodes point matchs the given point the search has been successful
    @Override
    public List<Point> search(Point searchTerm, int k) {
        Node closestNode, headNode;
        List<Point> returnArrayList = new ArrayList<Point>();
        int numberOfNeighbours = (k-1);
        int arrySize;
        String thisCat = searchTerm.cat.name();

        if (thisCat == "RESTAURANT") {
            headNode = restaurantRoot;
            arrySize = restaurantStructure.size();
        }
        else if (thisCat == "EDUCATION") {
            headNode = educationRoot;
            arrySize = educationStructure.size();
        }
        else {
            headNode = hospitalRoot;
            arrySize = hospitalStructure.size();
        }
        int i = 0;
        int use;
        if(k > arrySize){
            use = arrySize;
        }
        else{
            use = k;
        }
        while(returnArrayList.size() < use){
            
        closestNode = findClosest(searchTerm, headNode, returnArrayList);
        returnArrayList.add(i, closestNode.point);
        i++;
        
    }
        return returnArrayList;
    }

    //findclosest searches through the tree much the same way it is constructed, but on the way back is compares the parent node
    //to its children and sets the closest to tempNode. this permeates back up the chain and is returned by the function as the 
    //closest node to the search
    public Node findClosest(Point searchTerm, Node parent, List<Point> winners) {
        
        System.out.println(parent.getParent());

        // To be implemented.
        double x, y;
        x = searchTerm.lat;
            y = searchTerm.lon;
        int jon = 1;
        List<Point> returnArrayList = new ArrayList<Point>();
        Node tempNode = parent, leftChild = null, rightChild = null;
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
            if (tempNode.getLeftChild()==null) {
                leftChild = rightChild;
            }
            if (tempNode.getRightChild()==null) {
                rightChild = leftChild;
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
               
            return closestNode;
        
    }













    //DEPTH FIRST SEARCH NEEDS TO BE IMPLEMENTED HERE TO ADD ALL NODES (UNDER THE CLOSEST NODE) INTO ARRAYLIST AND THEN RECALL BUILDTREE FUNCTION USING THE NODE THAT
    //WE WANT TO ADD AS THE ROOT

    //addpoint searches through the tree looing for the closest point to branch the given point off. if it comes across the given point 
    //it returns false, but otherwise it finds the closest leaf node and splices the given node onto it
    @Override
    public boolean addPoint(Point point) {
        //System.out.println("Adding point");
        Node tempNode;
        Point tempPoint;
        //System.out.println(point.cat.name());
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
            if (tempNode.getPoint().equals(point)) {
                //System.out.println("Point already exsists");
                return false;    
            }
            System.out.println(tempNode.point);
            System.out.println(tempNode.getParent().point);
            //check if no kids, if none; set dimension to opposite of parents and set node as left or right child
            if (tempNode.getLeftChild() == null && tempNode.getRightChild() == null) {
                //System.out.println("no children")
                
                boolean parentSplit = tempNode.getParent().getDimension();
                if (parentSplit == true){
                    tempNode.setDimension(false);
                    if (tempNode.getPoint().lat > point.lat) {
                        tempNode.leftChild = new Node(point);
                        tempNode.leftChild.setParent(tempNode);
                        done = true;
                    } 
                }
                else{
                    tempNode.setDimension(true);
                    if (tempNode.getPoint().lon > point.lat) {
                        tempNode.rightChild = new Node(point);
                        tempNode.rightChild.setParent(tempNode);
                        done = true;
                    }
                }
            }
            if (done == true) {
                return true;
            }
            //if no left child set as left and vice versa for right
            
            


            //check dimension of node with children, follow correct path
            if (tempNode.getDimension() == true){
                if (tempNode.getLeftChild() == null) {
                tempNode.leftChild = new Node(point);
                tempNode.leftChild.setParent(tempNode);
                return true;
            }
            
            if (tempNode.getRightChild() == null) {
                tempNode.rightChild = new Node(point);
                tempNode.rightChild.setParent(tempNode);
                return true;
            }
                if(tempNode.getPoint().lat > tempNode.getRightChild().getPoint().lat){
                    tempNode = tempNode.getRightChild();
                }
                else{
                    tempNode = tempNode.getLeftChild();
                }
            }
            else{
                if (tempNode.getLeftChild() == null) {
                tempNode.leftChild = new Node(point);
                tempNode.leftChild.setParent(tempNode);
                return true;
            }
            
            if (tempNode.getRightChild() == null) {
                tempNode.rightChild = new Node(point);
                tempNode.rightChild.setParent(tempNode);
                return true;
            }
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

    //delete point also uses closest node to find the closest point to the one given, if the closest point is the point for deletion it 
    //is removed. it doesnt quite set the parents properly afterwards though for some reason. it gives a null pointer exception when 
    //calling getleftnode() on the parent node
    @Override
    public boolean deletePoint(Point point) {
        //System.out.println("deleting point");
        Node closestNode, headNode;

        boolean bool;
        List<Point> arrayList = new ArrayList<Point>();
        
        
        String thisCat = point.cat.name();

        if (thisCat == "RESTAURANT") {
            headNode = restaurantRoot;
        }
        else if (thisCat == "EDUCATION") {
            headNode = educationRoot;
        }
        else {
            headNode = hospitalRoot;
        }
        
        
            
        closestNode = findClosest(point, headNode, arrayList);

        if (closestNode.getPoint().equals(point)) {
            Node replacementNode = new Node(point);
            Node parent;
            bool = closestNode.getDimension();
            arrayList = getKids(closestNode);
            if(arrayList.size() != 0){
                if (closestNode!=headNode) {
                    replacementNode = buildTree(arrayList, bool, closestNode);
                    parent = closestNode.getParent();
                    //System.out.println("closestNode: " + closestNode.point + " child " + replacementNode.getParent().getLeftChild().getPoint());
                    if (replacementNode.getParent().getLeftChild() == closestNode) {
                        closestNode.getParent().setLeftChild(replacementNode);
                    }
                    if (replacementNode.getParent().getRightChild() == closestNode) {
                        closestNode.getParent().setRightChild(replacementNode);
                    }
                    replacementNode.setParent(closestNode.getParent());
                    //System.out.println("Head node " + closestNode.point + " replaced with node " + replacementNode.point);
                    return true;
                }
                replacementNode = buildTree(arrayList, bool, closestNode);
                if (thisCat == "RESTAURANT") {
                    setRoot(replacementNode, thisCat);
                }
                else if (thisCat == "EDUCATION") {
                    setRoot(replacementNode, thisCat);
                }
                else {
                    setRoot(replacementNode, thisCat);
                }
                //System.out.println("Node " + closestNode.point + " replaced with node " + replacementNode.point);
            }


            return true;
        }
        else {
            //System.out.println("node at point; " + point + " not found");
            //System.out.println("closest node; " + closestNode.point);
            return false;
        }   
    }

    public List<Point> getKids(Node topNode){
        List<Point> returnList = new ArrayList<Point>();
        List<Point> leftList = new ArrayList<Point>();
        List<Point> rightList = new ArrayList<Point>();

        if (topNode.getLeftChild() == null && topNode.getRightChild() == null)  {
            
            returnList.add(0, topNode.getPoint());
            return returnList;
        }

        if (topNode.getLeftChild() != null) {
            leftList = getKids(topNode.getLeftChild());
        }

        if (topNode.getRightChild() != null) {
            rightList = getKids(topNode.getRightChild());   
        }
        int i = 0, k = 0;
        while(i<leftList.size()){
            returnList.add(k, leftList.get(i));
            i++;
            k++;
        }
        i=0;
        while(i<rightList.size()){
            
            returnList.add(k, rightList.get(i));
            i++;
            k++;
        }
        returnList.add(k, topNode.getPoint());
        return returnList;
    }

    //ispointin also uses findclosest to search for a given node much like delete, but it only returns true if it is found
    @Override
    public boolean isPointIn(Point point) {
        //System.out.println("checking if point exists in tree");
        Node headNode, closestNode;
        String thisCat = point.cat.name();
        List<Point> emptyList = new ArrayList<Point>();

        if (thisCat == "RESTAURANT") {
            headNode = restaurantRoot;
        }
        else if (thisCat == "EDUCATION") {
            headNode = educationRoot;
        }
        else {
            headNode = hospitalRoot;
        }

        closestNode = findClosest(point, headNode, emptyList);
        
        if (closestNode.getPoint().equals(point)) {
            //System.out.println("point found");
            return true;
        }

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
            currParent.setParent(parent); 
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

