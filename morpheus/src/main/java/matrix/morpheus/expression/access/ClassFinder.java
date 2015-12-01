package matrix.morpheus.expression.access;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * Created by poets11 on 15. 7. 20..
 */
public interface ClassFinder {
    CtClass findActualClass(CtField accessedField, CtMethod ctMethod) throws NotFoundException;
}
