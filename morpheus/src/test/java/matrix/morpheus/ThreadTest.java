package matrix.morpheus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by poets11 on 15. 12. 11..
 */
public class ThreadTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 20; i++) {
            Runner runner = new Runner();
            runner.setId("" + i);
            service.execute(runner);
            System.out.println("push : " + i);
        }

        service.shutdown();
        while (service.isTerminated() == false) {
        }
        System.out.println("complete");
    }

    public static class Runner implements Runnable {
        private String id;

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("run : " + id + " / " + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
