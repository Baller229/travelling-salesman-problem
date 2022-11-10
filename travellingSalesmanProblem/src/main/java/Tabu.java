import java.util.*;
import java.util.Comparator;

public class Tabu extends Genetic
{
   public static LinkedList<City> cities = new LinkedList<City>();
   public static LinkedList<Path> paths = new LinkedList<Path> ();
   public static LinkedList<Path> tabuPaths = new LinkedList<Path> ();
   public static Map< String, Double> BEST_PATH = new HashMap<> ();
   public static Map< String, Double> ALL_BEST_PATHS = new HashMap<> ();
   public static Map< String, Double> PATHS = new HashMap<> ();
   public static LinkedList<Double> TABU_PATHS = new LinkedList<> ();


public static void tabuSearch()
   {
      final int minCoord = 0;
      final int maxCoord = 200;

      //generate cities with coordinations
      generateCities (cities, minCoord, maxCoord);

      tabuGenerations ();
   }


   public static void tabuGenerations()
   {
      String key = null;
      Double value = 0.0;

      for (int i = 0; i < 100000; i++)
      {

         if(key != null)
         {
            ALL_BEST_PATHS.put (key, value);
         }
         generateFewPaths ();
         ALL_BEST_PATHS.put (getBestPathKey (), getBestPathValue ());
         Double localMinimum = 0.0;
         if(localMinimum == 0.0)
         {
            localMinimum = getBestPathValue ();
            ALL_BEST_PATHS.put (getBestPathKey (), getBestPathValue ());
            generateFewPaths ();

         }
         if(localMinimum >= getBestPathValue ())
         {
            localMinimum = getBestPathValue ();
            ALL_BEST_PATHS.put (getBestPathKey (), getBestPathValue ());
            generateFewPaths ();
         }

         if(localMinimum < getBestPathValue())
         {
            TABU_PATHS.add (localMinimum);
            generateFewPaths ();
         }
         if(!(ALL_BEST_PATHS.isEmpty ()))
         {
            key = getMinKey (ALL_BEST_PATHS);
            value = ALL_BEST_PATHS.get (key);
            System.out.println (i + ": ROAD: " + key + " ,COST: " + ALL_BEST_PATHS.get (key));
            ALL_BEST_PATHS = null;
            ALL_BEST_PATHS = new HashMap<> ();
         }
      }

   }
   public static void generateFewPaths ()
   {
      BEST_PATH = null;
      PATHS = null;
      PATHS = new HashMap<> ();
      paths = null;
      paths = new LinkedList<> ();
      BEST_PATH = new HashMap<> ();

      if(TABU_PATHS.size () > 5)
      {
         TABU_PATHS.removeLast ();
      }
      if(TABU_PATHS.size () == 0)
      {
         for(int i = 0; i < 10; i++)
         {
            generatePaths (cities, paths);
            PATHS.put (Arrays.toString (paths.get (i).poradieMiest), paths.get (i).cenaCesty);
         }

      }
      else
      {
         for(int i = 0; i < 10; i++)
         {
            generatePaths (cities, paths);
            if(!(TABU_PATHS.contains (paths.get (i).cenaCesty)))
            {
               PATHS.put (Arrays.toString (paths.get (i).poradieMiest), paths.get (i).cenaCesty);
            }
            else
            {
               i--;
            }
         }
      }

      pickBestPath ();
   }
   public static void pickBestPath()
   {
      String  key = getMinKey (PATHS);
      BEST_PATH.put (key, PATHS.get (key));
   }

   public static String getBestPathKey()
   {
      return getMinKey (PATHS);
   }

   public static Double getBestPathValue()
   {
      String key = getMinKey (PATHS);
      return PATHS.get (key);
   }
   public static <K, V> Map<K, V> copyMap(Map<K, V> original)
   {
      // constructor by passing original hashmap
      // in the parameter returns the new hashmap
      // with the copied content of the original one
      return new HashMap<>(original);
   }
}
