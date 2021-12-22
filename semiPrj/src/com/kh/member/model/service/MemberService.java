package com.kh.member.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.kh.common.JDBCTemplate.*;

import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.MemberVo;

public class MemberService {

	public int join(MemberVo m) {
		
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
		
		// 가져온 pwd 랑 사용자 입력 pwd랑 같은지 비교하고 그 Member정보 리턴
		if(selectedMember.getPwd().equals(m.getPwd())) {
			return selectedMember;
		} else {
			return null;
		}
	}
	public MemberVo selectMember(Connection conn, MemberVo m) {
		return new MemberDao().selectMember(conn, m);
	}

//--------------------------------------------------------------------------------------	
	
	public List<MemberVo> search(String type, String value) {
		
		Connection conn = getConnection();
		List<MemberVo> memberList;
		
		if(type == null || value == null) {
			memberList = selectMemberAll(conn);
		} else {
			memberList = selectMemberBySearch(conn, type, value);
		}
		
		close(conn);
		
		return memberList;
	}
	public List<MemberVo> selectMemberAll(Connection conn) {
		
		return new MemberDao().selectMemberAll(conn);
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
