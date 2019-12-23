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
	// 1. �α��� �� ȸ���� �����͸� �Է� (insert)
		public int getLoginCustomerregiste(CustomerVO lvo) throws Exception {
			// �� ������ ó���� ���� SQL ��
			// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
			String dml = "insert into logincustomer "
					+ "(id, password, name, phone, email)" + " values "
					+ "(?, ?, ?, ?, ?)";

			Connection con = null;
			PreparedStatement pstmt = null;
			int count = 0;
			try {
				// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
				con = DBUtil.getConnection();

				// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
				pstmt = con.prepareStatement(dml);			
				pstmt.setString(1, lvo.getId());
				pstmt.setString(2, lvo.getPassword());
				pstmt.setString(3, lvo.getName());
				pstmt.setString(4, lvo.getPhone());
				pstmt.setString(5, lvo.getEmail());

				// �� SQL���� ������ ó�� ����� ����
				count = pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("e=[" + e + "]");
			} catch (Exception e) {
				System.out.println("e=[" + e + "]");
			} finally {
				try {
					// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
			return count;
		}
		// 2. �α��� �� ���� ���������� �α����� �� ã�� ��� (select * form hrvtbl where name like %�浿%)
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
