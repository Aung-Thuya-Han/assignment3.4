import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        long current = 0;
        long next = 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("fibonacci.csv"))) {
            writer.write("Fibonacci");
            writer.newLine();

            for (int i = 0; i < 60; i++) {
                writer.write(Long.toString(current));
                writer.newLine();

                long sum = current + next;
                current = next;
                next = sum;
            }
        } catch (IOException e) {
            System.err.println("Could not write the CSV: " + e.getMessage());
        }
    }
}
