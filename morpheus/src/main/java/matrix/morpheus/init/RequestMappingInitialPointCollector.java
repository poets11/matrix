package matrix.morpheus.init;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import matrix.morpheus.AssembleConfig;
import matrix.morpheus.AssembleException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class RequestMappingInitialPointCollector implements InitialPointCollector {
    @Override
    public List<CtMethod> getInitialPoints(AssembleConfig assembleConfig) {
        try {
            List<CtMethod> requestMappingMethods = new ArrayList<CtMethod>();

            ClassPool pool = ClassPool.getDefault();

            // FIXME 916개를 만들어내는 RegexPatternTypeFilter 대신에 AnnotationTypeFilter 를 사용하도록 변경한다.

            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
            provider.addIncludeFilter(new AnnotationTypeFilter(Controller.class));

            Set<BeanDefinition> classes = provider.findCandidateComponents(assembleConfig.getBasePackage());
            for (BeanDefinition bean : classes) {
                String className = bean.getBeanClassName();

                CtClass ctClass = pool.get(className);
                CtMethod[] methods = ctClass.getDeclaredMethods();

                for (int i = 0; i < methods.length; i++) {
                    CtMethod method = methods[i];
                    
                    try {
                    	Object[] availableAnnotations = method.getAvailableAnnotations();
                    	for (Object object : availableAnnotations) {
							if(object instanceof RequestMapping) {
								RequestMapping mapping = (RequestMapping) object;
								String[] value = mapping.value();
								String mappedUrl = value[0];
								
								if(mappedUrl.startsWith("/doc/group/fileDown")) {
//									System.out.println(mappedUrl);
									requestMappingMethods.add(method);
								}
							}
						}
//                    	Object requestMappingAnnotaion = method.getAnnotation(RequestMapping.class);
//                        if (requestMappingAnnotaion != null) {
//                            requestMappingMethods.add(method);
//                        }
                    } catch (Exception e) {
                    	System.out.println(className + " : " + method.getName());
                    }
                }
            }

            return requestMappingMethods;
        } catch(Exception e) {
            throw new AssembleException(e);
        }
    }
}
