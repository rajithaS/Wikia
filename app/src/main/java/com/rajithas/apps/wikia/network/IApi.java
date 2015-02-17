package com.rajithas.apps.wikia.network;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public interface IApi<R, I> {
    public R getContent(I input) throws Exception;
}
