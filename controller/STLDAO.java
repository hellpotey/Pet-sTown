package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.HRLVO;
import model.STLVO;

public class STLDAO {
	// 1. ����� �̿� ��ü ����Ʈ (select)
	public ArrayList<STLVO> getSTLTotal() {
		ArrayList<STLVO> list = new ArrayList<STLVO>();
		String tml = "select * from stltbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		STLVO stlVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				stlVO = new STLVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				list.add(stlVO);
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
	// 2. �ٿ뵵 ã�� ��� (select * form ? where name like %�浿%)
	public ArrayList<STLVO> getSTLSearch(String corumns, String search) {
		ArrayList<STLVO> list = new ArrayList<STLVO>();
		String dml = "select * from stltbl where " + corumns + " like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		STLVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			String search2 = "%" + search + "%";
			System.out.println(search2);
			pstmt.setString(1, search2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new STLVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				list.add(retval);
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
	// 3. �ٿ뵵 ã�� ��� 2 
	public ArrayList<STLVO> getSTLSearch2(String corumns1,String corumns2, String search1, String search2) {
		ArrayList<STLVO> list = new ArrayList<STLVO>();
		String dml = "select * from stltbl "
				+ "where " + corumns1 + "= ? or "+corumns2+"= ?";
		//SELECT id, password FROM customer WHERE id = '%123%' and password = '%123%';
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		STLVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			String string1 = "%" + search1 + "%";
			String string2 = "%" + search2 + "%";
			System.out.println(string1);
			System.out.println(string2);
			pstmt.setString(1, search1);
			pstmt.setString(2, search2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new STLVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				list.add(retval);
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
	// 4. ���࿩�� �ٲٱ�
	public int setReservation(int no, String setReservation) {
		// �� ������ ó���� ���� SQL ��
		String dml = "update stltbl set reservation=? where no=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, setReservation);
			pstmt.setInt(2, no);

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
	
	// 5. ���࿩�� �ٲٱ�
		public int setReservation2(String setReservation, String date, String time) {
			// �� ������ ó���� ���� SQL ��
			// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
			String dml = "update stltbl set reservation=? where date=? and time=?";
			Connection con = null;
			PreparedStatement pstmt = null;
			int count = 0;
			try {
				// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
				con = DBUtil.getConnection();

				// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, setReservation);
				pstmt.setString(2, date);
				pstmt.setString(3, time);

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
}
