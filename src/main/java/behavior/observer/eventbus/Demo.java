package behavior.observer.eventbus;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        UserController controller = new UserController();
        RegNotificationObserver regNotificationObserver = new RegNotificationObserver();
        RegPromotionObserver regPromotionObserver = new RegPromotionObserver();
        controller.setRegObservers(Arrays.asList(regNotificationObserver, regPromotionObserver));
        controller.register("13801899719", "12345");
    }
}
