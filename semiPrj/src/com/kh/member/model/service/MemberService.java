package com.kh.member.model.service;

import java.sql.Connection;
import java.sql.SQLException;

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

}
