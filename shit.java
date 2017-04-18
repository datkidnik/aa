import java.util.concurrent.ThreadLocalRandom;

public class shit {

    public static void main(String[] args){

        int i;
        for(i=0; i<100; i++){
        	String cat;
        	int randomLat = ThreadLocalRandom.current().nextInt(1, 1000);
	        int randomLon = ThreadLocalRandom.current().nextInt(1, 1000);
	        int randomK = ThreadLocalRandom.current().nextInt(1, 100);
	        int randomCat = ThreadLocalRandom.current().nextInt(1, 4);

	        switch(randomCat){
	        	case 1:
	        		cat = "restaurant";
	        		break;
	        	case 2:
	        		cat = "hospital";
	        		break;
	        	case 3:
	        		cat = "education";
	        		break;
	        	default:
	        		cat = "restaurant";
	        		break;
	        }

	        System.out.println("A " + "id" + i + " " + cat + " " + randomLat + " " + randomLon);
        }
    }
}
