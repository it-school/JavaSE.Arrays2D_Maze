package com.itschool;

import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
   public static final int WIDTH = 50;
   public static final int HEIGHT = 10;
   public static final int VARIABLE_PARAMETER = 10;

   public static Labirynth[][] labirynth = new Labirynth[HEIGHT][WIDTH];
   public static int[][] map = new int[HEIGHT][WIDTH];
   public static Random random = new Random();
   public static int enter = 0;
   public static Scanner scanner = new Scanner(System.in);

   public static void visualize() {
      for (int i = HEIGHT - 1; i >= 0; i--) {
         for (Labirynth cell : labirynth[i]) {
            switch (cell) {
               case wall:
                  System.out.print('#');
                  break;
               case coin:
                  System.out.print('@');
                  break;
               case player:
                  System.out.print('!');
                  break;
               default:
                  System.out.print(' ');
                  break;
            }
         }
         System.out.println();
      }
      scanner.nextLine();
   }


   public static void initialize() {
      for (int row = 0; row < HEIGHT; row++) {
         for (int column = 0; column < WIDTH; column++) {
            labirynth[row][column] = Labirynth.wall;
            map[row][column] = 0;
         }
      }

      generateWays(2);
      generateWays(4);
      generateWays(5);
      generateWays(6);
      generateWays(7);
      generateWays(8);
   }

   public static void generateWays(int level) {
      int startColumn = WIDTH / VARIABLE_PARAMETER + random.nextInt(WIDTH);
      if (startColumn >= WIDTH - WIDTH / VARIABLE_PARAMETER)
         startColumn = WIDTH / 2;

      if (enter == 0)
         enter = startColumn;

      int shift = 0;
      startColumn = enter;
      for (int row = 0; row < HEIGHT; row++) {
         labirynth[row][startColumn] = Labirynth.way;
         shift = ((random.nextInt(level) > level / 2 - 1 ? +1 : (random.nextInt(level) > level / 2 - 1 ? 0 : -1)));
         if (!(startColumn + shift > WIDTH - 1 || startColumn + shift < 0))
            startColumn += shift;
      }
   }

   public static void main(String[] args) {
      initialize();
      visualize();

      play();
   }

   public static void play() {
      int myColumn = -1;
      for (int column = 0; column < WIDTH; column++) {
         if (labirynth[0][column] == Labirynth.way) {
            myColumn = column;
            break;
         }
      }

      int forward = 0, right = 0;
      int myRow = 0;
      labirynth[myRow][myColumn] = Labirynth.player;
      do {
         if (labirynth[myRow + 1][myColumn] == Labirynth.way) {
            labirynth[myRow][myColumn] = Labirynth.way;
            myRow++;
            labirynth[myRow][myColumn] = Labirynth.player;
         } else if (labirynth[myRow][myColumn + 1] == Labirynth.way) {
            labirynth[myRow][myColumn] = Labirynth.way;
            myColumn++;
            labirynth[myRow][myColumn] = Labirynth.player;
         } else if (labirynth[myRow][myColumn - 1] == Labirynth.way) {
            labirynth[myRow][myColumn] = Labirynth.way;
            myColumn--;
            labirynth[myRow][myColumn] = Labirynth.player;
         }
         map[myRow][myColumn]++;
         if (map[myRow][myColumn] > 3) {
            System.out.println("Lost");
            exit(-1);
         }
         visualize();
      } while (myRow < HEIGHT - 1);
   }
}

