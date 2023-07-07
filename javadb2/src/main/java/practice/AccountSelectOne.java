package practice;

import db.DBConnection;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
    // DB에서 조회에서 Model에 집어넣는것이 목적
public class AccountSelectOne {

    public static void main(String[] args) {
        Account account = null;
        Connection conn = DBConnection.getInstance();

        try {
            String sql = "select * from account_tb where account_number = ?";
            PreparedStatement statement = conn. prepareStatement(sql);
            statement.setInt(1, 1111);

            ResultSet rs =  statement.executeQuery();

            if(rs.next()){
                //rs.get---("속성") 함수는 rs에서 속성의 데이터를 가져오는 함수
                //이것을 account의 생성자에 담는다.
                 account = new Account(
                        rs.getInt("account_number"),
                        rs.getString("account_password"),
                        rs.getInt("account_balance"),
                        rs.getTimestamp("account_created_at"));
            }

            System.out.println("계좌번호: "+account.getAccountNumber());
            System.out.println("계좌번호: "+account.getAccountPassword());
            System.out.println("계좌번호: "+account.getAccountBalance());
            System.out.println("계좌번호: "+account.getAccountCreatedAt());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
