package sync.threadlocal;

public class InheritableThreadLocalDemo {

    public static void main(String[] args) {

        var inhThreadLocal = new InheritableThreadLocal<String>();

        var parentTh = new Thread(() -> {
            inhThreadLocal.set("PARENT THREAD LOCAL");
            System.out.println("PARENT THREAD VAL: " + inhThreadLocal.get());


            var childTh = new Thread(() -> {
                System.out.println("CHILD THREAD VAL: " + inhThreadLocal.get());
            });

            childTh.start();
        });

        parentTh.start();

    }
}
