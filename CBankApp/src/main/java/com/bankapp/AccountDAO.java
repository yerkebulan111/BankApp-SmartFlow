package com.bankapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    public boolean createAccount(int userId) {
        String sql = "INSERT INTO accounts (user_id, balance) VALUES (?, 0)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            //System.out.println("Account created successfully!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error while creating account: " + e.getMessage());
            return false;
        }
    }



    public boolean deposit(int accountId, double amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);

            stmt.executeUpdate();
            //System.out.println("Deposit successful!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error while depositing money: " + e.getMessage());
            return false;
        }
    }



    public boolean withdraw(int accountId, double amount) {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.setDouble(3, amount);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                //System.out.println("Withdrawal successful!");
                return true;
            } else {
                System.out.println("Insufficient balance.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error while withdrawing money: " + e.getMessage());
            return false;
        }
    }



    public double checkBalance(int accountId) {
        String sql = "SELECT balance FROM accounts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Error while checking balance: " + e.getMessage());
        }
        return -1;
    }



    public boolean deleteAccount(int accountId) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting account: " + e.getMessage());
            return false;
        }
    }

}
