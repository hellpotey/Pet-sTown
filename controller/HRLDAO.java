package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.HRLVO;
import model.HRLVO;

public class HRLDAO {
		// 1. ������ �� �ִ� ȣ�� ��ü ����Ʈ (select)
		public ArrayList<HRLVO> getHRLTotal() {
			ArrayList<HRLVO> list = new ArrayList<HRLVO>();
			String tml = "select * from hrltbl";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			HRLVO hrlVO = null;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(tml);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					hrlVO = new HRLVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
					list.add(hrlVO);
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
		// 2. �ٿ뵵 ã�� ��� (select * form ? where name like )
		public ArrayList<HRLVO> getHRLSearch(String corumns, String search) {
			ArrayList<HRLVO> list = new ArrayList<HRLVO>();
			String dml = "select * from hrltbl where " + corumns + " like ?";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			HRLVO retval = null;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				String search2 = "%" + search + "%";
				if(corumns=="id") {
					pstmt.setString(1, search);
				}else {
					pstmt.setString(1, search2);
				}
				rs = pstmt.executeQuery();
				while (rs.next()) {
					retval = new HRLVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
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
		public ArrayList<HRLVO> getHRLSearch2(String corumns1,String corumns2, String search1, String search2) {
			ArrayList<HRLVO> list = new ArrayList<HRLVO>();
			String dml = "select * from hrltbl "
					+ "where " + corumns1 + "= ? or "+corumns2+"= ?";
			//SELECT id, password FROM customer WHERE id = '%123%' and password = '%123%';
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			HRLVO retval = null;
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
					retval = new HRLVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
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
			String dml = "update hrltbl set reservation=? where no=?";
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
			public int setReservation2(String setReservation, String checkin, String checkout, String room) {
				// �� ������ ó���� ���� SQL ��
				// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
				String dml = "update hrltbl set reservation=? where checkin=? and checkout=? and room=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				int count = 0;
				try {
					// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
					con = DBUtil.getConnection();

					// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
					pstmt = con.prepareStatement(dml);
					pstmt.setString(1, setReservation);
					pstmt.setString(2, checkin);
					pstmt.setString(3, checkout);
					pstmt.setString(4, room);

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
