package matrix.morpheus;

import javassist.CtMethod;
import matrix.morpheus.expression.AbstractExprEditor;
import matrix.morpheus.init.RootChainCollector;
import matrix.morpheus.write.SequenceImageTraceWriter;
import matrix.morpheus.write.SequenceImageTraceWriterForService;
import matrix.morpheus.write.TraceWriter;

import java.util.List;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class MorpheusAnalyzer {
    private AnalyzerConfig analyzerConfig;

    public void analyze() {
        try {
            RootChainCollector rootChainCollector = analyzerConfig.getRootChainCollector();
            List<CtMethod> rootChains = rootChainCollector.getRootChains(analyzerConfig);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assemble() {
        try {
            RootChainCollector collector = analyzerConfig.getRootChainCollector();

            // FIXME 왜 여기서 analyzerConfig 를 다시 넣어주나?
            
            List<CtMethod> initialPoints = collector.getRootChains(analyzerConfig);
            for (CtMethod initialPoint : initialPoints) {
                AbstractExprEditor exprEditor = analyzerConfig.getExprEditor();
                exprEditor.setAssembleConfig(analyzerConfig);

                TraceWriter traceWriter = analyzerConfig.getTraceWriter();

                String groupKey = traceWriter.writeInitTraceInfo(initialPoint);
                if (groupKey != null) {
                    exprEditor.setSeq(3);
                    exprEditor.setDepth(2);
//                    exprEditor.setSeq(2);
//                    exprEditor.setDepth(1);
                    exprEditor.setKey(groupKey);

                    initialPoint.instrument(exprEditor);
                }
                
                if(traceWriter instanceof SequenceImageTraceWriter) {
                	((SequenceImageTraceWriter)traceWriter).generateAndClear();
                    System.out.println(groupKey);
                }
                
                if(traceWriter instanceof SequenceImageTraceWriterForService) {
                	((SequenceImageTraceWriterForService)traceWriter).generateAndClear();
                    System.out.println(groupKey);
                }
            }
        } catch (Exception e) {
            throw new AssembleException(e);
        }
    }

    public AnalyzerConfig getAnalyzerConfig() {
        return analyzerConfig;
    }

    public void setAnalyzerConfig(AnalyzerConfig analyzerConfig) {
        this.analyzerConfig = analyzerConfig;
    }
}
