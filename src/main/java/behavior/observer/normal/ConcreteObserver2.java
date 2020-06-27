package behavior.observer.normal;

public class ConcreteObserver2 implements Observer {
    @Override
    public void update(Message message) {
        System.out.println("Notified message for ConcreteObserver2");
    }
}
