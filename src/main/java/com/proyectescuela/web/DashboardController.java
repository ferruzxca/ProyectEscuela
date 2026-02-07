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

        return """
            <!doctype html>
            <html lang=\"es\">
            <head>
              <meta charset=\"utf-8\" />
              <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />
              <title>ProyectEscuela API</title>
              <style>
                :root { --bg:#0b1020; --card:#121833; --ok:#22c55e; --bad:#ef4444; --muted:#94a3b8; }
                * { box-sizing: border-box; }
                body { margin:0; font-family: system-ui, -apple-system, Segoe UI, Roboto, sans-serif; background: radial-gradient(1200px 800px at 10% -10%, #1b2350, var(--bg)); color:#e2e8f0; }
                .wrap { max-width: 900px; margin: 40px auto; padding: 0 20px; }
                .card { background: var(--card); border:1px solid rgba(255,255,255,.08); border-radius: 16px; padding: 20px; box-shadow: 0 20px 60px rgba(0,0,0,.35); }
                .title { font-size: 28px; font-weight: 700; }
                .muted { color: var(--muted); }
                .grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 16px; margin-top: 16px; }
                .pill { display:inline-block; padding: 6px 10px; border-radius: 999px; font-weight: 600; }
                .ok { background: rgba(34,197,94,.15); color: var(--ok); border:1px solid rgba(34,197,94,.35); }
                .bad { background: rgba(239,68,68,.12); color: var(--bad); border:1px solid rgba(239,68,68,.35); }
                a { color:#60a5fa; text-decoration: none; }
                code { background: rgba(255,255,255,.06); padding: 2px 6px; border-radius: 6px; }
              </style>
            </head>
            <body>
              <div class=\"wrap\">
                <div class=\"card\">
                  <div class=\"title\">ProyectEscuela API</div>
                  <p class=\"muted\">Estado en tiempo real del backend y base de datos.</p>
                  <div class=\"grid\">
                    <div>
                      <div class=\"muted\">API</div>
                      <div class=\"pill ok\">ONLINE</div>
                      <div class=\"muted\" style=\"margin-top:8px\">Inicio</div>
                      <div><code>""" + startedAt + """</code></div>
                    </div>
                    <div>
                      <div class=\"muted\">Base de datos</div>
                      <div class=\"pill """ + (dbOk ? "ok" : "bad") + "\">" + dbStatus + "</div>
                      <div class=\"muted\" style=\"margin-top:8px\">Versión</div>
                      <div><code>""" + dbVersion + """</code></div>
                    </div>
                    <div>
                      <div class=\"muted\">Accesos</div>
                      <div style=\"margin-top:8px\"><a href=\"/api/health\">/api/health</a></div>
                      <div><a href=\"/api/carreras\">/api/carreras</a></div>
                      <div><a href=\"/api/turnos\">/api/turnos</a></div>
                    </div>
                  </div>
                  <div style=\"margin-top:16px\" class=\"muted\">Conteos: <code>""" + counts + """</code></div>
                </div>
              </div>
            </body>
            </html>
            """;
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
