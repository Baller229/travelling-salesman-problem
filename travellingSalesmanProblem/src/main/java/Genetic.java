import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Genetic
{
   public static void geneticAlgorithm()
   {
      final int minCoord = 0;
      final int maxCoord = 200;

      LinkedList<City> cities = new LinkedList<City>();
      LinkedList<Path> paths = new LinkedList<Path> ();
      LinkedList<Path> crossoverPaths = new LinkedList<Path> ();
      LinkedList<Path> mutatedPaths = new LinkedList<Path> ();

      //generate cities with coordinations
      generateCities (cities, minCoord, maxCoord);

      // generate n random paths with path cost and sequence of cities
      for(int i = 0; i < 20; i++)
      {
         generatePaths (cities, paths);
      }
      // store best path from generatedPaths
      storeBestPath(paths);


   }

   public static void generateCities(LinkedList<City> cities, final int min, final int max)
   {
      double currentCoordX;
      double currentCoordY;
      for(int i = 0; i < 20; i++)
      {
         currentCoordX = Math.random()*(max-min+1)+min;
         currentCoordY = Math.random()*(max-min+1)+min;
         cities.add (new City(String.valueOf (i), currentCoordX, currentCoordY));
      }
   }

   public static void generatePaths(LinkedList<City> cities, LinkedList<Path> paths)
   {
      Path currentPath;
      // store start position, remove from list, shuffle, add start position to index 0
      // so start position will be on static place while other positions will be shuffled
      City startCity;
      startCity = cities.getFirst ();
      cities.remove (0);
      LinkedList<City> shuffledCities = cities;
      Collections.shuffle(cities);
      shuffledCities.addFirst (startCity);

      City currentCity;
      City nextCity;

      String[] currentCityPosition = new String[shuffledCities.size ()];
      double pathCost = 0;

      for (int i = 0; i < shuffledCities.size (); i++)
      {
         currentCity = shuffledCities.get(i);
         currentCityPosition[i] = currentCity.position;
         if( (i + 1) == shuffledCities.size ())
         {

            pathCost += countPathCost(currentCity, startCity);  //count path cost when comming back to start city position
         }
         else
         {
            nextCity = shuffledCities.get(i+1);
            pathCost += countPathCost(currentCity, nextCity); //count path cost when going from one city to another
         }
      }
      //add shuffled path to list
      paths.add (new Path (currentCityPosition, pathCost));
   }

   public static double countPathCost(City currCity, City nextCity)
   {
      int diffX = Math.abs((int)(currCity.X - nextCity.X));
      int diffY = Math.abs((int)(currCity.Y - nextCity.Y));
      return Math.sqrt( (double)( (diffX*diffX) + (diffY*diffY) ) );
   }

   public static double storeBestPath(LinkedList<Path> path)
   {
     double min[] = new double[path.size ()];
     int i = 0;
      for (Path p : path)
      {
         min[i] = p.cenaCesty;
         i++;
      }
      Arrays.sort (min);
      return min[0];
   }
}
