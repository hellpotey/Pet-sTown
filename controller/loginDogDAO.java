package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DogVO;

public class loginDogDAO {
	//1. 개 추가
		public int getSelectDogregiste(DogVO dvo) {
			
			String dml = "insert into logindog " + "(id, dogname, breed, gender, age, neuter, special, localurl)" + " values "
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
			}catch ( SQLException e) {
				System.out.println("e = ["+e+"]");
			}catch (Exception e) {
				System.out.println("e = ["+e+"]");
			}finally {
				 try {
					 if(pstmt !=null)
						 pstmt.close();
					 if(con != null)
						 con.close();
				 }catch (SQLException e) {
				 }
			}
			return count;
		}
		//2. 해당 주인 개 찾아오기
		public DogVO getLoginCustomerselectDog(String id) {
			String dml = "select * from logindog where id like ?";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			DogVO retval = null;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				System.out.println("id = "+ id);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					retval = new DogVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
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
		
		// 3. 다용도 찾기 기능 2 
				public DogVO getLoginDogGet(String corumns1,String corumns2, String search1, String search2) {
					String dml = "select "+corumns1+", "+corumns2+" from logindog "
							+ "where " + corumns1 + "= ? and "+corumns2+"= ?";
					//SELECT id, password FROM customer WHERE id = '%123%' and password = '%123%';
					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					DogVO retval = null;
					try {
						con = DBUtil.getConnection();
						pstmt = con.prepareStatement(dml);
						String string1 = "%" + search1 + "%";
						String string2 = "%" + search2 + "%";
						System.out.println(string1);
						System.out.println(string2);
						pstmt.setString(1, string1);
						pstmt.setString(2, string2);
						rs = pstmt.executeQuery();
						while (rs.next()) {
							retval = new DogVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
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
					return retval;
				}
				// 4. 로그인 한 고객중 마지막으로 로그인한 고객 찾기 기능 
				public DogVO getLoginDog() {
					String dml = "SELECT * FROM logindog";

					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					DogVO retval = null;
					try {
						con = DBUtil.getConnection();
						pstmt = con.prepareStatement(dml);
						rs = pstmt.executeQuery();
						while (rs.next()) {
							retval = new DogVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
									rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9));
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
