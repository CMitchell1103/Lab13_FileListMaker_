import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;


public class Main {
    private static List<String> itemList = new ArrayList<>();
    private static boolean needsToBeSaved = false;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nList Maker Menu:");
            System.out.println("A - Add an item");
            System.out.println("D - Delete an item");
            System.out.println("I - Insert an item");
            System.out.println("M - Move an item");
            System.out.println("V - View the list");
            System.out.println("O - Open a list file from disk");
            System.out.println("S - Save the list to disk");
            System.out.println("C - Clear the list");
            System.out.println("Q - Quit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    addItem(scanner);
                    break;
                case "D":
                    deleteItem(scanner);
                    break;
                case "I":
                    insertItem(scanner);
                    break;
                case "M":
                    moveItem(scanner);
                    break;
                case "V":
                    viewList();
                    break;
                case "O":
                    openListFromFile(scanner);
                    break;
                case "S":
                    saveListToFile();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    exit = quit(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter the item to add: ");
        String item = scanner.nextLine();
        itemList.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem(Scanner scanner) {
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < itemList.size()) {
            itemList.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem(Scanner scanner) {
        System.out.print("Enter the index to insert at: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index <= itemList.size()) {
            System.out.print("Enter the item to insert: ");
            String item = scanner.nextLine();
            itemList.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void moveItem(Scanner scanner) {
        System.out.print("Enter the index of the item to move: ");
        int oldIndex = Integer.parseInt(scanner.nextLine());
        if (oldIndex >= 0 && oldIndex < itemList.size()) {
            System.out.print("Enter the new index to move the item to: ");
            int newIndex = Integer.parseInt(scanner.nextLine());
            if (newIndex >= 0 && newIndex < itemList.size()) {
                String item = itemList.remove(oldIndex);
                itemList.add(newIndex, item);
                needsToBeSaved = true;
            } else {
                System.out.println("Invalid new index.");
            }
        } else {
            System.out.println("Invalid old index.");
        }
    }

    private static void viewList() {
        System.out.println("Current List:");
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < itemList.size(); i++) {
                System.out.println(i + ": " + itemList.get(i));
            }
        }
    }

    private static void openListFromFile(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save before opening a new list? (y/n): ");
            String saveChoice = scanner.nextLine();
            if (saveChoice.equalsIgnoreCase("y")) {
                saveListToFile();
            }
        }

        System.out.print("Enter the filename to open: ");
        String filename = scanner.nextLine();
        try {
            itemList = FileUtils.loadListFromFile(filename);
            needsToBeSaved = false;
            System.out.println("List loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveListToFile() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty. Nothing to save.");
            return;
        }
        System.out.print("Enter the filename to save the list: ");
        String filename = new Scanner(System.in).nextLine();
        try {
            FileUtils.saveListToFile(itemList, filename);
            needsToBeSaved = false;
            System.out.println("List saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList() {
        itemList.clear();
        needsToBeSaved = true;
    }

    private static boolean quit(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them before quitting? (y/n): ");
            String saveChoice = scanner.nextLine();
            if (saveChoice.equalsIgnoreCase("y")) {
                saveListToFile();
            }
        }
        return true;
    }
}


