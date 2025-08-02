import java.sql.*;
import java.util.Scanner;

public class Transaction {

    public void viewTransaction(Connection connection, Scanner scanner) {
        String query = "select * from transactions where account_id = ?";

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("Enter Account Id : ");
            int accountId = scanner.nextInt();
            scanner.nextLine();
            preparedStatement.setInt(1,accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                int amount = resultSet.getInt("amount");
                String transactionType = resultSet.getString("transaction_type");
                Timestamp transactionTime = resultSet.getTimestamp("transaction_time");

                System.out.println("+-----------------------------------------------+");
                System.out.println("Transaction Id : "+transactionId);
                System.out.println("Transaction Amount : "+ amount);
                System.out.println("Tranasaction Type : "+ transactionType);
                System.out.println("Transaction Time : "+transactionTime);
            }
            connection.commit();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdrawlMoney(Connection connection, Scanner scanner) {
        String query = "select balance from accounts where account_id = ?";
        String query2 = "insert into transactions(account_id, amount, transaction_type, description) values(?, ?, ?, ?)";
        String query3 = "update accounts set balance = ? where account_id = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("Enter AccountId : ");
            int accountId = scanner.nextInt();
            scanner.nextLine();
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int balance = 0;
            while(resultSet.next()){
                balance = resultSet.getInt("balance");
            }

            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
            System.out.print("Enter your withdrawl amount : ");
            int amount = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Description : ");
            String description = scanner.nextLine();
            preparedStatement1.setInt(1, accountId);
            preparedStatement1.setInt(2, amount);
            preparedStatement1.setString(3, "Withdrawl");
            preparedStatement1.setString(4, description);
            int rowAffected = preparedStatement1.executeUpdate();
//            if(rowAffected > 0) {
//                System.out.println("Inserted Successfully");
//            }else {
//                System.out.println("Inserted Failed");
//            }

            if(amount < balance) {
                balance -= amount;
                PreparedStatement preparedStatement2 = connection.prepareStatement(query3);
                preparedStatement2.setInt(1, balance);
                preparedStatement2.setInt(2, accountId);
                int rowsAffected = preparedStatement2.executeUpdate();
                if(rowsAffected > 0){
                    System.out.println("Balance Updation Successfully!");
                }else {
                    System.out.println("Balance Updation Failed");
                }

                while(resultSet.next()){
                    balance = resultSet.getInt("balance");
                }
                System.out.println("Balance : "+ balance);
                connection.commit();
            }else {
                System.out.println("Not Sufficient Balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void depositMoney(Connection connection, Scanner scanner){
        String query = "select balance from accounts where account_id = ?";
        String query2 = "insert into transactions(account_id, amount, transaction_type, description) values(?, ?, ?, ?)";
        String query3 = "update accounts set balance = ? where account_id = ?";
        int balance = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("Enter your AccountId : ");
            int accountId = scanner.nextInt();
            scanner.nextLine();
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                balance = resultSet.getInt("balance");
            }

            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
            System.out.print("Enter your depoist amount : ");
            int amount = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Description : ");
            String description = scanner.nextLine();
            preparedStatement1.setInt(1, accountId);
            preparedStatement1.setInt(2, amount);
            preparedStatement1.setString(3, "Deposit");
            preparedStatement1.setString(4, description);
            int rowAffected = preparedStatement1.executeUpdate();
//            if(rowAffected > 0) {
//                System.out.println("Transaction Updated Successfully!");
//            }else {
//                System.out.println("Transaction Updation Failed");
//            }

            balance += amount;
            PreparedStatement preparedStatement2 = connection.prepareStatement(query3);
            preparedStatement2.setInt(1, balance);
            preparedStatement2.setInt(2, accountId);
            int rowsAffected = preparedStatement2.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Balance Updation Successfully!");
            }else {
                System.out.println("Balance Updation Failed");
            }

            while(resultSet.next()){
                balance = resultSet.getInt("balance");
            }
            System.out.println("Balance : "+ balance);
            connection.commit();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }
}
