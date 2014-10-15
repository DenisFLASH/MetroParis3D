import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilisée pour parser les données RATP afin d'y extraire les informations suivantes sur
 * chaque statin de métro: nom, latitude, longitude. Ensuite, l'information peut être facilement
 * insérée dans la base de données.
 */
public class DataParser {

  public static void main(String[] args) {
    int startingId = 76;
    int idLine = 4;
    Map<String, List<Double>> stationMap = parseFile("raw_GPS_data/line4", idLine, startingId);
    Map<String, List<Double>> sortedMap = sortByComparator(stationMap, 0, false);
    displayMap(sortedMap, idLine, startingId);
  }

  private static Map<String, List<Double>> parseFile(String filename, int idLine, int id) {
    String stationLabel;
    Map<String, List<Double>> stationMap = new HashMap<String, List<Double>>();

    String regex = "\"([^\"]*)\".*\",([\\d\\.]+),([\\d\\.]+)";
    Pattern pattern = Pattern.compile(regex);
    Matcher m;
    String line;
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {
        m = pattern.matcher(line);
        if (m.find()) {
          stationLabel = m.group(1);
          if (!stationMap.containsKey(stationLabel)) {
            List<Double> coordinates = new ArrayList<Double>();
            coordinates.add(Double.valueOf(m.group(2)));
            coordinates.add(Double.valueOf(m.group(3)));
            stationMap.put(stationLabel, coordinates);
          }
        }
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        br.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return stationMap;
  }

  private static void displayMap(Map<String, List<Double>> stationMap, int idLine, int id) {
    List<Double> coordinates;

    System.out.println("INSERT INTO station (id,label,id_line,latitude,longitude) VALUES ");
    for (String label : stationMap.keySet()) {
      coordinates = stationMap.get(label);
      System.out.println("(" + id + ", \"" + label + "\", " + idLine + ", " + coordinates.get(0)
          + ", " + coordinates.get(1) + "),");
      id++;
    }
  }


  /**
   * Tri la map par l'ordre d'une des coordonnées GPS.
   * 
   * @param unsortedMap map des stations
   * @param coordinateId 0 = latitude; 1 = longitude
   * @param order asc/desc
   * 
   * @return la map triée
   */
  private static Map<String, List<Double>> sortByComparator(Map<String, List<Double>> unsortedMap,
      final int coordinateId, final boolean order) {

    List<Entry<String, List<Double>>> entries =
        new LinkedList<Entry<String, List<Double>>>(unsortedMap.entrySet());

    // Sorting the list based on values
    Collections.sort(entries, new Comparator<Entry<String, List<Double>>>() {
      public int compare(Entry<String, List<Double>> entry1, Entry<String, List<Double>> entry2) {
        Double value1 = entry1.getValue().get(coordinateId);
        Double value2 = entry2.getValue().get(coordinateId);
        if (order) {
          return value1.compareTo(value2);
        } else {
          return value2.compareTo(value1);
        }
      }
    });

    // Maintaining insertion order with the help of LinkedList
    Map<String, List<Double>> sortedMap = new LinkedHashMap<String, List<Double>>();

    for (Entry<String, List<Double>> entry : entries) {
      List<Double> coordinates = new ArrayList<Double>();
      coordinates.add(entry.getValue().get(0));
      coordinates.add(entry.getValue().get(1));
      sortedMap.put(entry.getKey(), coordinates);
    }

    return sortedMap;
  }

  public static void printMap(Map<String, Integer> map) {
    for (Entry<String, Integer> entry : map.entrySet()) {
      System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
    }
  }

}
