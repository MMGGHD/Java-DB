package dao;

import db.DBConnection;
import model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// SRP (Single Responsibility Principle) 단일책임의 원칙, SOILD원칙중 일부
// DAO = Data Access Object << DB접근 미들웨어, 필요 메서드를 들고만 있음

public class AccountDAO {
    Connection conn;
    public  AccountDAO(){
        conn = DBConnection.getInstance();
    }
    public void insert(int accountNumber, String accountPassword, int balance) {

        //1. DB연결

        //2. 버퍼로 SQL 쓰기 (버퍼는 만들어 져있다 << 쓰기만 하면됨)
        try {
            String sql =
                    "insert into account_tb(account_number, account_password, " +
                            "account_balance, account_created_at) " +
                            "values (?, ?, ?, now())";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, accountNumber);
            statement.setString(2, accountPassword);
            statement.setInt(3, balance);

            int result = statement.executeUpdate(); // Flush(변경된 row 카운트를 응답)
            System.out.println("결과 : " + result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void update(int balance, int accountNumber) {
        //1. DB연결(소켓 연결 등등)

        //2. 버퍼로 SQL 쓰기 (버퍼는 만들어 져있다 << 쓰기만 하면됨)
        try {
            String sql =
                    "update account_tb set account_balance = ? where account_number = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, balance);
            statement.setInt(2, accountNumber);

            int result = statement.executeUpdate(); // Flush(변경된 row 카운트를 응답)
            System.out.println("결과 : " + result);
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void delete(int accountNumber) {

        //1. DB연결(소켓 연결 등등)

        //2. 버퍼로 SQL 쓰기 (버퍼는 만들어 져있다 << 쓰기만 하면됨)
        try {
            String sql =
                    "delete from account_tb where account_number = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, accountNumber);

            int result = statement.executeUpdate(); // Flush(변경된 row 카운트를 응답)
            System.out.println("결과 : " + result);
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Account SelectOne(int accountNumber) throws SQLException {

        String sql = "select * from account_tb where account_number = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, accountNumber);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return buildAccountFromResultSet(rs);
                }
                return null;
            }
        }
    }

    // 다수의 계좌를 while을 통해 불러낸다 = 다수를 담을수 있는 List 필요
    public List<Account> selectAll() throws SQLException {
        List<Account> account = new ArrayList<>();
        String sql = "select * from account_tb";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    account.add(buildAccountFromResultSet(rs));
                }
            }
        }
        return account;
    }

    // "--" 칼럼의 결과인 ResultSet값을 변수에 담는다. (여기서 Account-- 변수는 지역변수)
    // 담은 변수를 빌더를 통해 Account 객체의 생성자에 전달한다.
    // 가져온 정보가 담긴 Account객체를 써먹을수 있게 됨
    private Account buildAccountFromResultSet(ResultSet resultSet) throws SQLException {
        int accountNumber = resultSet.getInt("account_number");
        String accountPassword = resultSet.getString("account_password");
        int accountBalance = resultSet.getInt("account_balance");
        Timestamp accountCreatedAt = resultSet.getTimestamp("account_Created_At");

        return Account.builder()
                .accountNumber(accountNumber)
                .accountPassword(accountPassword)
                .accountBalance(accountBalance)
                .accountCreatedAt(accountCreatedAt).build();
    }

}
