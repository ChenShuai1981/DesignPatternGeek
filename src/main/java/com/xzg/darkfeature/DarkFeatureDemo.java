package com.xzg.darkfeature;

public class DarkFeatureDemo {

    public static void main(String[] args) {
        DarkLaunch darkLaunch = new DarkLaunch(); // 默认加载classpath下dark-rule.yaml文件中的灰度规则
        darkLaunch.addProgrammedDarkFeature("user_promotion", new UserPromotionDarkRule()); // 添加编程实现的灰度规则
        IDarkFeature darkFeature = darkLaunch.getDarkFeature("user_promotion");
        System.out.println(darkFeature.enabled());
        System.out.println(darkFeature.dark(893));

        IDarkFeature darkFeature2 = darkLaunch.getDarkFeature("call_newapi_getUserById");
        System.out.println(darkFeature2.enabled());
        System.out.println(darkFeature2.dark(893));

        IDarkFeature darkFeature3 = darkLaunch.getDarkFeature("create_order");
        System.out.println(darkFeature3.enabled());
    }

}
