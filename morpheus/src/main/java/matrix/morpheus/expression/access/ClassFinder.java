package matrix.morpheus.expression.access;

import javassist.CtMethod;
import javassist.expr.FieldAccess;
import matrix.morpheus.chain.node.Node;

/**
 * Created by poets11 on 15. 7. 20..
 */
public interface ClassFinder {
    CtMethod findActualMethod(Node parentNode, FieldAccess lastAccessedField, CtMethod ctMethod);
}
