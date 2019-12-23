package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.HRVVO;

public class HRVDAO {
	// 1. ����� ȣ�� ��ü ����Ʈ (select)
	public ArrayList<HRVVO> getHRVTotal() {
		ArrayList<HRVVO> list = new ArrayList<HRVVO>();
		String tml = "select * from hrvtbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HRVVO hrvVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hrvVO = new HRVVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11));
				list.add(hrvVO);
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
	public ArrayList<HRVVO> getHRVSearch(String corumns, String search) {
		ArrayList<HRVVO> list = new ArrayList<HRVVO>();
		String dml = "select * from hrvtbl where " + corumns + " like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HRVVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			String search2 = "%" + search + "%";
			System.out.println(search2);
			if(corumns=="id") {
				pstmt.setString(1, search);
			}else {
				pstmt.setString(1, search2);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new HRVVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11));
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

	// 3. room����Ʈ �޺��ڽ��� �����ϱ� ()
	public HashSet<String> getStringHashSet(String coloumns) {
		HashSet<String> list = new HashSet<String>();
		String tml = "select " + coloumns + " from hrvtbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String room;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				room = rs.getString(1);
				list.add(room);
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

	// 4. ����� HRV�� �߰�
	public int getHRVregiste(HRVVO svo) {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into hrvtbl "
				+ "(checkin, checkout, room, id, dogname, breed, gender, age, neuter, special)" + " values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getCheckin());
			pstmt.setString(2, svo.getCheckout());
			pstmt.setString(3, svo.getRoom());
			pstmt.setString(4, svo.getId());
			pstmt.setString(5, svo.getDogname());
			pstmt.setString(6, svo.getBreed());
			pstmt.setString(7, svo.getGender());
			pstmt.setString(8, svo.getAge());
			pstmt.setString(9, svo.getNeuter());
			pstmt.setString(10, svo.getSpecial());

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



	// 5. ���� ���� ���
	public void getHRVDelete(int no) {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from hrvtbl where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setHeaderText("���� �Ϸ�.");
				alert.setContentText("���� ����!!!");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setHeaderText("���� ����.");
				alert.setContentText("���� ����!!!");

				alert.showAndWait();
			}

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

	}
	// 6. ������� 2
				public int getHRVDelete(String checkin, String checkout, String room) {
					// �� ������ ó���� ���� SQL ��
					// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
					String dml = "delete from hrvtbl where checkin = ? and checkout = ? and room = ?";
					Connection con = null;
					PreparedStatement pstmt = null;
					int count = 0;
					try {
						// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
						con = DBUtil.getConnection();

						// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
						pstmt = con.prepareStatement(dml);
						pstmt.setString(1, checkin);
						pstmt.setString(2, checkout);
						pstmt.setString(3, room);

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
				// 7. �������� ����� ȣ�� (select)
				public HRVVO getHRVLast() {
					String tml = "select * from hrvtbl";

					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					HRVVO hrvVO = null;

					try {
						con = DBUtil.getConnection();
						pstmt = con.prepareStatement(tml);
						rs = pstmt.executeQuery();
						while (rs.next()) {
							hrvVO = new HRVVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
									rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
									rs.getString(11));
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
					return hrvVO;
				}
}
