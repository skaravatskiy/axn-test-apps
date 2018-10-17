package com.rshtukaraxondevgroup.bookstest;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

public class RxImmediateSchedulerRule implements TestRule {
    private static final TestScheduler TEST_SCHEDULER = new TestScheduler();
    private static final Scheduler IMMEDIATE_SCHEDULER = new Scheduler() {
        @Override
        public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
            return super.scheduleDirect(run, 0, unit);
        }

        @Override
        public Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(Runnable::run);
        }
    };

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> TEST_SCHEDULER);
                RxJavaPlugins.setComputationSchedulerHandler(
                        scheduler -> TEST_SCHEDULER);
                RxJavaPlugins.setNewThreadSchedulerHandler(
                        scheduler -> TEST_SCHEDULER);
                RxAndroidPlugins.setMainThreadSchedulerHandler(
                        scheduler -> IMMEDIATE_SCHEDULER);
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }

    public TestScheduler getTestScheduler() {
        return TEST_SCHEDULER;
    }
}
