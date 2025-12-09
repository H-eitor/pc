import java.util.concurrent.*;

class Producer implements Runnable{
    private final Buffer buffer;
    private final int maxItems;
    private final int sleepTime;
    private final int id;
    private final Semaphore mutex;
    private final Semaphore consumerLock;    
    private final Semaphore producerLock;
    private final Semaphore maxBufferSize;
    
    public Producer(int id, Buffer buffer, int maxItems, int sleepTime, Semaphore mutex, Semaphore consumerLock, Semaphore producerLock, Semaphore maxBufferSize) {
        this.id = id;
        this.buffer = buffer;
        this.maxItems = maxItems;
        this.sleepTime = sleepTime;
        this.mutex = mutex;
        this.producerLock = producerLock;
        this.consumerLock = consumerLock;
        this.maxBufferSize = maxBufferSize;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < maxItems; i++) {
            try {
                producerLock.acquire();
                Thread.sleep(sleepTime);
                int item = (int) (Math.random() * 100);
                System.out.println("Producer " + id + " produced item " + item);
                maxBufferSize.acquire();
                mutex.acquire();
                buffer.put(item);
                mutex.release();
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            consumerLock.release();
        }
    }
}
