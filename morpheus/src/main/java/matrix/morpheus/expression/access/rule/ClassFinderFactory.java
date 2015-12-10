package matrix.morpheus.expression.access.rule;

import javassist.CtMethod;
import javassist.Modifier;
import javassist.expr.FieldAccess;
import matrix.morpheus.chain.node.Node;
import matrix.morpheus.expression.access.ClassFinder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by poets11 on 15. 7. 20..
 */
public class ClassFinderFactory {
    private List<ClassFinder> classFinders = new ArrayList<ClassFinder>();

    public ClassFinderFactory() {

    }

    public ClassFinderFactory(Class<? extends ClassFinder>... classFinderClasses) throws IllegalAccessException, InstantiationException {
        for (int i = 0; i < classFinderClasses.length; i++) {
            Class<? extends ClassFinder> classFinderClass = classFinderClasses[i];
            classFinders.add(classFinderClass.newInstance());
        }
    }

    public ClassFinderFactory(String[] classFinderNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (int i = 0; i < classFinderNames.length; i++) {
            String classFinderName = classFinderNames[i];
            ClassFinder classFinder = (ClassFinder) Class.forName(classFinderName).newInstance();
            classFinders.add(classFinder);
        }
    }

    public CtMethod findActualClass(FieldAccess lastAccessedField, CtMethod ctMethod) {
        return findActualClass(null, lastAccessedField, ctMethod);
    }

    public CtMethod findActualClass(Node parentNode, FieldAccess lastAccessedField, CtMethod ctMethod) {
        for (int i = 0; i < classFinders.size(); i++) {
            ClassFinder classFinder = classFinders.get(i);

            CtMethod actualMethod = classFinder.findActualMethod(parentNode, lastAccessedField, ctMethod);
            if (actualMethod != null && Modifier.isAbstract(actualMethod.getModifiers()) == false) {
                return actualMethod;
            }
        }

        return null;
    }

    public List<ClassFinder> getClassFinders() {
        return classFinders;
    }

    public void setClassFinders(List<ClassFinder> classFinders) {
        this.classFinders = classFinders;
    }

    public void addClassFinder(ClassFinder classFinder) {
        classFinders.add(classFinder);
    }
}
