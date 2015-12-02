package matrix.sample.member;

import java.util.List;

/**
 * Created by poets11 on 15. 12. 1..
 */
public interface MemberRepository {
    final String NS = MemberRepository.class.getCanonicalName() + ".";

    List<Member> selectMemberList(MemberCondition memberCondition);

    Member selectMember(MemberCondition memberCondition);
}
