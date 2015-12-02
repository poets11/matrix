package matrix.sample.member;

/**
 * Created by poets11 on 15. 12. 1..
 */
public class MemberCondition {
    private Member member;
    private String id;
    private String name;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MemberCondition{" +
                "member=" + member +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
