package com.rajithas.apps.wikia.model;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public interface IFactory<R, T> {
    public R create(T input);
}
