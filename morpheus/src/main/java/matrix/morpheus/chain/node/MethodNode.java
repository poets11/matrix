package matrix.morpheus.chain.node;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * Created by poets11 on 15. 12. 2..
 */
public class MethodNode implements Node {
    private String id;
    private String packageName;
    private String className;
    private String methodName;
    private String[] paramTypes;

    public MethodNode(CtMethod ctMethod) {
        CtClass ctClass = ctMethod.getDeclaringClass();
        id = ctClass.getName() + "." + ctMethod.getName();
        packageName = ctClass.getPackageName();
        className = ctClass.getSimpleName();
        methodName = ctMethod.getName();

        try {
            CtClass[] parameterTypes = ctMethod.getParameterTypes();
            if (parameterTypes == null || parameterTypes.length == 0) {
                id += "();";
                return;
            }

            StringBuilder paramTypeString = new StringBuilder();
            paramTypes = new String[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                String parameterTypeSimpleName = parameterTypes[i].getSimpleName();

                if (i > 0) {
                    paramTypeString.append(", ");
                }

                paramTypes[i] = parameterTypeSimpleName;
                paramTypeString.append(parameterTypeSimpleName);
            }

            id += "(" + paramTypeString.toString() + ");";
        } catch (Exception e) {
            paramTypes = null;
            id += "(?);";
            e.printStackTrace();
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(String[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ClassNode { " + id + " } ";
    }
}
