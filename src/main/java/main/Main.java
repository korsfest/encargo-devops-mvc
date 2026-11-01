package main;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando microservicio MVC...");
        
    
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
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
            
            String response = html.toString();
            
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes("UTF-8"));
            os.close();
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor web escuchando en http://localhost:8080");
    }
}