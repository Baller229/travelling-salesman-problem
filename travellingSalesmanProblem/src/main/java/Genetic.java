import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Genetic
{
   public static LinkedList<City> cities = new LinkedList<City>();
   public static LinkedList<Path> paths = new LinkedList<Path> ();
   public static LinkedList<Path> crossoverPaths = new LinkedList<Path> ();
   public static LinkedList<Path> mutatedPaths = new LinkedList<Path> ();
   public static LinkedList<Double> bestPaths = new LinkedList<Double> ();
   public static void geneticAlgorithm()
   {
      final int minCoord = 0;
      final int maxCoord = 200;

   //generate cities with coordinations
      generateCities (cities, minCoord, maxCoord);

      // store best path from generatedPaths
      //bestPaths.add (storeBestPath(paths));

      for(int i = 0; i < 100000; i++)
      {
         generation();
         System.out.println (i + ": Path cost: " + bestPaths.get (0));
      }

   }

   public static void generation()
   {

      for(int i = 0; i < 100; i++)
      {
         generatePaths (cities, paths);
      }
      //generate
      for(int i = 0; i < 50; i++)
         {
            generateCrossover(paths, crossoverPaths);
            generateMutations (paths, mutatedPaths);
         }
         bestPaths.add (storeBestPath(crossoverPaths));
         bestPaths.add (storeBestPath(mutatedPaths));
         Collections.sort (bestPaths);
         Double best = bestPaths.getFirst ();
         bestPaths = null;
         bestPaths = new LinkedList<> ();
         bestPaths.add (best);
         crossoverPaths = null;
         paths = null;
         mutatedPaths = null;
         crossoverPaths = new LinkedList<> ();
         mutatedPaths = new LinkedList<> ();
         paths = new LinkedList<> ();
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

   public static void generatePaths(LinkedList<City> city, LinkedList<Path> paths)
   {
      LinkedList<City> cities = new LinkedList<> ();
      cities = (LinkedList) city.clone ();
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

   public static double totalPathCost(String sequence[])
   {
      double pathCost = 0;
      City startCity = getCityfromIndex (0);
      City currentCity;
      City nextCity;
      for(int i = 0; i < sequence.length; i++)
      {
         if ((i + 1) == sequence.length)
         {
            currentCity = getCityfromIndex (Integer.parseInt (sequence[i]));
            pathCost += countPathCost (currentCity, startCity);
         }
         else
         {
            currentCity = getCityfromIndex (Integer.parseInt (sequence[i]));
            nextCity = getCityfromIndex (Integer.parseInt (sequence[i+1]));
            pathCost += countPathCost (currentCity, nextCity);
         }
      }
      return pathCost;
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

   public static void generateCrossover(LinkedList<Path> path, LinkedList<Path> crossover)
   {
      String[] parent1;
      String[] parent2;


      //get random parent
      parent1 = path.get ((int)(Math.random() * (path.size () - 1) + 1)).poradieMiest;
      parent2 =path.get ((int)(Math.random() * (path.size () - 1) + 1)).poradieMiest;

      String[] child = new String[parent1.length];
      int pos = (int)(Math.random() * (parent1.length - 1) + 1);
      crossOver(parent1, parent2, child, pos);

      Path childs = new Path (child, totalPathCost (child));
      crossover.add (childs);

   }
   public static void generateMutations(LinkedList<Path> path, LinkedList<Path> mutations)
   {
      String parent [] = path.get ((int)(Math.random() * (path.size () - 1) + 1)).poradieMiest;
      String child [] = new String[parent.length];
      memcpy (parent, child);
      memswap(child);
      Path  childs = new Path (child, totalPathCost (child));
      mutations.add (childs);
   }

   public static void crossOver(String[] parent1, String[] parent2, String[] child, int pos)
   {
      int x[];
      int y[];
      String[] parentA = new String[parent1.length];
      String[] parentB = new String[parent2.length];
      memcpy (parent1, parentA);
      memcpy (parent2, parentB);

   //crossover from parent 1
      for (int i = pos; i < parentA.length; i++)
      {
         child[i] = parentA[i];
         int indexOf = Arrays.asList(parentB).indexOf(parentA[i]);
         if(indexOf > 0 && indexOf < parentB.length);
         {
            parentB[indexOf] = "-1";
         }

      }

      // crossover from parent2
      int index = 0;
      for (int j = 0; j < parentA.length; j++)
      {
         if (parentB[j] == "-1")
         {
            continue;
         }

         child[index] = parentB[j];
         index++;
      }

   }

   public static void memcpy(String a[], String b[])
   {
      // Copying elements of a[] to b[]
      for (int i = 0; i < a.length; i++)
         b[i] = a[i];
   }
   public static void memswap(String a[])
   {
      String temp;
      int pos1 = (int)(Math.random() * (a.length - 1) + 1);
      int pos2 = (int)(Math.random() * (a.length - 1) + 1);

      temp = a[pos1];
      a[pos1] = a[pos2];
      a[pos2] = temp;
   }

   public static City getCityfromIndex(int index)
   {
      return cities.get (index);
   }

}
