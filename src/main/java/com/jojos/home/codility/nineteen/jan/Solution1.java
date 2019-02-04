package com.jojos.home.codility.nineteen.jan;

/**
 * @author karanikasg@gmail.com
 */
import java.util.stream.IntStream;

class Solution1 {

    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[] T =  {-3, -14, -5, 7, 8, 42, 8, 3};
        String season = solution.solution(T);
        System.out.println(season);
    }
    private static final int SEASONS_NUM = 4;

    public String solution(int[] T) {

        // assumed T.length is always divisible by 4
        int seasonChunkLength = T.length / SEASONS_NUM;
        SeasonAmplitude[] seasonAmplitudes = new SeasonAmplitude[SEASONS_NUM];

        for (int i = 0; i < SEASONS_NUM; i++) {
            int max = -1000;
            int min = 1000;
            for (int j = 0; j < seasonChunkLength; j++) {
                int index = (i * seasonChunkLength)+ j;
                max = findMax(max, T[index]);
                min = findMin(min, T[index]);
            }
            seasonAmplitudes[i] = new SeasonAmplitude(Season.valueOf(i), max - min);
        }

        // at this point we have the amplitudes for every season. Finding the max between each of them is trivial
        int max = -1000;
        Season seasonWithMaxAmplitude = Season.WINTER;
        for (SeasonAmplitude seasonAmplitude: seasonAmplitudes) {
            if (seasonAmplitude.getAmplitude() > max) {
                max = seasonAmplitude.getAmplitude();
                seasonWithMaxAmplitude = seasonAmplitude.getSeason();
            }
        }
        return seasonWithMaxAmplitude.toString();
    }

    private int findMaxL(int[] ints) {
//        String[] subarray = new String[end - beg + 1];
//        System.arraycopy(arr, beg, subarray, 0, subarray.length);
        return IntStream.of(ints).max().getAsInt();
    }

    private int findMax(int x, int y) {
        if (x > y) {
            return x;
        }
        return y;
    }

    private int findMin(int x, int y) {
        if (x < y) {
            return x;
        }
        return y;
    }

    private enum Season {
        WINTER(0),
        SPRING(1),
        SUMMER(2),
        AUTUMN(3);

        private final int hierarchy;

        Season(int hierarchy) {
            this.hierarchy = hierarchy;
        }

        public int getHierarchy() {
            return hierarchy;
        }

        public static Season valueOf(int hierarchy) {
            for (Season season : values()) {
                if (season.getHierarchy() == hierarchy) {
                    return season;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

    private static final class SeasonAmplitude {
        private final Season season;
        private final int amplitude;

        public SeasonAmplitude(Season season, int amplitude) {
            this.season = season;
            this.amplitude = amplitude;
        }

        public Season getSeason() {
            return season;
        }

        public int getAmplitude() {
            return amplitude;
        }
    }



}
