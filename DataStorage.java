import java.io.*;
import java.util.*;

public class DataStorage {
    private static final String FILE_NAME = "data.txt";

    public static boolean verifyUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveUsers(List<String[]> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String[] user : users) {
                writer.write(String.join(",", user) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + password + ",0,0,0\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBill(String username, int month, double amount) {
        List<String> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    double totalBill = Double.parseDouble(data[2]) + amount;
                    line = data[0] + "," + data[1] + "," + totalBill + "," + amount + "," + month;
                }
                users.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String user : users) {
                writer.write(user + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getUsers() {
        List<String[]> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
