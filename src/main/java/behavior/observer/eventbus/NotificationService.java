package behavior.observer.eventbus;

public class NotificationService {
    public void sendInboxMessage(Long userId, String message) {
        System.out.println("sendInboxMessage: " + message + " for userId: " + userId);
    }
}
