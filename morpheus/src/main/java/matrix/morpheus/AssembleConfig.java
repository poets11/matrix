package matrix.morpheus;


import matrix.morpheus.expression.AbstractExprEditor;
import matrix.morpheus.expression.access.inspect.ClassTypeInspectorFactory;
import matrix.morpheus.expression.access.rule.ClassFinderFactory;
import matrix.morpheus.init.InitialPointCollector;
import matrix.morpheus.write.TraceWriter;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class AssembleConfig {
    private String basePackage;
    private InitialPointCollector initialPointCollector;
    private TraceWriter traceWriter;
    private Class<AbstractExprEditor> abstractExprEditorClass;
    private ClassTypeInspectorFactory classTypeInspectorFactory;
    private ClassFinderFactory classFinderFactory;

    public ClassTypeInspectorFactory getClassTypeInspectorFactory() {
        return classTypeInspectorFactory;
    }

    public void setClassTypeInspectorFactory(ClassTypeInspectorFactory classTypeInspectorFactory) {
        this.classTypeInspectorFactory = classTypeInspectorFactory;
    }

    public ClassFinderFactory getClassFinderFactory() {
        return classFinderFactory;
    }

    public void setClassFinderFactory(ClassFinderFactory classFinderFactory) {
        this.classFinderFactory = classFinderFactory;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public InitialPointCollector getInitialPointCollector() {
        return initialPointCollector;
    }

    public void setInitialPointCollector(InitialPointCollector initialPointCollector) {
        this.initialPointCollector = initialPointCollector;
    }

    public TraceWriter getTraceWriter() {
        return traceWriter;
    }

    public void setTraceWriter(TraceWriter traceWriter) {
        this.traceWriter = traceWriter;
    }

    public Class<AbstractExprEditor> getAbstractExprEditorClass() {
        return abstractExprEditorClass;
    }

    public void setAbstractExprEditorClass(Class<? extends AbstractExprEditor> abstractExprEditorClass) {
        this.abstractExprEditorClass = (Class<AbstractExprEditor>) abstractExprEditorClass;
    }

    public AbstractExprEditor getExprEditor() throws IllegalAccessException, InstantiationException {
        return abstractExprEditorClass.newInstance();
    }
}
