// plinko
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class plinko {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("plinko.in");
        File outputFile = new File("plinko.out");

        Scanner scanner = new Scanner(inputFile);
        PrintWriter printWriter = new PrintWriter(outputFile);

        String line;
        String[] splitLine;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.equals("0")) {
                break;
            }

            int levels = Integer.valueOf(line);

            for (int i = 0; i < levels; i++) {
                line = scanner.nextLine();
                splitLine = line.split(" ");
                int[] lineInts = new int[splitLine.length];
                for (int x = 0; x < splitLine.length; x++) {
                    lineInts[x] = Integer.valueOf(splitLine[x]);
                }

                
            }
        }
    }

    public static class Node {
        int value;
        Node l;
        Node r;
        Node parent;

        Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
            this.l = null;
            this.r = null;
        }
    }
}
