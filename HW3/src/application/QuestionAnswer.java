package application;

import java.util.*;

public class QuestionAnswer {
	
	// Question class with properties for question IDs, text, and a list of associated answers 
    static class Question {
        int id;
        String text;
        List<Answer> answers = new ArrayList<>();

        Question(int id, String text) {
            this.id = id;
            this.text = text;
        }
    }
    
    // Answer class with a property for text 
    static class Answer {
        String text;

        Answer(String text) {
            this.text = text;
        }
    }

    private static final Map<Integer, Question> questions = new HashMap<>();
    private static int questionCount = 1;

    public static void main(String[] args) {
    	QuestionAnswer app = new QuestionAnswer();
        app.runConsoleApp(); 
    }

    // run as stand alone console app rather than GUI application 
    private void runConsoleApp() {
    	
    	// allow the user to choose manual mode or automated test mode and proceed accordingly 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose mode:");
        System.out.println("1. Manual Mode");
        System.out.println("2. Run Automated Tests");
        String mode = scanner.nextLine();

        if (mode.equals("2")) {
        	// run all automated tests, then exit the program 
            runAllTests();
            return; 
        }
        
        boolean running = true;
        
        // manual input loop 
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Create Question");
            System.out.println("2. Create Answer");
            System.out.println("3. Display All");
            System.out.println("4. Delete Question by ID");
            System.out.println("5. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createQuestion(scanner);
                    break;
                case "2":
                    createAnswer(scanner);
                    break;
                case "3":
                    displayAll();
                    break;
                case "4":
                    deleteQuestion(scanner);
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
        System.exit(0);
    }
    
    private void runAllTests() {
        System.out.println("Running automated tests...\n");

        testCreateQuestions();
        testCreateAnswers();
        testDeleteQuestions();
        testViewUpdates();
    }

    // automated test to create questions 
    private void testCreateQuestions() {
        System.out.println("=== TEST: Create Questions ===");
        String[] testQuestions = { "Test question?", "Another example", "Question 1" };

        for (String q : testQuestions) {
            Question question = new Question(questionCount++, q);
            questions.put(question.id, question);
            System.out.println("Created question ID " + question.id + ": " + question.text);
        }

        displayAll();
    }
    
    // automated test to create answers by question ID 
    private void testCreateAnswers() {
        System.out.println("\n=== TEST: Create Answers for Valid and Invalid Questions ===");

        int[] questionIDs = {1, 3};
        String[] answers = {"This is my answer", "Another answer"};

        for (int i = 0; i < questionIDs.length; i++) {
            Question q = questions.get(questionIDs[i]);
            if (q != null) {
                q.answers.add(new Answer(answers[i % answers.length]));
                System.out.println("Added answer to question ID " + q.id);
            } else {
                System.out.println("Error: Question ID " + questionIDs[i] + " not found.");
            }
        }

        displayAll();
    }
    
    // automated test to delete questions by ID 
    private void testDeleteQuestions() {
        System.out.println("\n=== TEST: Deletion of Questions ===");

        int[] deleteIDs = {1, 3};

        for (int id : deleteIDs) {
            if (questions.containsKey(id)) {
                questions.remove(id);
                System.out.println("Deleted question with ID " + id);
            } else {
                System.out.println("Error: Cannot delete question ID " + id + " (not found)");
            }
        }

        displayAll();
    }
    
    // automated test to view all question/answer data 
    private void testViewUpdates() {
        System.out.println("\n=== TEST: Viewing Questions and Answers ===");
        displayAll(); 
    }
    
    // creates a question through console input 
    private void createQuestion(Scanner scanner) {
        System.out.print("Enter question text: ");
        String text = scanner.nextLine();
        Question q = new Question(questionCount++, text);
        questions.put(q.id, q);
        System.out.println("Question created with ID: " + q.id);
    }

    // creates an answer through console input and question ID 
    private void createAnswer(Scanner scanner) {
        System.out.print("Enter question ID to attach the answer to: ");
        int id = Integer.parseInt(scanner.nextLine());

        Question q = questions.get(id);
        if (q != null) {
            System.out.print("Enter answer text: ");
            String text = scanner.nextLine();
            q.answers.add(new Answer(text));
            System.out.println("Answer added.");
        } else {
            System.out.println("Question ID not found.");
        }
    }
    
    // displays existing question/answer data 
    private void displayAll() {
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        for (Question q : questions.values()) {
            System.out.println("Question ID: " + q.id + " | " + q.text);
            if (q.answers.isEmpty()) {
                System.out.println("  No answers.");
            } else {
                for (int i = 0; i < q.answers.size(); i++) {
                    System.out.println("  Answer " + (i + 1) + ": " + q.answers.get(i).text);
                }
            }
        }
    }

    // deletes a question based on ID 
    private void deleteQuestion(Scanner scanner) {
        System.out.print("Enter question ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (questions.containsKey(id)) {
            questions.remove(id);
            System.out.println("Question and its answers deleted.");
        } else {
            System.out.println("Question ID not found.");
        }
    }
}
