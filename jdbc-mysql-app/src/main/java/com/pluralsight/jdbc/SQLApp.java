package com.pluralsight.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.Scanner;

public class SQLApp {
    public static void main(String[] args) {
        BasicDataSource dataSource;
        dataSource = new BasicDataSource();

        String url = "jdbc:mysql://localhost:3306/car_dealership";
//        String user = "root";
//        String password = "YU_oppdivide!20";
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);
        dataSource.setUrl(url);

        Scanner scanley = new Scanner(System.in);
        boolean running = true;

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Vehicles SET make = ? WHERE make = 'Porsche'");
            PreparedStatement statement = connection.prepareStatement("SELECT * from Vehicles WHERE make = ?;");
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO Vehicles (VIN, Year, Make, Model, VehicleType, Color, Odometer, Price, Sold) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );

            while (running) {
                System.out.print("Enter the make you are looking to insert: ");
                String makeToSearchFor = scanley.nextLine();

                if (makeToSearchFor.equalsIgnoreCase("quit")){
                    break;
                }

                // Collect additional input for the 'Vehicles' table
                System.out.print("Enter VIN: ");
                String vin = scanley.nextLine();

                System.out.print("Enter Year: ");
                int year = Integer.parseInt(scanley.nextLine());

                System.out.print("Enter Model: ");
                String model = scanley.nextLine();

                System.out.print("Enter Vehicle Type: ");
                String vehicleType = scanley.nextLine();

                System.out.print("Enter Color: ");
                String color = scanley.nextLine();

                System.out.print("Enter Odometer Reading: ");
                int odometer = Integer.parseInt(scanley.nextLine());

                System.out.print("Enter Price: ");
                double price = Double.parseDouble(scanley.nextLine());

                System.out.print("Is the vehicle sold (true/false)? ");
                boolean sold = Boolean.parseBoolean(scanley.nextLine());

                // Insert the new vehicle into the database
                insertStatement.setString(1, vin);
                insertStatement.setInt(2, year);
                insertStatement.setString(3, makeToSearchFor);
                insertStatement.setString(4, model);
                insertStatement.setString(5, vehicleType);
                insertStatement.setString(6, color);
                insertStatement.setInt(7, odometer);
                insertStatement.setDouble(8, price);
                insertStatement.setBoolean(9, sold);
                insertStatement.executeUpdate();

                System.out.println("Vehicle inserted successfully! :)");

                // Update the make
                updateStatement.setString(1, makeToSearchFor);
                updateStatement.executeUpdate();

                // Query and display vehicles matching the make
                statement.setString(1, makeToSearchFor);
                ResultSet resultSet = statement.executeQuery();

                System.out.println("Vehicles with the make '" + makeToSearchFor + "':");

                while (resultSet.next()) {
                    String make = resultSet.getString("Make");
                    System.out.println(make);
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        try {
            dataSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
