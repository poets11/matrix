package matrix.morpheus.sample.resource;

/**
 * Created by poets11 on 15. 12. 2..
 */
public abstract class AbstractSampleService {
    public void concreteMethodInAbstract() {
        privateConcreteMethodInAbstract();
    }

    public abstract void abstractMethodInAbstract();

    private void privateConcreteMethodInAbstract() {

    }
}
