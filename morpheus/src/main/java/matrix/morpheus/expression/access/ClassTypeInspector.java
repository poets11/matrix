package matrix.morpheus.expression.access;

import javassist.CtField;
import javassist.CtMethod;

/**
 * Created by poets11 on 15. 7. 20..
 */
public interface ClassTypeInspector {
    ClassType inspect(CtField accessedField, CtMethod ctMethod) throws ClassNotFoundException;
}
