package cafe.jjdev.ajaxcrud.member.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafe.jjdev.ajaxcrud.member.service.MemberService;
import cafe.jjdev.ajaxcrud.member.vo.Member;


@RestController
public class RestMemberController {
    @Autowired private MemberService memberService;
    
    @GetMapping("/getMemberList")
    // 현재 페이지 번호를 받아서 페이지 번호에 맞는 목록을 리턴한다.
    public Map<String, Object> getMemberList(@RequestParam(value = "currentPage") int currentPage) {
    	System.out.println("[cafe.jjdev.ajaxcrud.member.restcontroller.RestMemberController.getMemberList] currentPage: "+currentPage);
    	Map<String, Object> map = memberService.getMemberList(currentPage);
    	return map;
    }
    
    @PostMapping("/addMember")
    public void addMember(Member member) {
    	System.out.println("[cafe.jjdev.ajaxcrud.member.restcontroller.RestMemberController.addMember] member: "+member);
    	memberService.addMember(member);
    }
    
    @PostMapping("/removeMember")
    // 삭제 할 아이디들의 배열을 매개변수로 받는다.
    public void removeMember(@RequestParam(value="ck[]") String[] idsToDelete) {
        System.out.println("[cafe.jjdev.ajaxcrud.member.restcontroller.RestMemberController.removeMember] idsToDelete: "+idsToDelete);
        memberService.removeMember(idsToDelete);
    }
    
    @PostMapping("/modifyMember")
    public void modifyMember(Member member) {
    	System.out.println("[cafe.jjdev.ajaxcrud.member.restcontroller.RestMemberController.modifyMember] member: "+member);
    	memberService.modifyMember(member);
    }
}
