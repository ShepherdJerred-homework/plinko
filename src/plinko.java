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
        while (scanner.hasNextLine()) {
            nextLine = scanner.nextLine();
            if (nextLine.equals("0")) {
                break;
            }

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

            int bestPath = getBestPath(head, new HashMap<>());
            System.out.println(bestPath);
            printWriter.println(bestPath);
        }

        printWriter.close();
    }

    private static int getBestPath(Node n, HashMap<Node, Integer> memo) {
        if (n.l == null && n.r == null) {
            return n.value;
        }

        int l;
        if (memo.containsKey(n.l)) {
            l = memo.get(n.l);
        } else {
            l = getBestPath(n.l, memo);
        }

        int r;
        if (memo.containsKey(n.r)) {
            r = memo.get(n.r);
        } else {
            r = getBestPath(n.r, memo);
        }

        int best = l > r ? n.value + l : n.value + r;
        memo.put(n, best);
        return best;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value &&
                    Objects.equals(l, node.l) &&
                    Objects.equals(r, node.r);
        }

        @Override
        public int hashCode() {
            int lValue = l != null ? l.value : 0;
            int rValue = r != null ? r.value : 0;
            return 31 * value * lValue * rValue;
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
