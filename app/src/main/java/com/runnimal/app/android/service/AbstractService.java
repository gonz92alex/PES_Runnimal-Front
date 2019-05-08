package com.runnimal.app.android.service;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class AbstractService {

    private final CompositeDisposable compositeDisposable;
    private final Scheduler executorThread;
    private final Scheduler uiThread;

    public AbstractService(Scheduler executorThread, Scheduler uiThread) {
        this.executorThread = executorThread;
        this.uiThread = uiThread;
        compositeDisposable = new CompositeDisposable();
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected <T> void execute(Observable<T> useCase, DisposableObserver<T> callback) {

        if (callback == null) {
            throw new IllegalArgumentException("disposableObserver must not be null");
        }

        final Observable<T> observable = useCase.subscribeOn(executorThread).observeOn(uiThread);

        DisposableObserver observer = observable.subscribeWith(callback);
        compositeDisposable.add(observer);
    }
}
