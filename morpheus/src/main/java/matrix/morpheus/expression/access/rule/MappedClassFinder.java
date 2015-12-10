package matrix.morpheus.expression.access.rule;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.FieldAccess;
import matrix.morpheus.chain.node.Node;
import matrix.morpheus.expression.access.ClassFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by poets11 on 15. 12. 4..
 */
public class MappedClassFinder implements ClassFinder {
    private Map<String, String> classMap = new HashMap<String, String>();

    public void put(String source, String target) {
        classMap.put(source, target);
    }

    @Override
    public CtMethod findActualMethod(Node parentNode, FieldAccess lastAccessedField, CtMethod ctMethod) {
        try {
            String mappedClassName = classMap.get(ctMethod.getDeclaringClass().getName());
            if (mappedClassName != null) {
                CtClass ctClass = ClassPool.getDefault().get(mappedClassName);
                return ctClass.getDeclaredMethod(ctMethod.getName(), ctMethod.getParameterTypes());
            }

            mappedClassName = classMap.get(ctMethod.getDeclaringClass().getName() + "." + ctMethod.getName());
            if (mappedClassName != null) {
                CtClass ctClass = ClassPool.getDefault().get(mappedClassName);
                return ctClass.getDeclaredMethod(ctMethod.getName(), ctMethod.getParameterTypes());
            }
        } catch (Exception e) {

        }

        return null;
    }
}
