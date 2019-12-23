package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.HRLVO;
import model.HRLVO;

public class HRLDAO {
		// 1. 예약할 수 있는 호텔 전체 리스트 (select)
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
		// 2. 다용도 찾기 기능 (select * form ? where name like )
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
		// 3. 다용도 찾기 기능 2 
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
		// 4. 예약여부 바꾸기
		public int setReservation(int no, String setReservation) {
			// ② 데이터 처리를 위한 SQL 문
			String dml = "update hrltbl set reservation=? where no=?";
			Connection con = null;
			PreparedStatement pstmt = null;
			int count = 0;
			try {
				// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
				con = DBUtil.getConnection();

				// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, setReservation);
				pstmt.setInt(2, no);

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
		
		// 5. 예약여부 바꾸기
			public int setReservation2(String setReservation, String checkin, String checkout, String room) {
				// ② 데이터 처리를 위한 SQL 문
				// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
				String dml = "update hrltbl set reservation=? where checkin=? and checkout=? and room=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				int count = 0;
				try {
					// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
					con = DBUtil.getConnection();

					// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
					pstmt = con.prepareStatement(dml);
					pstmt.setString(1, setReservation);
					pstmt.setString(2, checkin);
					pstmt.setString(3, checkout);
					pstmt.setString(4, room);

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
}
