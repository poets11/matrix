package matrix.morpheus;

import javassist.CtMethod;
import matrix.morpheus.expression.AbstractExprEditor;
import matrix.morpheus.init.InitialPointCollector;
import matrix.morpheus.write.SequenceImageTraceWriter;
import matrix.morpheus.write.SequenceImageTraceWriterForService;
import matrix.morpheus.write.TraceWriter;

import java.util.List;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class TraceAssembler {
    private AssembleConfig assembleConfig;

    public void assemble() {
        try {
            InitialPointCollector collector = assembleConfig.getInitialPointCollector();

            // FIXME 왜 여기서 assembleConfig 를 다시 넣어주나?
            
            List<CtMethod> initialPoints = collector.getInitialPoints(assembleConfig);
            for (CtMethod initialPoint : initialPoints) {
                AbstractExprEditor exprEditor = assembleConfig.getExprEditor();
                exprEditor.setAssembleConfig(assembleConfig);

                TraceWriter traceWriter = assembleConfig.getTraceWriter();

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

    public AssembleConfig getAssembleConfig() {
        return assembleConfig;
    }

    public void setAssembleConfig(AssembleConfig assembleConfig) {
        this.assembleConfig = assembleConfig;
    }
}
