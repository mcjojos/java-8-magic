package com.jojos.home.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gkaranikas
 */
public class CollectorsUtil {

    /**
     * For a map containing a map for the specified {@code outerMapKey}, adds the provided {@code value} to an
     * inner map for key {@code innerMapKey}. If no inner map is found, one will be created first. The put into
     * the inner map will be a normal {@link ConcurrentMap#put(Object, Object)}.
     *
     * @param map the outer map
     * @param outerMapKey the key to the outer map
     * @param innerMapKey the key for which to add the value in the inner map
     * @param value the value
     * @param <OK> the type of the outer key
     * @param <IK> the type of the inner key
     * @param <V> the type of the value
     * @return the existing value from the inner map.
     * @see Map#put(Object, Object)
     */
    public static <OK, IK, V> V addToContainedMap(ConcurrentMap<OK, Map<IK, V>> map, OK outerMapKey, IK innerMapKey, V value) {
        Map<IK, V> existingMap = map.get(outerMapKey);
        if (existingMap == null) {
            // It might look like checking for null and then creating something means that we need a lock.
            // this isn't the case, as the ACTUAL point of synchronization is the map.putIfAbsent() below.
            // it's perfectly possible to have multiple threads enter this block at the same time.
            // this is fine, as the only "true" value added is added by the putIfAbsent() call.
            // this race will only be an issue in the beginning. Once putIfAbsent() has succeeded,
            // the outer if-statement will always be false, which means we can avoid creating the
            // inner container and calling putIfAbsent() again.
            // This replaces this more legible but slower pattern:
            // map.putIfAbsent(outerMapKey, new ConcurrentHashMap<IK, V>()); // ensure that we have something
            // map.get(outerMapKey).put(innerMapKey, value);
            // See slides 54 and 55 of this presentation regarding the speed of this: http://www.slideshare.net/marakana/effective-java-still-effective-after-all-these-years
            ConcurrentHashMap<IK, V> newMap = new ConcurrentHashMap<>();
            existingMap = map.putIfAbsent(outerMapKey, newMap);
            if (existingMap == null) {
                // we've added a new set
                existingMap = newMap;
            }
        }
        return existingMap.put(innerMapKey, value);
    }

    /**
     * For a map containing a set for the specified {@code mapKey}, adds the provided value to the set.
     * If there is no set for the {@code mapKey}, one will be created first. As we are using a {@link ConcurrentMap},
     * we will assume that we want a concurrent set as well, so one will be created by way of {@link ConcurrentHashMap}
     * and {@link Collections#newSetFromMap(Map)}
     *
     * @param map    the map of sets
     * @param mapKey the key that indicates a set in the map
     * @param value  the value to be added to the set
     * @return {@code true} if the value could be added to the set or {@code false} otherwise.
     * @see Set#add(Object)
     */
    public static <K, V> boolean addToContainedSet(ConcurrentMap<K, Set<V>> map, K mapKey, V value) {
        Set<V> existingSet = map.get(mapKey);
        if (existingSet == null) {
            // It might look like checking for null and then creating something means that we need a lock.
            // this isn't the case, as the ACTUAL point of synchronization is the map.putIfAbsent() below.
            // it's perfectly possible to have multiple threads enter this block at the same time.
            // this is fine, as the only "true" value added is added by the putIfAbsent() call.
            // this race will only be an issue in the beginning. Once putIfAbsent() has succeeded,
            // the outer if-statement will always be false, which means we can avoid creating the
            // inner container and calling putIfAbsent() again.
            // This replaces this more legible but slower pattern:
            // map.putIfAbsent(mapKey, Collections.newSetFromMap(new ConcurrentHashMap<V, Boolean>())); // ensure that we have something
            // map.get(mapKey).add(value);
            // See slides 54 and 55 of this presentation regarding the speed of this: http://www.slideshare.net/marakana/effective-java-still-effective-after-all-these-years
            Set<V> newSet = Collections.newSetFromMap(new ConcurrentHashMap<V, Boolean>());
            existingSet = map.putIfAbsent(mapKey, newSet);
            if (existingSet == null) {
                // we've added a new set
                existingSet = newSet;
            }
        }
        return existingSet.add(value);
    }

    public static <K, V> boolean removeFromContainedSet(ConcurrentMap<K, Set<V>> map, K mapKey, V value) {
        Set<V> existingSet = map.get(mapKey);
        if (existingSet == null) {
            // It might look like checking for null and then creating something means that we need a lock.
            // this isn't the case, as the ACTUAL point of synchronization is the map.putIfAbsent() below.
            // it's perfectly possible to have multiple threads enter this block at the same time.
            // this is fine, as the only "true" value added is added by the putIfAbsent() call.
            // this race will only be an issue in the beginning. Once putIfAbsent() has succeeded,
            // the outer if-statement will always be false, which means we can avoid creating the
            // inner container and calling putIfAbsent() again.
            // This replaces this more legible but slower pattern:
            // map.putIfAbsent(mapKey, Collections.newSetFromMap(new ConcurrentHashMap<V, Boolean>())); // ensure that we have something
            // map.get(mapKey).add(value);
            // See slides 54 and 55 of this presentation regarding the speed of this: http://www.slideshare.net/marakana/effective-java-still-effective-after-all-these-years
            Set<V> newSet = Collections.newSetFromMap(new ConcurrentHashMap<V, Boolean>());
            existingSet = map.putIfAbsent(mapKey, newSet);
            if (existingSet == null) {
                // we've added a new set
                existingSet = newSet;
            }
        }
        return existingSet.remove(value);
    }

    public static <OK, IK, V> V removeFromContainedMap(ConcurrentMap<OK, Map<IK, V>> map, OK outerMapKey, IK innerMapKey, V value) {
        Map<IK, V> existingMap = map.get(outerMapKey);
        if (existingMap == null) {
            // It might look like checking for null and then creating something means that we need a lock.
            // this isn't the case, as the ACTUAL point of synchronization is the map.putIfAbsent() below.
            // it's perfectly possible to have multiple threads enter this block at the same time.
            // this is fine, as the only "true" value added is added by the putIfAbsent() call.
            // this race will only be an issue in the beginning. Once putIfAbsent() has succeeded,
            // the outer if-statement will always be false, which means we can avoid creating the
            // inner container and calling putIfAbsent() again.
            // This replaces this more legible but slower pattern:
            // map.putIfAbsent(outerMapKey, new ConcurrentHashMap<IK, V>()); // ensure that we have something
            // map.get(outerMapKey).put(innerMapKey, value);
            // See slides 54 and 55 of this presentation regarding the speed of this: http://www.slideshare.net/marakana/effective-java-still-effective-after-all-these-years
            ConcurrentHashMap<IK, V> newMap = new ConcurrentHashMap<>();
            existingMap = map.putIfAbsent(outerMapKey, newMap);
            if (existingMap == null) {
                // we've added a new set
                existingMap = newMap;
            }
        }
        return existingMap.remove(innerMapKey);
    }


    /**
     * Creates a comma-separated list of values.
     *
     * @param values the collection of values
     * @param mapFunction a function returning the string
     * @param <T> the type of the values
     * @return a string like "{a, b, c}"
     */
    public static <T> String toString(Collection<T> values,
                                      Function<T, String> mapFunction) {
        return values.stream().map(mapFunction).collect(Collectors.joining(", ", "{", "}"));
    }

    /**
     * Same functionality as {@link #toString(Collection, Function)} but with optional parameters
     * for delimiter, prefix and suffix
     * @param values the collection of values
     * @param mapFunction a function returning the string
     * @param delimiter an optional string defining the delimiter, "," is used in case it's empty.
     * @param prefix an optional string defining the prefix, "{" is used in case it's empty.
     * @param suffix an optional string defining the suffix, "}" is used in case it's empty.
     * @param <T> the type of the values
     * @return a delimiter-separated string like having a prefix and s suffix.
     */
    public static <T> String toString(Collection<T> values,
                                      Function<T, String> mapFunction,
                                      Optional<String> delimiter,
                                      Optional<String> prefix,
                                      Optional<String> suffix) {
        return values.stream().map(mapFunction).collect(
                Collectors.joining(delimiter.orElse(","),
                        prefix.orElse("{"),
                        suffix.orElse("}")));
    }

}
