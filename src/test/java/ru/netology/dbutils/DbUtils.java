package ru.netology.dbutils;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {

    @SneakyThrows
    public static void clearDB() {
        var deleteCreditTable = "DELETE FROM credit_request_entity";
        var deletePaymentTable = "DELETE FROM payment_entity";
        var deleteOrderTable = "DELETE FROM order_entity";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "user", "pass")
        ) {
            runner.update(conn, deleteCreditTable);
            runner.update(conn, deletePaymentTable);
            runner.update(conn, deleteOrderTable);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @SneakyThrows
    public static int getCountLinesOrderEntity() {
        var countSQL = "SELECT COUNT(*) FROM order_entity;";
        var result = 0;
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var countStmt = conn.createStatement();
        ) {
            try (var rs = countStmt.executeQuery(countSQL)) {
                if (rs.next()) {
                    var count = rs.getInt(1);
                    result = count;
                }
            }
        }
        return result;
    }

    @SneakyThrows
    public static int getCountLinesPaymentEntity() {
        var countSQL = "SELECT COUNT(*) FROM payment_entity;";
        var result = 0;
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var countStmt = conn.createStatement();
        ) {
            try (var rs = countStmt.executeQuery(countSQL)) {
                if (rs.next()) {
                    var count = rs.getInt(1);
                    result = count;
                }
            }
        }
        return result;
    }

    @SneakyThrows
    public static int getCountLinesCreditRequestEntity() {
        var countSQL = "SELECT COUNT(*) FROM credit_request_entity;";
        var result = 0;
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var countStmt = conn.createStatement();
        ) {
            try (var rs = countStmt.executeQuery(countSQL)) {
                if (rs.next()) {
                    var count = rs.getInt(1);
                    result = count;
                }
            }
        }
        return result;
    }


    @Value
    public static class CreditRequestEntityTable {
        private String id;
        private String bankId;
        private String created;
        private String status;

    }

    @Value
    public static class OrderEntityTable {
        private String id;
        private String created;
        private String creditId;
        private String paymentId;

    }

    @Value
    public static class PaymentEntityTable {
        private String id;
        private String created;
        private String status;
        private String transactionId;

    }

}
