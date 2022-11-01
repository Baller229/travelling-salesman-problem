import java.util.Collections;
import java.util.LinkedList;

public class Genetic
{
   public void geneticAlgorithm()
   {
      final int minCoord = 0;
      final int maxCoord = 200;
      double currentCoordX;
      double currentCoordY;
      LinkedList<City> cities = new LinkedList<City>();
      for(int i = 0; i < 20; i++)
      {
         currentCoordX = Math.random()*(maxCoord-minCoord+1)+minCoord;
         currentCoordY = Math.random()*(maxCoord-minCoord+1)+minCoord;
         cities.add (new City(String.valueOf (i), currentCoordX, currentCoordY));
      }
      Collections.shuffle(cities);
   }


}
