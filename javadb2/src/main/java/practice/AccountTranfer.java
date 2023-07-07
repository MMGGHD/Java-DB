package practice;

import com.sun.tools.javac.Main;
import dao.AccountDAO;
import db.DBConnection;
import model.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AccountTranfer {

    public static void main(String[] args) {
        AccountTranfer at = new AccountTranfer();
        AccountDAO ad = new AccountDAO();
        // 1. 이체 정보 받기
        String wAccountPassword = "1234";
        int wAccountNumber = 1111;
        int dAccountNumber = 2222;
        int amount = 100;

        // 2. 송금 요건 확인 (유효성 검사)
        if (amount <= 0) {
            System.out.println("0보다 적은금액을 송금할수 없습니다.");
            return;
        }
        if (!wAccountPassword.matches("\\d+")) {
            System.out.println("숫자만 입력하세요.");
            return;
        }
        if (wAccountPassword.length() > 4) {
            System.out.println("4자리 숫자만 입력하세요.");
            return;
        }

        // 3. 동일 계좌 확인
        if (wAccountNumber == dAccountNumber) {
            System.out.println("입 출금 계좌가 동일할 수 없습니다.");
            return;
        }
        // ------------------------------------------- 트랜젝션 시작

        try {
            // 4. 계좌 존재여부 확인
            Account wAccount = ad.SelectOne(wAccountNumber);
            Account dAccount = ad.SelectOne(dAccountNumber);
            if(wAccount == null){
                System.out.println("출금 계좌를 찾을수 없습니다.");
                 throw new RuntimeException();
            }
            if(dAccount == null){
                System.out.println("입금 계좌를 찾을수 없습니다.");
                throw new RuntimeException();
            }


            // 5. 잔액 확인
            if(wAccount.getAccountBalance()<amount){
                System.out.println("잔액이 부족합니다");
                System.out.println("잔액 : "+wAccount.getAccountBalance());
                throw new RuntimeException();
            }

            // 6. 계좌 비밀번호 확인
            if (!wAccountPassword.equals(wAccount.getAccountPassword())){
                System.out.println("계좌 비밀번호를 확인하세요");
                throw new RuntimeException();
            }
    
            // 7. 계좌 업데이트
            ad.update(wAccount.getAccountBalance()-amount,wAccountNumber);
            ad.update(dAccount.getAccountBalance()+amount,dAccountNumber);




            System.out.println("문제 없음");
        }catch (Exception e){
                e.printStackTrace();
        }

    }
 {
    }
}
