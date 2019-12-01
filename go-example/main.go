package main

import (
	"github.com/prometheus/client_golang/prometheus/promhttp"
	"log"
	"net/http"
)

func main() {
	http.Handle("/prometheus", promhttp.Handler())
	log.Print(http.ListenAndServe(":8081", nil))
}
