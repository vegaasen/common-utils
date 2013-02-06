package com.suiveg.utils.map;

import java.util.*;

/**
 * MapSortUtils is a simple sort by (..) library
 *
 * @author <a href="mailto:vegaasen@gmail.com">Vegard Aasen</a>
 * @author <a href="mailto:marius.kristensen@gmail.com">Marius Kristensen</a>
 * @version see system.properties
 * @since 0.2
 */
public final class MapSortUtils {

    private MapSortUtils(){}

     public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    public static <K,V> Map<K,V> sortByKey(Map<K,V> map) {
        if(map!=null&&!map.isEmpty()) {
            return new TreeMap<K,V>(map);
        }
        return Collections.emptyMap();
    }

}
