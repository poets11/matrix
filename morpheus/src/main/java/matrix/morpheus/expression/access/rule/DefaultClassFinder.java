package matrix.morpheus.expression.access.rule;

import javassist.CtMethod;
import javassist.Modifier;
import javassist.expr.FieldAccess;
import matrix.morpheus.chain.node.Node;
import matrix.morpheus.expression.access.ClassFinder;

/**
 * Created by poets11 on 15. 7. 21..
 */
public class DefaultClassFinder implements ClassFinder {
    @Override
    public CtMethod findActualMethod(Node parentNode, FieldAccess lastAccessedField, CtMethod ctMethod) {
        if (Modifier.isAbstract(ctMethod.getModifiers())) {
            return null;
        } else {
            return ctMethod;
        }
    }
}
