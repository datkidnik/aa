package nearestNeigh;

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

    @Override
    public void buildIndex(List<Point> points) {
        // To be implemented.
        int length, i;
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

}
