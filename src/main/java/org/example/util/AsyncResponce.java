package org.example.util;

import java.util.concurrent.*;

/**
 * Created by sergeikostin on 3/4/16.
 */
public class AsyncResponce<V> implements Future<V> {

    private V value;
    private Exception executeException;
    private boolean isCompletedExceptionally = false;
    private boolean isCanceled = false;
    private boolean isDone = false;
    private long checkCompletedInterval = 100;

    public AsyncResponce() {
    }

    public AsyncResponce(V val) {
        this.value = val;
        this.isDone = true;
    }

    public AsyncResponce(Throwable ex) {
        this.isDone = true;
        this.isCompletedExceptionally = true;
        this.executeException = new Exception(ex);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isDone = true;
        this.isCanceled = true;
        return false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCanceled;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    public boolean isCompletedExceptionally(){
        return this.isCompletedExceptionally;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {

        block(0);

        if(isCancelled()){
            throw new CancellationException();
        }

        if(isCompletedExceptionally()){
            throw new ExecutionException(this.executeException);
        }

        if(isDone()){
            return this.value;
        }

        throw new InterruptedException();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {

        long timeoutInMillis = unit.toMillis(timeout);
        block(timeoutInMillis);

        if(isCancelled()){
            throw new CancellationException();
        }

        if(isCompletedExceptionally()){
            throw new ExecutionException(this.executeException);
        }

        if(isDone()){
            return this.value;
        }

        throw new InterruptedException();
    }

    public boolean complete(V val){
        this.value = val;
        this.isDone = true;

        return true;
    }

    public boolean completeExceptionally(Throwable ex){
        this.value = null;
        this.executeException = new ExecutionException(ex);
        this.isCompletedExceptionally = true;
        this.isDone = true;

        return true;
    }

    public void setCheckCompletedInterval(long millis){
        this.checkCompletedInterval = millis;
    }

    private void block(long timeout) throws InterruptedException{
        long start = System.currentTimeMillis();

        //Block until done, canceled or timeout exceeded
        while(!isDone() && !isCancelled()){
            if(timeout > 0){
                long now = System.currentTimeMillis();
                if(now > start + timeout) break;
            }
            Thread.sleep(checkCompletedInterval);
        }
    }

}
