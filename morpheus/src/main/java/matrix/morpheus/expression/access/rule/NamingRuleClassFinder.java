package matrix.morpheus.expression.access.rule;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.FieldAccess;
import matrix.morpheus.chain.node.Node;
import matrix.morpheus.expression.access.ClassFinder;

/**
 * Created by poets11 on 15. 7. 22..
 */
public class NamingRuleClassFinder implements ClassFinder {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public CtMethod findActualMethod(Node parentNode, FieldAccess lastAccessedField, CtMethod ctMethod) {
        ClassPool pool = ClassPool.getDefault();

        CtClass declaringClass = ctMethod.getDeclaringClass();
        String packageName = declaringClass.getPackageName();
        String className = declaringClass.getSimpleName();

        if (prefix != null) {
            try {
                CtClass ctClass = pool.get(packageName + "." + prefix + className);
                if (ctClass != null) {
                    return ctClass.getDeclaredMethod(ctMethod.getName(), ctMethod.getParameterTypes());
                }
            } catch (Exception e) {
            }
        }

        if (suffix != null) {
            try {
                CtClass ctClass = pool.get(packageName + "." + className + suffix);
                if (ctClass != null) {
                    return ctClass.getDeclaredMethod(ctMethod.getName(), ctMethod.getParameterTypes());
                }
            } catch (Exception e) {
            }
        }
        
        return null;
    }
}
