package matrix.sample.member;

import java.util.List;

/**
 * Created by poets11 on 15. 12. 1..
 */
public interface MemberService {
    List<Member> getMemberList(MemberCondition memberCondition);

    Member getMember(MemberCondition memberCondition);
}
