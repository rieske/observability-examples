GOCMD=go
GOBUILD=CGO_ENABLED=0 $(GOCMD) build
GOCLEAN=$(GOCMD) clean -testcache
GOGET=$(GOCMD) get

BINARY_NAME=bin/app
DOCKER_TAG=example:snapshot

all: build
build:
	$(GOBUILD) -o $(BINARY_NAME) -v
compose: build
	docker-compose up --build
clean:
	$(GOCLEAN)
	rm -f $(BINARY_NAME)

.PHONY: all test clean
