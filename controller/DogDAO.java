package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;
import model.DogVO;
import model.HRVVO;

public class DogDAO {
	// 1. 개 추가
	public int getDogregiste(DogVO dvo) {

		String dml = "insert into dog " + "(id, dogname, breed, gender, age, neuter, special, localurl)" + " values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, dvo.getId());
			pstmt.setString(2, dvo.getDogname());
			pstmt.setString(3, dvo.getBreed());
			pstmt.setString(4, dvo.getGender());
			pstmt.setString(5, dvo.getAge());
			pstmt.setString(6, dvo.getNeuter());
			pstmt.setString(7, dvo.getSpecial());
			pstmt.setString(8, dvo.getLocalurl());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("e = [" + e + "]");
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
		return count;
	}

	// 2. 해당 주인 개 찾아오기
	public ArrayList<DogVO> getLoginCustomerDogList(String id) {
		ArrayList<DogVO> list = new ArrayList<DogVO>();
		String dml = "select * from dog where id like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DogVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			System.out.println("id = " + id);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new DogVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
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
				System.out.println(se);
			}
		}
		return list;
	}

	// 3. 개삭제
	public void getDVODelete(String id, String dogname) {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "delete from dog where id=? and dogname = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, id);
			pstmt.setString(2, dogname);

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
	// 강아지 리스트 생성
	public ArrayList<DogVO> getDogTotal() {
		ArrayList<DogVO> list = new ArrayList<DogVO>();
		String tml = "select * from dog";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DogVO dogVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dogVO = new DogVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
				list.add(dogVO);
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
	// 강아지 검색용
	public ArrayList<DogVO> getDogSearch(String corumns, String search) throws Exception {
		ArrayList<DogVO> list = new ArrayList<DogVO>();
		String dml = "select * from dog where " + corumns + " like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DogVO dvo = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			String search2 = "%" + search + "%";
			System.out.println(search2);
			pstmt.setString(1, search2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dvo = new DogVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
				list.add(dvo);
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
	//강아지 체크
	public DogVO getDogCheck(String id, String password) throws Exception {

		String dml = "SELECT * FROM dog WHERE id = ? and password = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DogVO selectDog = null;
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
				selectDog = new DogVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
				System.out.println("커스터머 DAO selectCustomer= " + selectDog);
			}
		} catch (SQLException se) {
			System.out.println("tt" + se);
		} catch (Exception e) {
			System.out.println("kk" + e);
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
		return selectDog;
	}

}
