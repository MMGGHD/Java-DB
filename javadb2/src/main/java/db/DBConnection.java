package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getInstance() {
        //MySQL 정보로 연결하기
        String url = "jdbc:mysql://localhost:3306/metadb";
        String username = "root";
        String password = "root1234";

        // 자바와 MySQL간의 프로토콜을 보유하고있는 JDBC 드라이버 설치
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("DB 연결 성공");
            return connection;
        } catch (Exception e) {
            System.out.println("DB 연결 실패" + e.getMessage());
        }

        return null;
    }
}

