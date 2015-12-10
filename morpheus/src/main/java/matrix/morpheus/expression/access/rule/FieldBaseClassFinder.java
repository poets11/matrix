package matrix.morpheus.expression.access.rule;

import javassist.*;
import javassist.expr.FieldAccess;
import matrix.morpheus.chain.node.MethodNode;
import matrix.morpheus.chain.node.Node;
import matrix.morpheus.expression.access.ClassFinder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by poets11 on 15. 12. 4..
 */
public class FieldBaseClassFinder implements ClassFinder {
    private Map<String, String> classMap = new HashMap<String, String>();

    public void put(String name, String className) {
        classMap.put(name, className);
    }

    @Override
    public CtMethod findActualMethod(Node parentNode, FieldAccess lastAccessedField, CtMethod ctMethod) {
        try {
            CtClass methodClass = ctMethod.getDeclaringClass();

            CtField field = lastAccessedField.getField();
            CtClass fieldClass = field.getType();

            if (fieldClass.getName().equals(methodClass.getName()) == false) {
                return null;
            }

            CtMethod actualMethod = findWithQualifier(lastAccessedField, ctMethod);
            if (actualMethod == null) {
                actualMethod = findWithFieldName(lastAccessedField, ctMethod);
            }

            if (actualMethod == null) {
                actualMethod = findWithSetterName(parentNode, lastAccessedField, ctMethod);
            }

            return actualMethod;
        } catch (Exception e) {
            return null;
        }
    }

    private CtMethod findWithSetterName(Node parentNode, FieldAccess lastAccessedField, CtMethod ctMethod) throws NotFoundException, ClassNotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtField field = lastAccessedField.getField();

        CtClass param = field.getType();
        CtClass declaredCtClass = field.getDeclaringClass();
        CtClass parentNodeClass = null;
        if (parentNode instanceof MethodNode) {
            try {
                parentNodeClass = pool.get(((MethodNode) parentNode).getPackageName() + "." + ((MethodNode) parentNode).getClassName());
            } catch (NotFoundException nfe) {
            }
        }

        String methodNameByFieldName = "set" + StringUtils.capitalize(lastAccessedField.getFieldName());
        String methodNameByFieldType = "set" + param.getSimpleName();
        CtClass[] params = {param};

        CtMethod setter = findMethod(declaredCtClass, methodNameByFieldName, params);
        if (isValidMethod(setter) == false) {
            setter = findMethod(declaredCtClass, methodNameByFieldType, params);
        }

        if (isValidMethod(setter) == false) {
            if (parentNodeClass != null) {
                setter = findMethod(parentNodeClass, methodNameByFieldName, params);

                if (isValidMethod(setter) == false) {
                    setter = findMethod(parentNodeClass, methodNameByFieldType, params);
                }
            }
        }

        if (isValidMethod(setter) == true) {
            Object annotation = setter.getAnnotation(Qualifier.class);
            if (annotation != null) {
                Qualifier qualifier = (Qualifier) annotation;
                String name = qualifier.value();
                String className = classMap.get(name);

                CtClass ctClass = pool.get(className);
                return ctClass.getDeclaredMethod(ctMethod.getName(), ctMethod.getParameterTypes());
            }
        }

        return null;
    }

    private boolean isValidMethod(CtMethod ctMethod) {
        return ctMethod != null && Modifier.isAbstract(ctMethod.getModifiers()) == false;
    }

    private CtMethod findMethod(CtClass ctClass, String methodName, CtClass[] params) {
        try {
            return ctClass.getDeclaredMethod(methodName, params);
        } catch (NotFoundException e) {
            return null;
        }
    }

    private CtMethod findWithFieldName(FieldAccess lastAccessedField, CtMethod ctMethod) {
        try {
            String fieldName = lastAccessedField.getFieldName();
            String className = classMap.get(fieldName);

            if (className != null) {
                ClassPool pool = ClassPool.getDefault();

                CtClass ctClass = pool.get(className);
                return ctClass.getDeclaredMethod(ctMethod.getName(), ctMethod.getParameterTypes());
            }
        } catch (Exception e) {
        }

        return null;
    }

    private CtMethod findWithQualifier(FieldAccess lastAccessedField, CtMethod ctMethod) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtField field = lastAccessedField.getField();
            Object annotation = field.getAnnotation(Qualifier.class);
            if (annotation != null) {
                Qualifier qualifier = (Qualifier) annotation;

                String name = qualifier.value();
                String className = classMap.get(name);

                CtClass ctClass = pool.get(className);
                return ctClass.getDeclaredMethod(ctMethod.getName(), ctMethod.getParameterTypes());
            }
        } catch (Exception e) {
        }

        return null;
    }
}
