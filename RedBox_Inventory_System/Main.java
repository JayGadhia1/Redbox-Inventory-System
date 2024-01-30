import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinTree<DVD> tree = new BinTree<>();

        // Prompt for and read inventory file
        System.out.print("Enter inventory file name: ");
        String inventoryFileName = scanner.nextLine();
        processInventoryFile(inventoryFileName, tree);

        // Prompt for and read transaction log
        System.out.print("Enter transaction log file name: ");
        String transactionFileName = scanner.nextLine();
        processTransactionFile(transactionFileName, tree);
        
        generateReports(tree);

        scanner.close();
    }

    private static void processInventoryFile(String fileName, BinTree<DVD> tree) {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.replace("\"", "").split(",");
                String title = parts[0];
                int available = Integer.parseInt(parts[1]);
                int rented = Integer.parseInt(parts[2]);
                tree.insert(new DVD(title, available, rented));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    private static void processTransactionFile(String fileName, BinTree<DVD> tree) {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                handleTransaction(line, tree);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    private static void handleTransaction(String transaction, BinTree<DVD> tree) {
        String[] parts = transaction.split(" \"", 2); // Split into action and the rest
    String action = parts[0].trim(); // Action part (rent, add, return, remove)

    // The remaining part of the string, which includes the title and possibly a quantity
    String details = parts[1].trim(); 

    // Find the last index of the quotation mark to separate the title from the quantity
    int lastIndexQuote = details.lastIndexOf("\"");
    String title = details.substring(0, lastIndexQuote).trim(); // Extract title

    // Extract the quantity part (if present) and parse it
    String quantityStr = details.substring(lastIndexQuote + 1).trim();
    int quantity = quantityStr.isEmpty() ? 0 : Integer.parseInt(quantityStr.replaceAll("[^0-9]", "")); // Parse quantity

    Node<DVD> node = tree.search(new DVD(title, 0, 0));
    
        switch (action) {
            case "rent":
                if (node != null) {
                    DVD dvd = node.getPayload();
                    // Check if there are available copies to rent
                    if (dvd.getAvailable() > 0) {
                        dvd.setAvailable(dvd.getAvailable() - 1);
                        dvd.setRented(dvd.getRented() + 1);
                    } else {
                        System.out.println("No copies available to rent for: " + title);
                    }
                } else {
                    System.out.println("Title not found for rent: " + title);
                }
                break;

            case "add":
                if (node != null) {
                    DVD dvd = node.getPayload();
                    dvd.setAvailable(dvd.getAvailable() + quantity);
                } else {
                    tree.insert(new DVD(title, quantity, 0));
                }
                break;
            case "return":
                if (node != null) {
                    DVD dvd = node.getPayload();
                    dvd.setAvailable(dvd.getAvailable() + 1); // Increase the available count
                    dvd.setRented(dvd.getRented() - 1); // Decrease the rented count

                    // Debugging statement
                    System.out.println("Returned: " + title + ", Available: " + dvd.getAvailable() + ", Rented: " + dvd.getRented());
                } else {
                    // Debugging statement if the DVD is not found
                    System.out.println("DVD not found for return: " + title);
                }
                break;            
                case "remove":
                if (node != null) {
                    DVD dvd = node.getPayload();
                    int currentAvailable = dvd.getAvailable();
                    int newAvailable = currentAvailable - quantity; // Use 'quantity' directly
            
                    // Ensure the available count does not go below zero
                    if (newAvailable < 0) {
                        newAvailable = 0;
                    }
            
                    dvd.setAvailable(newAvailable);
            
                    // Delete the node if no copies are available or rented
                    if (newAvailable == 0 && dvd.getRented() == 0) {
                        tree.delete(new DVD(title, 0, 0));
                    }
                }
                break;
                        

        }
    }    

    private static void generateReports(BinTree<DVD> tree) {
        tree.printTree();
        try (PrintWriter writer = new PrintWriter("inventory.out")) {
            tree.printTreePreOrder(writer);
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file: inventory.out");
        }
    }
}
