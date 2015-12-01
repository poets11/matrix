package matrix.morpheus.write;

import javassist.CtClass;
import javassist.CtMethod;
import matrix.morpheus.expression.access.ClassType;

/**
 * Created by poets11 on 15. 7. 22..
 */
public class TraceInfo {
    private int seq;
    private int depth;
    private String key;
    private String packageName;
    private String className;
    private String methodName;
    private ClassType classType;

    public TraceInfo() {
    }

    public TraceInfo(int seq, int depth, ClassType classType, CtMethod ctMethod) {
        setSeq(seq);
        setDepth(depth);
        setClassType(classType);

        CtClass declaringClass = ctMethod.getDeclaringClass();
        setPackageName(declaringClass.getPackageName());
        setClassName(declaringClass.getSimpleName());
        setMethodName(ctMethod.getName());
        setKey(packageName + "." + className + "." + methodName);
    }

    public TraceInfo(int seq, int depth, String key, ClassType classType, CtMethod ctMethod) {
        setSeq(seq);
        setDepth(depth);
        setKey(key);
        setClassType(classType);

        CtClass declaringClass = ctMethod.getDeclaringClass();
        setPackageName(declaringClass.getPackageName());
        setClassName(declaringClass.getSimpleName());
        setMethodName(ctMethod.getName());
    }

    public TraceInfo(int seq, int depth, String key, String packageName, String className, String methodName, ClassType classType) {
        this.seq = seq;
        this.depth = depth;
        this.key = key;
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.classType = classType;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassNameNewLine() {
    	if(className.endsWith("Controller")) {
    		return className.substring(0, className.indexOf("Controller")) + "\\n" + "Controller";
    	} else if(className.endsWith("ServiceImpl")) {
    		return className.substring(0, className.indexOf("ServiceImpl")) + "\\n" + "ServiceImpl";
    	} else if(className.endsWith("RepositoryImpl")) {
    		return className.substring(0, className.indexOf("RepositoryImpl")) + "\\n" + "RepositoryImpl";
    	}
    	
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

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

	public String getClassName() {
		return className;
	}
}
