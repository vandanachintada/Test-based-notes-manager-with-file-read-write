import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ---------------- Note Class ----------------
class Note {
    private String content;

    public Note(String content) {
        this.content = content;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return content;
    }
}

// ---------------- Notes Manager ----------------
class NotesManager {
    private List<Note> notes = new ArrayList<>();
    private String fileName;

    public NotesManager(String fileName) {
        this.fileName = fileName;
        loadFromFile();
    }

    public void addNote(String content) {
        notes.add(new Note(content));
        System.out.println("✅ Note added!");
        saveToFile();
    }

    public void viewNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }
        System.out.println("\n--- Your Notes ---");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i));
        }
    }

    public void deleteNote(int index) {
        if (index >= 0 && index < notes.size()) {
            notes.remove(index);
            System.out.println("✅ Note deleted!");
            saveToFile();
        } else {
            System.out.println("❌ Invalid note number.");
        }
    }

    // Save notes to file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Note note : notes) {
                writer.write(note.getContent());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving notes: " + e.getMessage());
        }
    }

    // Load notes using FileReader + BufferedReader
    private void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notes.add(new Note(line));
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading notes: " + e.getMessage());
        }
    }
}

// ---------------- Main App ----------------
public class NotesApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NotesManager manager = new NotesManager("notes.txt");

        int choice;
        do {
            System.out.println("\n===== Notes Manager =====");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete Note");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter note content: ");
                    String content = sc.nextLine();
                    manager.addNote(content);
                }
                case 2 -> manager.viewNotes();
                case 3 -> {
                    manager.viewNotes();
                    System.out.print("Enter note number to delete: ");
                    int num = sc.nextInt();
                    sc.nextLine();
                    manager.deleteNote(num - 1);
                }
                case 4 -> System.out.println(" Goodbye!");
                default -> System.out.println("❌ Invalid choice.");
            }
        } while (choice != 4);

        sc.close();
    }
}
