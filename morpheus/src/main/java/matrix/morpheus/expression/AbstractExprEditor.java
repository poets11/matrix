package matrix.morpheus.expression;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import matrix.morpheus.AssembleConfig;
import matrix.morpheus.AssembleException;
import matrix.morpheus.expression.access.ClassType;
import matrix.morpheus.write.TraceInfo;
import matrix.morpheus.write.TraceWriter;

/**
 * Created by poets11 on 15. 7. 16..
 */
public abstract class AbstractExprEditor extends ExprEditor {
    protected AssembleConfig assembleConfig;

    protected CtField accessedField;
    
    protected String key;
    protected int seq;
    protected int depth;

    public AbstractExprEditor() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public AssembleConfig getAssembleConfig() {
        return assembleConfig;
    }

    public void setAssembleConfig(AssembleConfig assembleConfig) {
        this.assembleConfig = assembleConfig;
    }

    @Override
    public void edit(FieldAccess f) throws CannotCompileException {
        try {
            accessedField = f.getField();
        } catch (NotFoundException e) {
            throw new AssembleException(e);
        }
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        try {
            CtMethod ctMethod = m.getMethod();

            CtClass actualClass = assembleConfig.getClassFinderFactory().findActualClass(accessedField, ctMethod);
            if (actualClass != null) {
                CtMethod actualMethod = actualClass.getDeclaredMethod(ctMethod.getName(), ctMethod.getParameterTypes());
                ClassType classType = assembleConfig.getClassTypeInspectorFactory().inspect(null, actualMethod);

                String className = actualClass.getName();
                if (className.startsWith(assembleConfig.getBasePackage())) {
                    TraceInfo traceInfo = new TraceInfo(seq, depth, key, classType, actualMethod);

                    TraceWriter traceWriter = assembleConfig.getTraceWriter();
                    boolean writeResult = traceWriter.writeTraceInfo(traceInfo);
                    if (writeResult) {
                        seq++;
                        depth++;

                        actualMethod.instrument(this);

                        depth--;
                    } else {
                        actualMethod.instrument(this);
                    } 
                }
            }
        } catch (NotFoundException e) {
            throw new AssembleException(e);
        }
    }
}
