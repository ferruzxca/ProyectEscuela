package com.proyectescuela.web;

import java.sql.Connection;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final Instant startedAt = Instant.now();

    public DashboardController(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public String dashboard() {
        Map<String, Object> health = healthPayload();
        boolean dbOk = Boolean.TRUE.equals(health.get("dbOk"));
        String dbStatus = dbOk ? "OK" : "ERROR";
        String dbVersion = String.valueOf(health.get("dbVersion"));
        String counts = String.valueOf(health.get("counts"));

        StringBuilder html = new StringBuilder();
        html.append("<!doctype html>");
        html.append("<html lang=\"es\">");
        html.append("<head>");
        html.append("<meta charset=\"utf-8\" />");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />");
        html.append("<title>ProyectEscuela API</title>");
        html.append("<style>");
        html.append(":root{--bg:#0b1020;--card:#121833;--ok:#22c55e;--bad:#ef4444;--muted:#94a3b8;}");
        html.append("*{box-sizing:border-box;}");
        html.append("body{margin:0;font-family:system-ui,-apple-system,Segoe UI,Roboto,sans-serif;");
        html.append("background:radial-gradient(1200px 800px at 10% -10%,#1b2350,var(--bg));color:#e2e8f0;}");
        html.append(".wrap{max-width:900px;margin:40px auto;padding:0 20px;}");
        html.append(".card{background:var(--card);border:1px solid rgba(255,255,255,.08);border-radius:16px;");
        html.append("padding:20px;box-shadow:0 20px 60px rgba(0,0,0,.35);}");
        html.append(".title{font-size:28px;font-weight:700;}");
        html.append(".muted{color:var(--muted);}");
        html.append(".grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(240px,1fr));gap:16px;margin-top:16px;}");
        html.append(".pill{display:inline-block;padding:6px 10px;border-radius:999px;font-weight:600;}");
        html.append(".ok{background:rgba(34,197,94,.15);color:var(--ok);border:1px solid rgba(34,197,94,.35);}");
        html.append(".bad{background:rgba(239,68,68,.12);color:var(--bad);border:1px solid rgba(239,68,68,.35);}");
        html.append("a{color:#60a5fa;text-decoration:none;}code{background:rgba(255,255,255,.06);padding:2px 6px;border-radius:6px;}");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div class=\"wrap\">");
        html.append("<div class=\"card\">");
        html.append("<div class=\"title\">ProyectEscuela API</div>");
        html.append("<p class=\"muted\">Estado en tiempo real del backend y base de datos.</p>");
        html.append("<div class=\"grid\">");
        html.append("<div>");
        html.append("<div class=\"muted\">API</div>");
        html.append("<div class=\"pill ok\">ONLINE</div>");
        html.append("<div class=\"muted\" style=\"margin-top:8px\">Inicio</div>");
        html.append("<div><code>").append(startedAt).append("</code></div>");
        html.append("</div>");
        html.append("<div>");
        html.append("<div class=\"muted\">Base de datos</div>");
        html.append("<div class=\"pill ").append(dbOk ? "ok" : "bad").append("\">").append(dbStatus).append("</div>");
        html.append("<div class=\"muted\" style=\"margin-top:8px\">Versión</div>");
        html.append("<div><code>").append(dbVersion).append("</code></div>");
        html.append("</div>");
        html.append("<div>");
        html.append("<div class=\"muted\">Accesos</div>");
        html.append("<div style=\"margin-top:8px\"><a href=\"/api/health\">/api/health</a></div>");
        html.append("<div><a href=\"/api/carreras\">/api/carreras</a></div>");
        html.append("<div><a href=\"/api/turnos\">/api/turnos</a></div>");
        html.append("</div>");
        html.append("</div>");
        html.append("<div style=\"margin-top:16px\" class=\"muted\">Conteos: <code>").append(counts).append("</code></div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</body>");
        html.append("</html>");
        return html.toString();
    }

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return healthPayload();
    }

    private Map<String, Object> healthPayload() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("service", "proyectescuela-api");
        out.put("startedAt", startedAt.toString());

        boolean dbOk = false;
        String dbVersion = "unknown";
        Map<String, Object> counts = new LinkedHashMap<>();

        try (Connection conn = dataSource.getConnection()) {
            dbOk = conn.isValid(2);
        } catch (Exception ignored) {
            dbOk = false;
        }

        if (dbOk) {
            try {
                dbVersion = jdbcTemplate.queryForObject("select version()", String.class);
                counts.put("carreras", jdbcTemplate.queryForObject("select count(*) from carreras", Integer.class));
                counts.put("turnos", jdbcTemplate.queryForObject("select count(*) from turnos", Integer.class));
                counts.put("grados", jdbcTemplate.queryForObject("select count(*) from grados", Integer.class));
                counts.put("grupos", jdbcTemplate.queryForObject("select count(*) from grupos", Integer.class));
                counts.put("alumnos", jdbcTemplate.queryForObject("select count(*) from alumnos", Integer.class));
            } catch (Exception ignored) {
                dbVersion = "error";
            }
        }

        out.put("dbOk", dbOk);
        out.put("dbVersion", dbVersion);
        out.put("counts", counts);
        return out;
    }
}
