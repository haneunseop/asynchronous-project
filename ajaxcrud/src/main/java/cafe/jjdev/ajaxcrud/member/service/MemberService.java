package cafe.jjdev.ajaxcrud.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cafe.jjdev.ajaxcrud.member.mapper.MemberMapper;
import cafe.jjdev.ajaxcrud.member.vo.Member;


@Service
public class MemberService {
@Autowired private MemberMapper memberMapper;

	// 회원 조회
	// 현재 페이지 번호를 받아서 몇번째 행의 회원을 보여줄 것인지 정할 것이다.(LIMIT x, y에서 x부분을 구할 때 사용)
	public Map<String, Object> getMemberList(int currentPage) {
		
		// 한 페이지에 몇명의 회원 정보를 보여 줄 것인지 정하는 rowPerPage변수(LIMIT x, y에서 y부분)
		int rowPerPage = 10;
		
		// 페이지마다 다른 회원의 정보를 보여 줄 것인지 정하는 startRow변수(LIMIT x, y에서 x부분)
		int startRow = (currentPage-1)*rowPerPage;
		
		// 한 페이지에서 페이징 번호를 몇개나 보여 줄 것인지 정하는 pagingNumber변수
		int pagingNumber = 8;
		
		// 마지막 페이지를 구하기 위해 일단 전체 회원의 수를 구해야한다.
		int totalMember = memberMapper.countMember();
		
		// 회원 수에 따라 마지막 페이지가 달라야 한다.
		int lastPage = 0;
		if(totalMember % rowPerPage == 0) {
			lastPage = totalMember/rowPerPage;
		}else {
			lastPage = totalMember/rowPerPage+1;
		}
		
		// 페이징 번호를 정하는 변수
		// pagingNumber와 같은 번호까지를 -1을 한 후 pagingNumber로 나누면 같은 숫자가 나온다.
		// 1~10페이지를 0~9로 바꾸고 10로 나누면 0~9 전부 몫은 0인 원리다. 
		int pageNo = (currentPage-1)/pagingNumber;
		
		// 회원 목록을 불러오는 메소드의 매개변수를 설정
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowPerPage", rowPerPage);
		map.put("startRow", startRow);
		
		// 회원 목록을 불러오기
		List<Member> memberList = memberMapper.selectMemberList(map);
		System.out.println("[cafe.jjdev.ajaxcrud.member.service.MemberService.getMemberList] memberList.size()"+memberList.size());
		// 리턴하기
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pagingNumber", pagingNumber);
		resultMap.put("pageNo", pageNo);
		resultMap.put("lastPage", lastPage);
		resultMap.put("memberList", memberList);
		
		return resultMap;
	}
	
	// 회원 등록
	public void addMember(Member member) {
	    memberMapper.insertMember(member);
	}
	
	// 회원 수정
	public void modifyMember(Member member) {
	    memberMapper.updateMember(member);
	}
	
	// 회원 탈퇴
    public void removeMember(String[] idsToDelete) {
       for(String id : idsToDelete) {
           Member member = new Member();
           member.setId(id);
           memberMapper.deleteMember(member);
       }
    }
}
