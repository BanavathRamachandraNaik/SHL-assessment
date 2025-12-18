# SHL RAG Assessment – Java Implementation

## Description
This project implements a query-based recommendation API using Java and Spring Boot.
It accepts a text query and returns assessment recommendations in JSON format.

## API Endpoint
POST /recommend

### Request
{
  "query": "Junior Java developer assessment"
}

### Response
{
  "query": "...",
  "results": [...],
  "status": "success"
}

## Evaluation
Evaluation logic is implemented in `Evaluate.java` with sample queries.

## How to Run
mvn spring-boot:run
