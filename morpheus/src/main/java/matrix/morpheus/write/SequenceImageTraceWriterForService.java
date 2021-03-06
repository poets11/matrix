package matrix.morpheus.write;

import javassist.CtMethod;
import matrix.morpheus.expression.access.ClassType;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class SequenceImageTraceWriterForService implements TraceWriter {
    private static String TRACE_FORMAT = "%s|%s|%s|%s|%s|%s|%s";

    private List<ClassType> traceTargetClassTypes;
    private Map<Integer, String> depthClassNameMap;
    private StringBuilder sb = new StringBuilder();
    private String fileName;

    public SequenceImageTraceWriterForService() {
        traceTargetClassTypes = new ArrayList<ClassType>();
        traceTargetClassTypes.add(ClassType.URL);
        traceTargetClassTypes.add(ClassType.CONTROLLER);
        traceTargetClassTypes.add(ClassType.SERVICE);
        traceTargetClassTypes.add(ClassType.REPOSITORY);
        traceTargetClassTypes.add(ClassType.UTIL);
        
        depthClassNameMap = new HashMap<Integer, String>();
    }
    
    public void generateAndClear() {
//    	System.out.println(sb.toString());
    	new SequenceImageGenerator().getSequenceDiagram(sb.toString(), fileName, "qsd");
    	sb = new StringBuilder();
    	fileName = "unknown";
    }

    @Override
    public String writeInitTraceInfo(CtMethod ctMethod) throws ClassNotFoundException {
        TraceInfo root = new TraceInfo(1, 0, ClassType.SERVICE, ctMethod);
        boolean rootResult = writeTraceInfo(root);

        if (rootResult) {
        	fileName = root.getClassName() + ".png";
            return root.getKey();
        } else {
            return null;
        }
    }

    private String getUrlFromRequestMapping(Object annotation) {
        RequestMapping requestMapping = (RequestMapping) annotation;
        String[] value = requestMapping.value();
        if (value != null && value.length > 0) {
            return value[0];
        } else {
            return "";
        } 
    }

    @Override
    public boolean writeTraceInfo(TraceInfo traceInfo) {
        boolean traceTarget = isSatisfyTraceTarget(traceInfo.getClassType());
        if (traceTarget) {
        	sb.append(getTraceMessage(traceInfo) + "\n");
//            System.out.println(getTraceMessage(traceInfo));
            return true;
        } else if(traceInfo.getClassName().equals("AbstractGeneralDocService")) {
        	sb.append(getTraceMessage(traceInfo) + "\n");
        	return true;
        }

        return false;
    }

    private String getTraceMessage(TraceInfo info) {
//        return String.format(TRACE_FORMAT, info.getKey(), info.getSeq(), info.getDepth(),
//                info.getClassType().getValue(), info.getPackageName(), info.getClassName(), info.getMethodName());
    	if(info.getDepth() == 0) {
    		depthClassNameMap.put(0, info.getClassNameNewLine());
    		return "";
    	} else {
    		depthClassNameMap.put(info.getDepth(), info.getClassNameNewLine());
    		String parentClassName = depthClassNameMap.get(info.getDepth() - 1);
    		return parentClassName + "->" + info.getClassNameNewLine() + " : " + info.getMethodName();
    	}
    	
//    	return info.getClassName() + "-> : " + info.getMethodName();

        // DEBUG
        
//        if (ClassType.URL.equals(info.getClassType())) {
//            return info.getMethodName();
//        }
//
//        StringBuilder sb = new StringBuilder();
//        if (info.getDepth() > 1) {
//            for (int i = 1; i < info.getDepth(); i++) {
//                sb.append(" ");
//            }
//            sb.append("└ ");
//        }
//
//        sb.append(info.getClassName() + "." + info.getMethodName() + "();");
//        return sb.toString();
    }

    private boolean isSatisfyTraceTarget(ClassType classType) {
        for (int i = 0; i < traceTargetClassTypes.size(); i++) {
            ClassType type = traceTargetClassTypes.get(i);
            if (type.equals(classType)) {
                return true;
            }
        }

        return false;
    }
}
