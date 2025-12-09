import java.util.concurrent.Semaphore;

class Consumer implements Runnable{
    private final Buffer buffer;
    private final int sleepTime;
    private final int id;
    private final Semaphore mutex;
    private final Semaphore consumerLock;    
    private final Semaphore producerLock;
    private final Semaphore maxBufferSize;
    
    public Consumer(int id, Buffer buffer, int sleepTime, Semaphore mutex, Semaphore consumerLock, Semaphore producerLock, Semaphore maxBufferSize) {
        this.id = id;
        this.buffer = buffer;
        this.sleepTime = sleepTime;
        this.mutex = mutex;
        this.consumerLock = consumerLock;
        this.producerLock = producerLock;
        this.maxBufferSize = maxBufferSize;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                consumerLock.release();
                consumerLock.acquire();
                mutex.acquire();
                int item = buffer.remove();
                mutex.release();
                maxBufferSize.release();
                if (item == -1) break;
                System.out.println("Consumer " + id + " consumed item " + item);
                Thread.sleep(sleepTime);
                producerLock.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}