package matrix.morpheus.expression.access;

/**
 * Created by poets11 on 15. 7. 20..
 */
public enum ClassType {
    CONTROLLER("C"), SERVICE("S"), REPOSITORY("D"), URL("U"), MODEL("M"), UTIL("T"), UNKNOWN("K");

    private final String type;
    
    ClassType(String type) {
        this.type = type;    
    }
    public String getValue() {
        return type;
        
    }
}
