package matrix.morpheus.expression.access.rule;

import javassist.*;
import matrix.morpheus.expression.access.ClassFinder;

/**
 * Created by poets11 on 15. 7. 21..
 */
public class DefaultClassFinder implements ClassFinder {
    @Override
    public CtClass findActualClass(CtField accessedField, CtMethod ctMethod) throws NotFoundException {
        if (Modifier.isAbstract(ctMethod.getModifiers())) {
            return null;
        } else {
            return ctMethod.getDeclaringClass();
        }
    }
}
