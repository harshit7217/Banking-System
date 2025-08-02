import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/banking_system";
        String username = "root";
        String password = "Anika@Tomar";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while(!exit) {
                System.out.println("\n=== BANKING SYSTEM MENU ===");
                System.out.println("1. Create Customer");
                System.out.println("2. Open Account");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. View Transactions");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.println(">> Creating customer...");
                        CreateCustomer createCustomer = new CreateCustomer();
                        createCustomer.createCustomer(connection, scanner);
                        createCustomer.customerId(connection, scanner);
                        break;

                    case 2:
                        System.out.println(">> Opening account...");
                        OpeningAccount openingAccount = new OpeningAccount();
                        openingAccount.openingAccount(connection, scanner);
                        break;

                    case 3:
                        System.out.println(">> Deposit money...");
                        Transaction transaction = new Transaction();
                        transaction.depositMoney(connection, scanner);
                        break;

                    case 4:
                        System.out.println(">> Withdraw money...");
                        Transaction transaction2 = new Transaction();
                        transaction2.withdrawlMoney(connection, scanner);
                        break;

                    case 5:
                        System.out.println(">> Viewing transactions...");
                        Transaction transaction1 = new Transaction();
                        transaction1.viewTransaction(connection, scanner);
                        break;

                    case 6:
                        System.out.println(">> Exiting system. Goodbye!");
                        exit = true;
                        break;

                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            }

            scanner.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}