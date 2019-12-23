package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.HRLVO;
import model.STLVO;

public class STLDAO {
	// 1. 예약된 미용 전체 리스트 (select)
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
	// 2. 다용도 찾기 기능 (select * form ? where name like %길동%)
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
	// 3. 다용도 찾기 기능 2 
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
	// 4. 예약여부 바꾸기
	public int setReservation(int no, String setReservation) {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update stltbl set reservation=? where no=?";
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
		public int setReservation2(String setReservation, String date, String time) {
			// ② 데이터 처리를 위한 SQL 문
			// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
			String dml = "update stltbl set reservation=? where date=? and time=?";
			Connection con = null;
			PreparedStatement pstmt = null;
			int count = 0;
			try {
				// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
				con = DBUtil.getConnection();

				// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, setReservation);
				pstmt.setString(2, date);
				pstmt.setString(3, time);

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
