package main

// import "fmt"

// func main() {
// 	var n int
// 	fmt.Scanf("%d", &n)
// 	go msg()
//     fmt.Printf("Fibonacci de %d: %d\n", n, fib(n))
// }

// func fib(n int) int {
// 	if n <= 1 {
// 		return 1
// 	} else {
// 		return (fib(n-1) + fib(n-2))
// 	}
// }

// func fib(n int) int {
//     if n == 0 || n == 1 { return 1 }

//     a, b := 1, 1
//     for i := 2; i <= n; i++ {
//         a, b = b, a+b
//     }
//     return b
// }

// func msg() {
// 	for {
// 		fmt.Printf("calma\n")
// 	}
// }

import (
	"fmt"
	"math/rand/v2" // Use v2 package for automatic seeding
)

func main() {
	ch := make(chan int)
	go randNum(ch)
	out(ch)
}

func randNum(ch chan <- int){
	for{
		ch <- rand.IntN(100)
	}
}

func out(ch <- chan int){
	for{
		randNum := <- ch
		if randNum % 2 == 1{
			fmt.Println(randNum)
		}
	}
}