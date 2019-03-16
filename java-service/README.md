## Java service operational monitoring example

Application side:
- skeleton spring boot application 
- uses micrometer library for operational metrics collection
- exposes metrics in prometheus format

Infrastructure:
- Prometheus server scraping metrics from the Java application
- Grafana server configured with Prometheus as a datasource and a dashboard for JVM metrics
