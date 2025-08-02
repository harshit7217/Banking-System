import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class OpeningAccount {

    public void openingAccount(Connection connection, Scanner scanner) {
        String query = "INSERT INTO accounts(customer_id, account_type) values (?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("CustomerId : ");
            int customerId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Account Type : ");
            String accountType = "";
            boolean check = true;
            while(check){
                System.out.println("1. Saving Account");
                System.out.println("2. Current Account");
                System.out.println("3. Fixed Deposit");
                System.out.println("4. Recurring Deposit");
                System.out.println("Enter Your Choice : ");
                int val = scanner.nextInt();
                scanner.nextLine();
                switch(val) {
                    case 1 :
                        accountType = "Saving Account";
                        check = false;
                        break;
                    case 2:
                        accountType = "Current Account";
                        check = false;
                        break;
                    case 3:
                        accountType = "Fixed Deposit";
                        check = false;
                        break;
                    case 4:
                        accountType = "Recurring Deposit";
                        check = false;
                        break;
                    default :
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            }

            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, accountType);

            int rowAffected = preparedStatement.executeUpdate();
            if(rowAffected > 0){
                System.out.println("Open Account Successfully!");
            }else {
                System.out.println("Opening Account Failed!");
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
