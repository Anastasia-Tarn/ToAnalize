package org.example;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    private static BlockingQueue<String> listA = new ArrayBlockingQueue<>(100);
    private static BlockingQueue<String> listB = new ArrayBlockingQueue<>(100);
    private static BlockingQueue<String> listC = new ArrayBlockingQueue<>(100);
    private static String LETTERS = "abc";
    private static int LENGTH = 100000;
    public static void main(String[] args) throws InterruptedException {

        Thread textGenerator = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String text = generateText(LETTERS, LENGTH);
                try {
                    listA.put(text);
                    listB.put(text);
                    listC.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        textGenerator.start();

        textGenerator.join();

        Thread countA = new Thread(() -> {
            int max = 0;
                for (String a : listA) {
                    int count = (int) a.chars().filter(ch -> ch == 'a').count();
                    if (max < count) {
                        max = count;
                    }
                }
            System.out.println("Максимальное количество символа 'a' в строке  - "+max);
        });
        countA.start();

        countA.join();

        Thread countB = new Thread(() -> {
            int max = 0;
            for (String b : listB) {
                int count = (int) b.chars().filter(ch -> ch == 'b').count();
                if (max < count) {
                    max = count;
                }
            }
            System.out.println("Максимальное количество символа 'b' в строке  - "+max);
        });
        countB.start();

        countB.join();

        Thread countC = new Thread(() -> {
            int max = 0;
            for (String c : listC) {
                int count = (int) c.chars().filter(ch -> ch == 'c').count();
                if (max < count) {
                    max = count;
                }
            }
            System.out.println("Максимальное количество символа 'c' в строке  - "+max);
        });
        countC.start();

        countC.join();

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}