package com.jojos.home.stream;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * check implementation of #asStream()
 * check also {@link BufferedReader#lines()}
 * Created by karanikasg@gmail.com.
 */
public class MyListStreamSupport implements Iterable<String> {

    private final List<String> list = new ArrayList<>();

    public MyListStreamSupport() {
    }

    public boolean add(String elem) {
        return list.add(elem);
    }

    public String get(int index) {
        return list.get(index);
    }

    public Stream<String> asStream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator(), Spliterator.ORDERED | Spliterator.NONNULL), false);

    }

    @Override
    public Iterator<String> iterator() {
        Iterator<String> it = new Iterator<String>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < list.size() && list.get(currentIndex) != null;
            }

            @Override
            public String next() {
                return list.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
