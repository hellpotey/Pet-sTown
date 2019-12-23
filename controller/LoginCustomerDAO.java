package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.CustomerVO;
import model.HRVVO;
import model.LoginCustomerVO;

public class LoginCustomerDAO {
	// 1. 로그인 한 회원의 데이터를 입력 (insert)
		public int getLoginCustomerregiste(CustomerVO lvo) throws Exception {
			// ② 데이터 처리를 위한 SQL 문
			// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
			String dml = "insert into logincustomer "
					+ "(id, password, name, phone, email)" + " values "
					+ "(?, ?, ?, ?, ?)";

			Connection con = null;
			PreparedStatement pstmt = null;
			int count = 0;
			try {
				// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
				con = DBUtil.getConnection();

				// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
				pstmt = con.prepareStatement(dml);			
				pstmt.setString(1, lvo.getId());
				pstmt.setString(2, lvo.getPassword());
				pstmt.setString(3, lvo.getName());
				pstmt.setString(4, lvo.getPhone());
				pstmt.setString(5, lvo.getEmail());

				// ⑤ SQL문을 수행후 처리 결과를 얻어옴
				count = pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("e=[" + e + "]");
			} catch (Exception e) {
				System.out.println("e=[" + e + "]");
			} finally {
				try {
					// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
			return count;
		}
		// 2. 로그인 한 고객중 마지막으로 로그인한 고객 찾기 기능 (select * form hrvtbl where name like %길동%)
		public LoginCustomerVO getLoginCustomer() {
			String dml = "SELECT * FROM logincustomer";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			LoginCustomerVO retval = null;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					retval = new LoginCustomerVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
				}
			} catch (SQLException se) {
				System.out.println(se);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
			return retval;
		}
}
