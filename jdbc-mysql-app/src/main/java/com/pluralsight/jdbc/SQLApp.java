package com.pluralsight.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.Scanner;

public class SQLApp {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/car_dealership";
        String user = "root";
        String password = "YU_oppdivide!20";

        try(Connection connection = DriverManager.getConnection(url, user, password)){

            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM Vehicles");

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                String year = resultSet.getString("year");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                double price = resultSet.getDouble("price");
                System.out.printf("Year: %s Make: %s Model: %s Price: $%.2f \n", year, make, model, price);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
