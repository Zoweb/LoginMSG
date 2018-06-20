package me.zoweb.loginmsg.lambda;

/**
 * Single-param lambda
 */
public interface Lambda<TParam> {
    void run(TParam param);
}