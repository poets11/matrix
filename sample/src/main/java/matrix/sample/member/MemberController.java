package matrix.sample.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by poets11 on 15. 12. 1..
 */
@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/list")
    @ResponseBody
    public List<Member> list(MemberCondition memberCondition) {
        checkValidation(memberCondition);

        List<Member> members = memberService.getMemberList(memberCondition);
        return members;
    }

    @RequestMapping("/list/${id}")
    @ResponseBody
    public Member info(@PathVariable String id) {
        MemberCondition memberCondition = new MemberCondition();
        memberCondition.setId(id);

        checkValidation(memberCondition);
        return memberService.getMember(memberCondition);
    }

    private boolean checkValidation(MemberCondition memberCondition) {
        String id = memberCondition.getId();

        if (id != null && id.isEmpty() == false) {
            return true;
        }

        return false;
    }
}
