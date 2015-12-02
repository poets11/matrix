package matrix.sample.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by poets11 on 15. 12. 1..
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> getMemberList(MemberCondition memberCondition) {
        return memberRepository.selectMemberList(memberCondition);
    }

    @Override
    public Member getMember(MemberCondition memberCondition) {
        return memberRepository.selectMember(memberCondition);
    }
}
