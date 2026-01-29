from time import sleep
import threading

mutex = threading.Semaphore(1)
n = int(input())
signal = 0

def fib(n):
    global signal
    din = [1, 1]

    for i in range(1, n):
        # sleep(0.1)
        din.append(din[i] + din[i-1])
    
    mutex.acquire()
    print('Fibonacci:', din[-1])
    signal = 1
    mutex.release()

t_fib = threading.Thread(target=fib, args=(n,))

t_fib.start()
while True:
    mutex.acquire()
    if signal == 1: break
    mutex.release()
    print('calma')
