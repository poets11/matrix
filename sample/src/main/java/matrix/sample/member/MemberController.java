package matrix.sample.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by poets11 on 15. 12. 1..
 */
@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;
}
