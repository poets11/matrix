package matrix.morpheus.init;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import matrix.morpheus.AssembleConfig;
import matrix.morpheus.AssembleException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class ServiceInitialPointCollector implements InitialPointCollector {
	@Override
	public List<CtMethod> getInitialPoints(AssembleConfig assembleConfig) {
		try {
			List<CtMethod> serviceMethods = new ArrayList<CtMethod>();

			ClassPool pool = ClassPool.getDefault();

			// FIXME 916개를 만들어내는 RegexPatternTypeFilter 대신에 AnnotationTypeFilter
			// 를 사용하도록 변경한다.

			ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
			provider.addIncludeFilter(new AnnotationTypeFilter(Service.class));

			Set<BeanDefinition> classes = provider.findCandidateComponents(assembleConfig.getBasePackage());
			for (BeanDefinition bean : classes) {
				String className = bean.getBeanClassName();

				if (className.startsWith("hanmi.ces.common.doc.group.service.dataservice") == false 
						&& className.startsWith("hanmi.ces.common.doc.popup.service.dataservice") == false) {
					continue;
				}

				CtClass ctClass = pool.get(className);
				CtMethod[] methods = ctClass.getDeclaredMethods();
				for (int i = 0; i < methods.length; i++) {
					CtMethod method = methods[i];

					if (method.getName().equals("load")) {
						serviceMethods.add(method);
					}
				}
			}

			return serviceMethods;
		} catch (Exception e) {
			throw new AssembleException(e);
		}
	}
}
