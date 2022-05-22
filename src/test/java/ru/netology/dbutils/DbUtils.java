package ru.netology.dbutils;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;

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


    @Value
    public static class CreditRequestEntityTable {
        private String id;
        private String bankId;
        private String created;
        private String status;

        @SneakyThrows

        public static String getStatusCredit() {
            var statusCredit = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";

            String status = null;
            try (
                    var conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/app", "app", "pass"
                    );
                    var countStmt = conn.createStatement();
            ) {
                try (var rs = countStmt.executeQuery(statusCredit)) {
                    if (rs.next()) {
                        status = rs.getString("status");
                    }
                    return status;

                }

            }
        }
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
