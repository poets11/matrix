package matrix.morpheus.expression.access.inspect;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import matrix.morpheus.expression.access.ClassType;
import matrix.morpheus.expression.access.ClassTypeInspector;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


/**
 * Created by poets11 on 15. 7. 20..
 */
public class DefaultClassTypeInspector implements ClassTypeInspector {
    @Override
    public ClassType inspect(CtField accessedField, CtMethod ctMethod) throws ClassNotFoundException {
        CtClass ctClass = ctMethod.getDeclaringClass();
        String className = ctClass.getSimpleName();

        Object controller = ctClass.getAnnotation(Controller.class);
        Object service = ctClass.getAnnotation(Service.class);
        Object repository = ctClass.getAnnotation(Repository.class);

        if (controller != null || className.endsWith("Controller")) {
            return ClassType.CONTROLLER;
        } else if (service != null || className.endsWith("Service") || className.endsWith("ServiceImpl")) {
            return ClassType.SERVICE;
        } else if (repository != null || className.endsWith("Repository") || className.endsWith("RepositoryImpl")) {
            return ClassType.REPOSITORY;
        }

        String packageName = ctClass.getPackageName();
        if (packageName.endsWith(".model")) {
            return ClassType.MODEL;
        }

        return ClassType.UNKNOWN;
    }
}
