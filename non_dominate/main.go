package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
	"sync"
)

var solutions []string
var nonDominateSolutions []string

func readFile(filename string) []string {
	results := make([]string, 0)
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}

	defer file.Close()

	scanner := bufio.NewScanner(file)

	for scanner.Scan() {
		results = append(results, scanner.Text())
	}
	return results
}

func getFitness(s string) (float64, float64) {
	elements := strings.Split(s, " ")
	elements = elements[len(elements)-2:]

	time, err := strconv.ParseFloat(elements[0], 64)
	if err != nil {
		panic(err)
	}

	distance, err := strconv.ParseFloat(elements[1], 64)
	if err != nil {
		panic(err)
	}

	return time, distance
}

func isNonDominate(s1 string) bool {
	isNonDominate := true
	time1, distance1 := getFitness(s1)
	for _, s2 := range solutions {
		time2, distance2 := getFitness(s2)

		if time1 > time2 && distance1 > distance2 {
			isNonDominate = false
			break
		}
	}

	return isNonDominate
}

func remove(s []string, i int) []string {
	s[i] = s[len(s)-1]
	return s[:len(s)-1]
}

func printNonDominateSolutions() {
	for _, solution := range nonDominateSolutions {
		fmt.Println(solution)
	}
}

func main() {
	var wg sync.WaitGroup
	var mutex sync.Mutex

	solutions = readFile("solutions10.txt")

	for index, solution := range solutions {
		wg.Add(1)
		go func(index int, solution string) {
			defer wg.Done()
			if isNonDominate(solution) {
				mutex.Lock()
				nonDominateSolutions = append(nonDominateSolutions, solution)
				mutex.Unlock()
			}
		}(index, solution)

	}

	wg.Wait()
	printNonDominateSolutions()
}
