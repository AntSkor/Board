import java.io.*;
import java.util.Random;
import java.util.Scanner;

class Board {
    private int width;
    private int height;
    private int[][] board;
    Scanner scan;

    public Board(int m, int n) {
        this.width = m;
        this.height = n;
        board = new int[m][n];
    }
    /*
     метод для генерации доски и вывода её в консоль
   */
    public void getBoard() {
        for (int x = 0; x < width; x++) {
            String line = "";
            for (int y = 0; y < height; y++) {
                if (board[x][y] == 0) {
                    line += 0 + " ";
                } else {
                    line += 1 + " ";
                }
            }
            line += "";
            System.out.println(line);
        }
        System.out.println("_________");
    }
    /*
 метод для генерации следующего поколения клеток
  */
    public void nextGeneration() {
        int[][] newBoard = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int aliveNeighbours = countAliveNeighbours(x, y);
                if (getState(x, y) == 1) {
                    if (aliveNeighbours < 2) {
                        newBoard[x][y] = 0; // если у живой клетки двух живых соседей - она умирает
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBoard[x][y] = 1; // если у живой клетки 2 или 3 живых соседа - она живёт
                    } else if (aliveNeighbours > 3) {
                        newBoard[x][y] = 0; // если у живой клетки больше 3 живых соседей - она умирает
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        newBoard[x][y] = 1; // если у мёртвой клетки 3 живых соседа - она оживает
                    }
                }
            }
        }
        board = newBoard;
        getBoard();
    }
    /*
     метод для подсчёта живых соседей
     */
    public int countAliveNeighbours(int x, int y) {
        int count = 0;
        count += getState(x - 1, y - 1);
        count += getState(x + 1, y + 1);
        count += getState(x, y + 1);
        count += getState(x, y - 1);
        count += getState(x + 1, y);
        count += getState(x - 1, y);
        count += getState(x + 1, y - 1);
        count += getState(x - 1, y + 1);
        return count;
    }

    public int getState(int x, int y) {
        if (x < 0 || x >= width) {
            return 0;
        }
        if (y < 0 || y >= height) {
            return 0;
        }
        return board[x][y];
    }
    /*
 метод для генирации случайного стартового состояния доски
  */
    public void randomFirstGeneration() {
        Random rand = new Random();
        int alive = rand.nextInt(width * height);
        for (int i = 0; i < alive; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            board[x][y] = 1;
        }
    }
    /*
    метод для чтения стартового состояния доски из файла.
     */
    public void fileConfig() {
        try {
            scan = new Scanner(new File("Config.txt"));
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    board[x][y] = scan.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    /*
 метод для вывода в консоль нового состояния доски (нового поколения клеток) кажду секунду
 */
    public void nextGenerations() {
        while (true) {
            try {
                Thread.sleep(1000);
                nextGeneration();
            } catch (InterruptedException e) {
            }
        }
    }
    /*
  дополнительный метод для выведения в консоль указанного количенства поколений
  generations - количество поколений
  */
    public void countOfGenerations(int generations) {
        for (int i = 0; i < generations; i++) {
            try {
                Thread.sleep(1000);
                nextGeneration();
            } catch (InterruptedException e) {
            }
        }
    }
    /* дополнительные методы для распределения живых и мёртвых клеток вручную
     */
    public void cellAlive(int x, int y) {
        board[x][y] = 1;
    }

    public void cellDead(int x, int y) {
        board[x][y] = 0;
    }

    public void startTheLife(){
         System.out.println("1 generate a random starting state" + "\n" + "2 read a starting state from file");
         scan = new Scanner(System.in);
         int choice = scan.nextInt();
         if (choice == 1) {
             randomFirstGeneration();
             getBoard();
         }
         if (choice == 2){
             fileConfig();
             getBoard();
         }
    }

    public static void main (String [] args) {
          Board b = new Board(8,8);
          b.startTheLife();
          b.nextGenerations();
    }
  }


