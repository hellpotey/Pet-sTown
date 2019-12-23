package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.SRVVO;

public class SRVDAO {
	
	//1. ����� �̿� ��ü ����Ʈ
	public ArrayList<SRVVO> getSTVTotal() {
		ArrayList<SRVVO> list = new ArrayList<SRVVO>();
		String tml = "select * from srvtbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SRVVO stvVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				stvVO = new SRVVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11));
				list.add(stvVO);
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

		//2. �ٿ뵵 �˻�
	public ArrayList<SRVVO> getSRVSearch(String corumns, String search){
		ArrayList<SRVVO> list = new ArrayList<SRVVO>();
		String dml = "select * from srvtbl where " + corumns + " like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SRVVO retval = null;
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
				retval = new SRVVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
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

		//3. �ٿ뵵 �˻�2 (select * from ? like %?%)
	public ArrayList<SRVVO> getSTVSearch(String corumns, String search) {
		ArrayList<SRVVO> list = new ArrayList<SRVVO>();
		String dml = "select * from srvtbl where " + corumns + " like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SRVVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			String search2 = "%" + search + "%";
			System.out.println(search2);
			pstmt.setString(1, search2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new SRVVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
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

	//4. �������
	public void getSTRDelete(int no) {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from srvtbl where no = ?";
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
	
	// 5. ����� SRV�� �߰�
	public int getSRVregiste(SRVVO svo) {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into srvtbl "
				+ "(date, time, cutstyle, id, dogname, breed, gender, age, neuter, special)" + " values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getDate());
			pstmt.setString(2, svo.getTime());
			pstmt.setString(3, svo.getCutstyle());
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



	// 6. ���� ���� ���
	public void getSRVDelete(int no) {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from srvtbl where no = ?";
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
	// 7. ������� 2
				public int getSRVDelete(String date, String time) {
					// �� ������ ó���� ���� SQL ��
					// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
					String dml = "delete from srvtbl where date = ? and time = ?";
					Connection con = null;
					PreparedStatement pstmt = null;
					int count = 0;
					try {
						// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
						con = DBUtil.getConnection();

						// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
						pstmt = con.prepareStatement(dml);
						pstmt.setString(1, date);
						pstmt.setString(2, time);

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
				// 8. time����Ʈ �޺��ڽ��� �����ϱ� ()
				public HashSet<String> getStringHashSet(String coloumns) {
					HashSet<String> list = new HashSet<String>();
					String tml = "select " + coloumns + " from srvtbl";

					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String time;

					try {
						con = DBUtil.getConnection();
						pstmt = con.prepareStatement(tml);
						rs = pstmt.executeQuery();
						while (rs.next()) {
							time = rs.getString(1);
							list.add(time);
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