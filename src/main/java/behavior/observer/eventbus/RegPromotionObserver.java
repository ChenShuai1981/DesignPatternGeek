package behavior.observer.eventbus;

import com.google.common.eventbus.Subscribe;

public class RegPromotionObserver {
    private PromotionService promotionService = new PromotionService(); // 依赖注入

    @Subscribe
    public void handleRegSuccess(Long userId) {
        promotionService.issueNewUserExperienceCash(userId);
    }
}