public class Main {
    public static void main(String[] args) {
        var p1 = new Polynomial();
        p1.add(5, 2);
        p1.add(7, 1);

        var p2 = new Polynomial();
        p2.add(5, 7);

        p1.add(1, 0);

        p2.add(3, 1);

        System.out.println(p1);
        System.out.println(p2);

        var p3 = new Polynomial("2x^2 + 3x^1");
        System.out.println(p3);
    }
}

class Polynomial {
    public static Node[] cells = new Node[1000];
    public static int firstEmptyCellIndex = 0;

    public int start;
    public int end;

    public Polynomial() {
        start = firstEmptyCellIndex;
        end = start - 1;
    }

    public Polynomial(String expression) {
        this();

        var parts = expression.split(" \\+ ");

        for (var part : parts) {
            var numbers = part.split("x\\^");
            var coefficient = Integer.parseInt(numbers[0]);
            var exponent = Integer.parseInt(numbers[1]);

            this.add(coefficient, exponent);
        }
    }

    public void add(int coefficient, int exponent) {
        if (firstEmptyCellIndex > cells.length - (end - start))
            throw new RuntimeException("Full!");

        for (int i = start; i <= end; i++)
            if (cells[i].exponent == exponent)
                throw new RuntimeException("Duplicate Exponent!");

        for (
                int originalCellIndex = start, newCellIndex = firstEmptyCellIndex;
                originalCellIndex <= end;
                originalCellIndex++, newCellIndex++
        ) {
            cells[newCellIndex] = cells[originalCellIndex];
        }

        end = firstEmptyCellIndex + end - start + 1;
        start = firstEmptyCellIndex;
        firstEmptyCellIndex = end + 1;

        cells[end] = new Node(coefficient, exponent);
    }

//    public void remove(int coefficient, int exponent) {
//        // TODO: your code ...
//    }
//
//    public Polynomial plus(Polynomial other) {
//        // TODO: your code ...
//    }
//
//    public Polynomial minus(Polynomial other) {
//        // TODO: your code ...
//    }
//
//    public Polynomial times(Polynomial other) {
//        // TODO: your code ...
//    }

    public boolean isZero() {
        for (int i = start; i <= end; i++)
            if (cells[i].coefficient != 0)
                return false;

        return true;
    }

    public int getCoefficient(int exponent) {
        for (int i = start; i <= end; i++)
            if (cells[i].exponent == exponent)
                return cells[i].coefficient;

        return 0;
    }

    public int getMaximumExponent() {
        int maximumExponent = 0;

        for (int i = start; i <= end; i++)
            if (maximumExponent < cells[i].exponent)
                maximumExponent = cells[i].exponent;

        return maximumExponent;
    }

    @Override
    public String toString() {
        var result = "";

        for (int i = start; i <= end; i++)
            result += cells[i].coefficient + "x^" + cells[i].exponent + "\t|\t";

        return result;
    }
}

class Node {
    public int coefficient;
    public int exponent;

    public Node(int coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }
}
