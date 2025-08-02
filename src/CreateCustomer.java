import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateCustomer {

    public void customerId(Connection connection, Scanner scanner) {
        String query = "select customer_id from customers where name = ? and email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("Name : ");
            String name = scanner.nextLine();
            System.out.print("Email : ");
            String email = scanner.nextLine();
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("customer_id");
                System.out.println("CustomerId : "+ id);
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createCustomer(Connection connection, Scanner scanner) {
        String query = "INSERT INTO customers(name, email, phone, address) values (?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("Name : ");
            String name = scanner.nextLine();
            System.out.print("Email : ");
            String email = scanner.nextLine();
            System.out.print("Phone : ");
            String phone = scanner.nextLine();
            System.out.print("Address : ");
            String address = scanner.nextLine();

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);

            int rowAffected = preparedStatement.executeUpdate();
            if(rowAffected > 0){
                System.out.println("Customer Created Successfully!");
            }else {
                System.out.println("Customer Created Failed!");
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
