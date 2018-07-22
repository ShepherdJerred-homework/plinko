// plinko
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class plinko {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("plinko.in");
        File outputFile = new File("plinko.out");

        Scanner scanner = new Scanner(inputFile);
        PrintWriter printWriter = new PrintWriter(outputFile);

        String nextLine;
        String[] nextLineSplit;
        int currentBoard = 0;
        while (scanner.hasNextLine()) {
            nextLine = scanner.nextLine();
            if (nextLine.equals("0")) {
                System.out.println("DONE");
                break;
            }

            currentBoard++;
            System.out.println("Board: " + currentBoard);

            int numberOfLevels = Integer.valueOf(nextLine);

            Node head = null;
            Queue<Node> parents = new LinkedList<>();

            for (int currentLevel = 0; currentLevel < numberOfLevels; currentLevel++) {
                nextLine = scanner.nextLine();

                int[] pegsInLevel = Arrays.stream(nextLine.split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                for (int peg = 0; peg < pegsInLevel.length; peg++) {
                    Node node = new Node(pegsInLevel[peg]);

                    if (head == null) {
                        head = node;
                    } else {
                        if (peg != 0) {
                            node.lparent = parents.poll();
                            if (node.lparent != null) {
                                node.lparent.r = node;
                            }
                        }

                        if (peg != pegsInLevel.length - 1) {
                            node.rparent = parents.poll();
                            if (node.rparent != null) {
                                node.rparent.l = node;
                            }
                        }
                    }

                    parents.add(node);
                    parents.add(node);
                }
            }

            System.out.println(getBestPath(head));
            printWriter.println(getBestPath(head));

            printWriter.close();

        }
    }

    private static int getBestPath(Node n) {
        if (n.l == null && n.r == null) {
            return n.value;
        }

        int l = getBestPath(n.l);
        int r = getBestPath(n.r);

        return l > r ? n.value + l : n.value + r;
    }

    public static class Node {
        int value;
        Node l;
        Node r;
        Node lparent;
        Node rparent;

        Node(int value) {
            this.value = value;
            this.lparent = null;
            this.rparent = null;
            this.l = null;
            this.r = null;
        }

        public void print() {
            print("", true);
        }

        // https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
        private void print(String prefix, boolean isTail) {
            LinkedList<Node> children = new LinkedList<>();
            if (l != null) children.add(l);
            if (r != null) children.add(r);
            System.out.println(prefix + (isTail ? "└── " : "├── ") + value);
            for (int i = 0; i < children.size() - 1; i++) {
                children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
            }
            if (children.size() > 0) {
                children.get(children.size() - 1)
                        .print(prefix + (isTail ?"    " : "│   "), true);
            }
        }
    }
}
