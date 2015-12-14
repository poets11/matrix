package matrix.morpheus.chain.node;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import matrix.morpheus.chain.Chain;

/**
 * Created by poets11 on 15. 12. 2..
 */
public abstract class ChainLinker extends ExprEditor implements Runnable {
    public abstract Chain link(Chain rootChain) throws CannotCompileException;

    protected FieldAccess lastAccessedField;
    protected ClassPool pool = ClassPool.getDefault();

    @Override
    public void edit(FieldAccess accessedField) throws CannotCompileException {
        lastAccessedField = accessedField;
    }

    @Override
    public void run() {
    }

    public FieldAccess getLastAccessedField() {
        return lastAccessedField;
    }

    public ClassPool getPool() {
        return pool;
    }

    protected CtMethod getCurrentNodeMethod(Chain rootChain) {
        Node node = rootChain.getNode();
        if (node == null || node instanceof MethodNode == false) {
            return null;
        }

        try {
            MethodNode methodNode = (MethodNode) node;

            CtClass ctClass = pool.get(methodNode.getPackageName() + "." + methodNode.getClassName());
            CtMethod[] methods = ctClass.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                CtMethod method = methods[i];

                if (method.getName().equals(((MethodNode) node).getMethodName())) {
                    if (isEqualParam(method.getParameterTypes(), methodNode.getParamTypes())) {
                        return method;
                    }
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected boolean isEqualParam(CtClass[] ctClassParams, String[] stringParams) {
        boolean emptyCtClassParams = isEmpty(ctClassParams);
        boolean emptyStringParams = isEmpty(stringParams);

        if (emptyCtClassParams == true && emptyStringParams == true) {
            return true;
        } else if (emptyCtClassParams == false && emptyStringParams == false) {
            if (ctClassParams.length != stringParams.length) {
                return false;
            }

            for (int i = 0; i < ctClassParams.length; i++) {
                CtClass ctClassParam = ctClassParams[i];

                if (ctClassParam.getSimpleName().equals(stringParams[i]) == false) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    private boolean isEmpty(Object paramTypes) {
        if (paramTypes == null) {
            return true;
        }

        if (paramTypes.getClass().isArray() == true) {
            Object[] arr = (Object[]) paramTypes;

            if (arr.length == 0) {
                return true;
            }
        }

        if (paramTypes.toString().length() == 0) {
            return true;
        }

        return false;
    }
}
