package matrix.morpheus.expression.access.rule;

import javassist.*;
import matrix.morpheus.expression.access.ClassFinder;

/**
 * Created by poets11 on 15. 7. 21..
 */
public class DocClassFinder implements ClassFinder {
	@Override
	public CtClass findActualClass(CtField accessedField, CtMethod ctMethod)
			throws NotFoundException {
		try {
			String name = ctMethod.getDeclaringClass().getName();
			
			if(name.equals("hanmi.ces.common.doc.group.service.dataservice.AbstractGeneralDocService")) {
				ClassPool pool = ClassPool.getDefault();
				return pool.get(name);
			}
			
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
