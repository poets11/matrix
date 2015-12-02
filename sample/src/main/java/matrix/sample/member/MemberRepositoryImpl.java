package matrix.sample.member;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by poets11 on 15. 12. 1..
 */
public class MemberRepositoryImpl implements MemberRepository {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Member> selectMemberList(MemberCondition memberCondition) {
        return sqlSessionTemplate.selectList(NS + "selectMember", memberCondition);
    }

    @Override
    public Member selectMember(MemberCondition memberCondition) {
        return sqlSessionTemplate.selectOne(NS + "selectMember", memberCondition);
    }
}
