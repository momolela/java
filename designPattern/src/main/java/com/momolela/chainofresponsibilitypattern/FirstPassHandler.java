package com.momolela.chainofresponsibilitypattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2025/1/8 10:58
 */
public class FirstPassHandler extends Handler {

    private final int score = 90;

    /**
     * 假设关卡得分
     *
     * @return
     */
    public int play() {
        return 90;
    }

    /**
     * 处理类
     *
     * @return 关卡得分
     */
    @Override
    public int handler() {
        int score = play();
        System.out.println("闯过第一关，分数：" + score);
        if (score >= this.score) {
            if (this.next != null) {
                return this.next.handler();
            }
        }
        return score;
    }
}
