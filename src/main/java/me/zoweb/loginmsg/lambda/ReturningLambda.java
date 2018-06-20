package me.zoweb.loginmsg.lambda;

/**
 * Single-param lambda
 */
public interface ReturningLambda<TReturns> {
    TReturns run();
}