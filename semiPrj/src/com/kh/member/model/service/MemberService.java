package com.kh.member.model.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.kh.common.JDBCTemplate.*;

import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.MemberVo;

public class MemberService {

	// 패스워드 받아서 암호화시켜서 리턴하는 메소드
	private String encrypt(String pwd) {
		
		// 패스워드 암호화 
		MessageDigest md;
		StringBuilder sb = new StringBuilder();
		try {
			md = MessageDigest.getInstance("SHA-512"); // SHA-512 암호화 알고리즘 사용
			md.update(pwd.getBytes());
			byte[] digest = md.digest();
			
			for(byte b : digest) { // ex) 23, -15, 11, 8, 12..
				sb.append(String.format("%02x", b));  // 위 숫자 바이트 배열을 문자열 포맷화
			}
			
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} 
		return sb.toString();
	}
	
	public int join(MemberVo m) {
		
		// 암호화한 패스워드로 DB에 저장하기 위해 암호화된 패스워드로 변경
		m.setPwd(encrypt(m.getPwd()));
		
		
		// DB Connection 가져오기
		Connection conn = getConnection();
		
		// 쿼리 날리기
		int result = 0;
		try {
			result = insertMember(conn, m);
			
			if(result>0)
				commit(conn);
			else
				rollback(conn);
		} catch (SQLException e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			close(conn);
		}
		
		// 결과 반환해주기
		
		return result;
	}
	
	public int insertMember(Connection conn, MemberVo m) throws SQLException {
		
		// DAO 불러서 쿼리 실행
		// DAO 한테 쿼리 실행 결과 받기
		return new MemberDao().insertMember(conn, m);
		
	}

	public MemberVo login(MemberVo m) {
		// 커넥션 가져오기
		Connection conn = getConnection();
		
		// id 가지고 그 아이디의 비번 조회
		MemberVo selectedMember = selectMember(conn, m);
		
		close(conn);
		
		// 가져온 pwd 랑 사용자 입력 pwd랑 같은지 비교하고 그 Member정보 리턴 (암호화해줌)
		if(selectedMember.getPwd().equals(encrypt(m.getPwd()))) {
			return selectedMember;
		} else {
			return null;
		}
	}
	public MemberVo selectMember(Connection conn, MemberVo m) {
		return new MemberDao().selectMember(conn, m);
	}

//--------------------------------------------------------------------------------------	
	
	public List<MemberVo> search(String type, String value, String currentPage) {
		
		Connection conn = getConnection();
		
		
		int totalBoardCount = countMemberAll(conn); // 총 회원 수
		int pageLimit = 5; // 페이징 목록 최대 개수
		int boardLimit = 5; // 한 페이지당 게시글 수 // 드랍 박스 이용해서 사용자가 개수 정할 수 있도록 구현
		int maxPage = 0; // 마지막 페이지
		
		maxPage = totalBoardCount / boardLimit;
		if(totalBoardCount % boardLimit != 0) {
			maxPage++;
		}
		System.out.println("maxPage : " + maxPage);
		
		// (currentPage * boardLimit) - boardLimit + 1 ~ currentPage * boardLimit
		int p = Integer.parseInt(currentPage);
		int endNo = p * boardLimit;
		int startNo = endNo - boardLimit + 1;
		
		
		
		List<MemberVo> memberList;
		
		if(type == null || value == null) {
			memberList = selectMemberAll(conn, currentPage, startNo, endNo);
		} else {
			memberList = selectMemberBySearch(conn, type, value);
		}
		
		close(conn);
		
		return memberList;
	}
	private int countMemberAll(Connection conn) {
		
		return new MemberDao().countMemberAll(conn);
	}

	public List<MemberVo> selectMemberAll(Connection conn, String currentPage, int startNo, int endNo) {
		
		return new MemberDao().selectMemberAll(conn, startNo, endNo);
	}

	private List<MemberVo> selectMemberBySearch(Connection conn, String type, String value) {
		
		return new MemberDao().selectMemberBySearch(conn, type, value);
	}
//---------------------------------------------------------------------------------------
	public int dupCheck(String id) {
		
		// 여러 쿼리들을 사용해서 DB에 접근하게 될 때 트랜잭션 관리를 위해 커넥션 연결을 Service에서 진행한다.
		// 여러 쿼리들을 하나의 트랜잭션으로 중간에 오류가생기면 롤백, 잘 성공하면 커밋을 한꺼번에 해주기 위해서이다.
		Connection conn = getConnection();
		
		int result = 0;
		try {
			result = selectMemberById(conn, id);
			commit(conn);
		} catch (SQLException e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			close(conn);			
		}
		
		return result;
	}

	private int selectMemberById(Connection conn, String id) throws SQLException {
	
		return new MemberDao().selectMemberById(conn, id);
	}


}
