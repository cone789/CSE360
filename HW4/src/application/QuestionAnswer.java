package application;

import java.util.*;

public class QuestionAnswer {

	// Question class 
    static class Question {
        int id;
        String text;
        // list of answers associated with each question 
        List<Answer> answers = new ArrayList<>();
        
        // pinned and thread properties
        boolean isPinned = false; 
        int threadId;

        Question(int id, String text, int threadId) {
            this.id = id;
            this.text = text;
            this.threadId = threadId;
        }
    }
    
    // Answer class 
    static class Answer {
        String text;
        boolean isPinned = false;

        Answer(String text) {
            this.text = text;
        }
    }
    
    // Staff can create threads to which students will post questions 
    static class Thread {
        int id;
        String title;
        boolean isOpen = true;

        Thread(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    // Staff class with special permissions associated 
    static class Staff {
        String name;

        Staff(String name) {
            this.name = name;
        }
    }

    private static final Map<Integer, Question> questions = new HashMap<>();
    private static final Map<Integer, Thread> threads = new HashMap<>();
    private static int questionCount = 1;
    private static int threadCount = 1;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        QuestionAnswer app = new QuestionAnswer();
        app.runConsoleApp();
    }

    private void runConsoleApp() {
        System.out.println("Choose mode:\n1. Manual Mode\n2. Run Automated Tests");
        String mode = scanner.nextLine();

        if (mode.equals("2")) {
            runAllTests();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Create Thread");
            System.out.println("2. Create Question");
            System.out.println("3. Create Answer");
            System.out.println("4. Display All");
            System.out.println("5. Pin Question/Answer");
            System.out.println("6. Close Thread");
            System.out.println("7. Delete Question/Answer");
            System.out.println("8. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": createThread(); break;
                case "2": createQuestion(); break;
                case "3": createAnswer(); break;
                case "4": displayAll(); break;
                case "5": pinContent(); break;
                case "6": closeThread(); break;
                case "7": deleteContent(); break;
                case "8": running = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // create a thread for a specific topic 
    private void createThread() {
        System.out.print("Enter thread title: ");
        String title = scanner.nextLine();
        threads.put(threadCount, new Thread(threadCount, title));
        System.out.println("Created thread with ID: " + threadCount);
        threadCount++;
    }

    // post a question to a specific valid thread 
    private void createQuestion() {
        System.out.print("Enter thread ID to post the question to: ");
        int threadId = Integer.parseInt(scanner.nextLine());

        Thread thread = threads.get(threadId);
        if (thread == null || !thread.isOpen) {
            System.out.println("Invalid or closed thread.");
            return;
        }

        System.out.print("Enter question text: ");
        String text = scanner.nextLine();
        questions.put(questionCount, new Question(questionCount, text, threadId));
        System.out.println("Created question with ID: " + questionCount);
        questionCount++;
    }

    // post an answer to a valid question ID 
    private void createAnswer() {
        System.out.print("Enter question ID to answer: ");
        int qid = Integer.parseInt(scanner.nextLine());

        Question q = questions.get(qid);
        if (q == null) {
            System.out.println("Question not found.");
            return;
        }

        System.out.print("Enter answer text: ");
        String text = scanner.nextLine();
        q.answers.add(new Answer(text));
        System.out.println("Answer added.");
    }

    // display all information (threads, questions, answers, pins) 
    private void displayAll() {
        for (Thread thread : threads.values()) {
            System.out.println("\nThread ID: " + thread.id + " | Title: " + thread.title + (thread.isOpen ? " (Open)" : " (Closed)"));
            for (Question q : questions.values()) {
                if (q.threadId == thread.id) {
                    String pinnedQ = q.isPinned ? " [PINNED]" : "";
                    System.out.println("  Question ID: " + q.id + pinnedQ + " | " + q.text);
                    for (int i = 0; i < q.answers.size(); i++) {
                        Answer a = q.answers.get(i);
                        String pinnedA = a.isPinned ? " [PINNED]" : "";
                        System.out.println("    Answer " + (i+1) + ": " + a.text + pinnedA);
                    }
                }
            }
        }
    }

    // pin a question or an answer (only staff have this permission) 
    private void pinContent() {
        System.out.println("1. Pin Question\n2. Pin Answer");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.print("Enter question ID to pin: ");
            int qid = Integer.parseInt(scanner.nextLine());
            Question q = questions.get(qid);
            if (q != null) {
                q.isPinned = true;
                System.out.println("Question pinned.");
            } else {
                System.out.println("Question not found.");
            }
        } else if (choice.equals("2")) {
            System.out.print("Enter question ID to find the answer: ");
            int qid = Integer.parseInt(scanner.nextLine());
            Question q = questions.get(qid);
            if (q == null || q.answers.isEmpty()) {
                System.out.println("Question or answers not found.");
                return;
            }
            System.out.print("Enter answer index to pin (starting from 1): ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < q.answers.size()) {
                q.answers.get(index).isPinned = true;
                System.out.println("Answer pinned.");
            } else {
                System.out.println("Invalid answer index.");
            }
        }
    }

    // staff role can close a thread to prevent more posting 
    private void closeThread() {
        System.out.print("Enter thread ID to close: ");
        int tid = Integer.parseInt(scanner.nextLine());
        Thread t = threads.get(tid);
        if (t != null) {
            t.isOpen = false;
            System.out.println("Thread closed.");
        } else {
            System.out.println("Thread not found.");
        }
    }

    // staff role can delete questions or answers which are inappropriate or irrelevant 
    private void deleteContent() {
        System.out.println("1. Delete Question\n2. Delete Answer");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.print("Enter question ID to delete: ");
            int qid = Integer.parseInt(scanner.nextLine());
            if (questions.remove(qid) != null) {
                System.out.println("Question deleted.");
            } else {
                System.out.println("Question not found.");
            }
        } else if (choice.equals("2")) {
            System.out.print("Enter question ID to delete answer from: ");
            int qid = Integer.parseInt(scanner.nextLine());
            Question q = questions.get(qid);
            if (q != null && !q.answers.isEmpty()) {
                System.out.print("Enter answer index to delete (starting from 1): ");
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index >= 0 && index < q.answers.size()) {
                    q.answers.remove(index);
                    System.out.println("Answer deleted.");
                } else {
                    System.out.println("Invalid index.");
                }
            } else {
                System.out.println("Question not found or no answers to delete.");
            }
        }
    }

    // automated JUnit tests 
    private void runAllTests() {
        createThread("Chapter 1");
        createQuestion("What is Java?", 1);
        createAnswer(1, "A programming language.");
        pinQuestion(1);
        pinAnswer(1, 0);
        closeThread(1);
        deleteAnswer(1, 0);
        deleteQuestion(1);
        displayAll();
    }

    // Helpers for tests
    private void createThread(String title) {
        threads.put(threadCount, new Thread(threadCount, title));
        threadCount++;
    }

    private void createQuestion(String text, int threadId) {
        questions.put(questionCount, new Question(questionCount, text, threadId));
        questionCount++;
    }

    private void createAnswer(int qid, String text) {
        Question q = questions.get(qid);
        if (q != null) q.answers.add(new Answer(text));
    }

    private void pinQuestion(int qid) {
        Question q = questions.get(qid);
        if (q != null) q.isPinned = true;
    }

    private void pinAnswer(int qid, int index) {
        Question q = questions.get(qid);
        if (q != null && index < q.answers.size()) q.answers.get(index).isPinned = true;
    }

    private void closeThread(int tid) {
        Thread t = threads.get(tid);
        if (t != null) t.isOpen = false;
    }

    private void deleteQuestion(int qid) {
        questions.remove(qid);
    }

    private void deleteAnswer(int qid, int index) {
        Question q = questions.get(qid);
        if (q != null && index < q.answers.size()) q.answers.remove(index);
    }
}
