package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cos.models.User;
import com.cos.util.DBClose;

public class UserDao {
	//싱글톤으로 만들어야 하는데 그냥 함.
	private Connection conn;	//MySQL과 연결하기 위해 필요
	private PreparedStatement pstmt; 	//Query 문을 작성 - 실행 하기 위해 필요
	private ResultSet rs;	//결과를 보관할 커서
	
	//===============================UpdateProfile============================
			public int updateProfile(User user) {
				
				final String SQL = "UPDATE user SET userProfile = ? WHERE username = ?";
				
				conn = DBConn.getConnection();
				
				try {
					pstmt = conn.prepareStatement(SQL);
					
					pstmt.setString(1, user.getUserProfile());
					System.out.println("UpdateProfile : userProfile : " + user.getUserProfile());
					pstmt.setString(2, user.getUsername());
					System.out.println("UpdateProfile : username : " + user.getUsername());
					int result = pstmt.executeUpdate();	//변경된 튜플의 개수를 리턴
					
					return result;
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					//conn.close() 이런거 다 해줘야 되는데 일일이 넣기 귀찮으니까 util 만들어서 가져오자.
					DBClose.close(conn, pstmt);
				}
				return -1;
			}
		//===============================UpdateProfile============================
	
	
	//===============================Update============================
		public int update(User user) {
			
			final String SQL = "UPDATE user SET password = ?, address = ? WHERE id = ?";
			
			conn = DBConn.getConnection();
			
			try {
				pstmt = conn.prepareStatement(SQL);
				
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getAddress());
				pstmt.setInt(3, user.getId());
				
				int result = pstmt.executeUpdate();	//변경된 튜플의 개수를 리턴
				
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//conn.close() 이런거 다 해줘야 되는데 일일이 넣기 귀찮으니까 util 만들어서 가져오자.
				DBClose.close(conn, pstmt);
			}
			return -1;
		}
	//===============================Update============================
	
		
	//==========================FindByUsername=========================
	public User findByUsername(String username) {
		
		final String SQL = "SELECT * FROM user WHERE username = ?";
		
		conn = DBConn.getConnection();
		
		try {
			
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, username);
			
			rs  = pstmt.executeQuery();
			
			if(rs.next()) {
				
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setUserProfile(rs.getString("userProfile"));
				
				return user;
				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	//==========================FindByUsername=========================
	
	//===============================SAVE============================
	public int save(User user) {
		
		final String SQL = "INSERT INTO user(username, password, email, address, createDate, userProfile) VALUES(?, ?, ?, ?, now(), ?)";
		
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getAddress());
			pstmt.setString(5, user.getUserProfile());
			
			int result = pstmt.executeUpdate();	//변경된 튜플의 개수를 리턴
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//conn.close() 이런거 다 해줘야 되는데 일일이 넣기 귀찮으니까 util 만들어서 가져오자.
			DBClose.close(conn, pstmt);
		}
		return -1;
	}
	//===============================SAVE============================
	
	//===================FindByUsernameAndPassword=======================
	
	//로그인 인증 성공 : 1, DB오류 : -1, 미인증 : 0
	public int findByUsernameAndPassword(String username, String password) {
		
		final String SQL = "SELECT count(id) FROM user WHERE username = ? AND password = ?";
		
		conn = DBConn.getConnection();
		
		try {
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int result = rs.getInt(1);	//count(id) 컬럼명은 인식을 못할 수 있어서 컬럼 순서대로 1을 넣어서 첫번째 컬럼을 가져온다.
				
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return -1;
	}
	
	//===================FindByUsernameAndPassword=======================
}
