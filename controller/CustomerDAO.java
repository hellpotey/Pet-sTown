package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;
import model.HRVVO;
import model.CustomerVO;

public class CustomerDAO {
	// 1. ȸ�����Խ� �����͸� �Է� (insert)
	public int getCustomerregiste(CustomerVO svo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into customer " + "(id, password, name, phone, email)" + " values " + "(?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getId());
			pstmt.setString(2, svo.getPassword());
			pstmt.setString(3, svo.getName());
			pstmt.setString(4, svo.getPhone());
			pstmt.setString(5, svo.getEmail());

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

	// 2. �α��� ��� (select * form customer where name like %�浿%)
	public CustomerVO getCustomerCheck(String id, String password) throws Exception {

		String dml = "SELECT * FROM customer WHERE id = ? and password = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO selectCustomer = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			String id2 = "%" + id + "%";
			String password2 = "%" + password + "%";
			System.out.println(id2);
			System.out.println(password2);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			System.out.println("pstmt= " + pstmt);
			rs = pstmt.executeQuery();
			System.out.println("rs= " + rs);
			while (rs.next()) {
				selectCustomer = new CustomerVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				System.out.println("Ŀ���͸� DAO selectCustomer= " + selectCustomer);
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
			}
		}
		return selectCustomer;
	}

	// 3. ����� ���̺� ����
	public ArrayList<CustomerVO> getCustomerTotal() {
		ArrayList<CustomerVO> list = new ArrayList<CustomerVO>();
		String tml = "select * from customer";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO cvo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cvo = new CustomerVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				list.add(cvo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		}

		return list;
	}

	// 4. ����� ����
	public void getCVODelete(String id) throws Exception {
		String dml = "delete from customer where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, id);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setHeaderText("�����Ϸ�");
				alert.setContentText("�����Ϸ�!");

				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setHeaderText("��������");
				alert.setContentText("��������!");
			}
		} catch (SQLException se) {
			System.out.println("e = [" + se + "]");
		} catch (Exception e) {
			System.out.println("e = [" + e + "]");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}
	//�� �ٿ뵵 �˻�
	public ArrayList<CustomerVO> getCustomerSearch(String corumns,String search) {
		ArrayList<CustomerVO> list = new ArrayList<CustomerVO>();
		String dml = "select * from customer where"+ corumns +"like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO cvo = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			String search2 = "%" + search + "%";
			System.out.println(search2);
			pstmt.setString(1, search2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cvo = new CustomerVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				list.add(cvo);
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
			}
		}
		return list;
	}
}