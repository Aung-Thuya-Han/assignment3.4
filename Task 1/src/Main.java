import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        double sum = 0;
        int count = 0;

        try {
            URL url = URI.create("https://users.metropolia.fi/~jarkkov/temploki.csv").toURL();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String header = reader.readLine();
                if (header == null) {
                    System.err.println("The CSV file is empty.");
                    return;
                }

                String[] columnNames = header.split(";");
                int temperatureColumn = -1;
                for (int i = 0; i < columnNames.length; i++) {
                    if (columnNames[i].equals("UlkoTalo")) {
                        temperatureColumn = i;
                        break;
                    }
                }

                if (temperatureColumn == -1) {
                    System.err.println("The UlkoTalo column was not found.");
                    return;
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("01.01.2023 ")) {
                        String[] columns = line.split(";", -1);
                        if (columns.length <= temperatureColumn) {
                            throw new IOException("Missing UlkoTalo column in a data row.");
                        }
                        double temperature = Double.parseDouble(columns[temperatureColumn].replace(',', '.'));
                        sum += temperature;
                        count++;
                    }
                }
            }

            if (count == 0) {
                System.out.println("No readings for 1 January 2023.");
            } else {
                double average = sum / count;
                System.out.printf(Locale.ROOT, "Average: %.2f °C (%d readings)%n", average, count);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Could not read temperatures: " + e.getMessage());
        }
    }
}
