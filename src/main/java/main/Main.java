package main;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;
import software.amazon.awssdk.services.cloudwatch.model.StandardUnit;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static CloudWatchClient cwClient;

    public static void main(String[] args) throws Exception {
        logger.info("Iniciando microservicio MVC con integracion AWS CloudWatch...");

        try {
            cwClient = CloudWatchClient.builder()
                    .region(Region.US_EAST_1)
                    .build();
        } catch (Exception e) {
            logger.warn("No se pudo inicializar CloudWatchClient (entorno local sin IAM): {}", e.getMessage());
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Ruta principal
        server.createContext("/", exchange -> {
            StringBuilder html = new StringBuilder();
            html.append("<html><head><meta charset='UTF-8'><title>Microservicio Usuarios</title></head>");
            html.append("<body style='font-family: Arial, sans-serif; padding: 20px;'>");
            html.append("<h2>Lista de Usuarios prueba2 (Microservicio Activo)</h2>");
            html.append("<ul>");
            html.append("<li>Cris</li>");
            html.append("<li>Francisco</li>");
            html.append("<li>Vicente</li>");
            html.append("<li>Diego</li>");
            html.append("</ul></body></html>");

            byte[] response = html.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }

            logger.info("GET / atendido correctamente (HTTP 200)");
            enviarMetricaCloudWatch("PeticionesExitosas", 1.0);
        });

        // Healthcheck para disponibilidad
        server.createContext("/health", exchange -> {
            String status = "{\"status\":\"UP\"}";
            byte[] response = status.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
            enviarMetricaCloudWatch("DisponibilidadHealth", 1.0);
        });

        // Ruta para simular error y probar logs/alarmas en CloudWatch
        server.createContext("/error-test", exchange -> {
            logger.error("ERROR CRITICO SIMULADO en /error-test - Registrado en CloudWatch Logs");
            enviarMetricaCloudWatch("ErroresAplicacion", 1.0);

            String msg = "{\"error\":\"Falla simulada registrada en CloudWatch\"}";
            byte[] response = msg.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        });

        server.setExecutor(null);
        server.start();
        logger.info("Servidor web escuchando en http://localhost:8080");
        enviarMetricaCloudWatch("DisponibilidadHealth", 1.0);
    }

    public static void enviarMetricaCloudWatch(String nombreMetrica, double valor) {
        if (cwClient == null) return;
        try {
            Dimension dimension = Dimension.builder()
                    .name("Servicio")
                    .value("encargo-devops-mvc")
                    .build();

            MetricDatum datum = MetricDatum.builder()
                    .metricName(nombreMetrica)
                    .unit(StandardUnit.COUNT)
                    .value(valor)
                    .timestamp(Instant.now())
                    .dimensions(dimension)
                    .build();

            PutMetricDataRequest request = PutMetricDataRequest.builder()
                    .namespace("EncargoDevOps/Microservicio")
                    .metricData(datum)
                    .build();

            cwClient.putMetricData(request);
        } catch (Exception e) {
            logger.warn("No se pudo enviar metrica {} a CloudWatch: {}", nombreMetrica, e.getMessage());
        }
    }
}