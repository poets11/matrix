package matrix.morpheus;


import matrix.morpheus.chain.ChainLinkerFactory;
import matrix.morpheus.chain.DefaultChainLinkerFactory;
import matrix.morpheus.chain.node.NodeConverterFactory;
import matrix.morpheus.expression.AbstractExprEditor;
import matrix.morpheus.expression.access.inspect.ClassTypeInspectorFactory;
import matrix.morpheus.expression.access.rule.ClassFinderFactory;
import matrix.morpheus.init.RootChainCollector;
import matrix.morpheus.write.TraceWriter;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class AnalyzerConfig {
    private String basePackage;
    private RootChainCollector rootChainCollector;

    private String webRootPath;
    private String tomcatHome;

    private TraceWriter traceWriter;
    private Class<AbstractExprEditor> abstractExprEditorClass;
    private ClassTypeInspectorFactory classTypeInspectorFactory;
    private ClassFinderFactory classFinderFactory;
    private ChainLinkerFactory chainLinkerFactory = new DefaultChainLinkerFactory();
    private NodeConverterFactory nodeConverterFactory;

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

    public RootChainCollector getRootChainCollector() {
        return rootChainCollector;
    }

    public void setRootChainCollector(RootChainCollector rootChainCollector) {
        this.rootChainCollector = rootChainCollector;
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

    public ChainLinkerFactory getChainLinkerFactory() {
        return chainLinkerFactory;
    }

    public NodeConverterFactory getNodeConverterFactory() {
        return nodeConverterFactory;
    }

    public void setNodeConverterFactory(NodeConverterFactory nodeConverterFactory) {
        this.nodeConverterFactory = nodeConverterFactory;
    }

    public String getWebRootPath() {
        return webRootPath;
    }

    public void setWebRootPath(String webRootPath) {
        this.webRootPath = webRootPath;
    }

    public String getTomcatHome() {
        return tomcatHome;
    }

    public void setTomcatHome(String tomcatHome) {
        this.tomcatHome = tomcatHome;
    }
}
