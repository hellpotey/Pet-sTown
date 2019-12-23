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
	
	//1. 예약된 미용 전체 리스트
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

		//2. 다용도 검색
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

		//3. 다용도 검색2 (select * from ? like %?%)
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

	//4. 삭제기능
	public void getSTRDelete(int no) {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "delete from srvtbl where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("삭제");
				alert.setHeaderText("삭제 완료.");
				alert.setContentText("삭제 성공!!!");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("삭제");
				alert.setHeaderText("삭제 실패.");
				alert.setContentText("삭제 실패!!!");

				alert.showAndWait();
			}

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
	}
	
	// 5. 예약시 SRV에 추가
	public int getSRVregiste(SRVVO svo) {
		// ② 데이터 처리를 위한 SQL 문
		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into srvtbl "
				+ "(date, time, cutstyle, id, dogname, breed, gender, age, neuter, special)" + " values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
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



	// 6. 예약 삭제 기능
	public void getSRVDelete(int no) {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "delete from srvtbl where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("삭제");
				alert.setHeaderText("삭제 완료.");
				alert.setContentText("삭제 성공!!!");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("삭제");
				alert.setHeaderText("삭제 실패.");
				alert.setContentText("삭제 실패!!!");

				alert.showAndWait();
			}

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

	}
	// 7. 예약삭제 2
				public int getSRVDelete(String date, String time) {
					// ② 데이터 처리를 위한 SQL 문
					// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
					String dml = "delete from srvtbl where date = ? and time = ?";
					Connection con = null;
					PreparedStatement pstmt = null;
					int count = 0;
					try {
						// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
						con = DBUtil.getConnection();

						// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
						pstmt = con.prepareStatement(dml);
						pstmt.setString(1, date);
						pstmt.setString(2, time);

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
				// 8. time리스트 콤보박스에 설정하기 ()
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