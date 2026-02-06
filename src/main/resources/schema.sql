CREATE TABLE IF NOT EXISTS carreras (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  sigla VARCHAR(10) NOT NULL UNIQUE,
  activo TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS turnos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  sigla VARCHAR(5) NOT NULL UNIQUE,
  activo TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS grados (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  numero INT NOT NULL UNIQUE,
  activo TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS grupos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  carrera_id BIGINT NOT NULL,
  turno_id BIGINT NOT NULL,
  grado_id BIGINT NOT NULL,
  consecutivo INT NOT NULL,
  codigo VARCHAR(20) NOT NULL UNIQUE,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_grupo_carrera FOREIGN KEY (carrera_id) REFERENCES carreras(id),
  CONSTRAINT fk_grupo_turno FOREIGN KEY (turno_id) REFERENCES turnos(id),
  CONSTRAINT fk_grupo_grado FOREIGN KEY (grado_id) REFERENCES grados(id),
  CONSTRAINT uq_grupo_comp UNIQUE (carrera_id, turno_id, grado_id, consecutivo)
);

CREATE TABLE IF NOT EXISTS alumnos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  matricula VARCHAR(20) NOT NULL UNIQUE,
  nombre VARCHAR(80) NOT NULL,
  apellidos VARCHAR(120) NOT NULL,
  grupo_id BIGINT NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_alumno_grupo FOREIGN KEY (grupo_id) REFERENCES grupos(id)
);

DROP TRIGGER IF EXISTS trg_grupos_codigo;
DROP TRIGGER IF EXISTS trg_grupos_codigo_upd;
DELIMITER $$
CREATE TRIGGER trg_grupos_codigo
BEFORE INSERT ON grupos
FOR EACH ROW
BEGIN
  DECLARE v_consecutivo INT;
  DECLARE v_sigla_carrera VARCHAR(10);
  DECLARE v_sigla_turno VARCHAR(5);
  DECLARE v_num_grado INT;

  IF NEW.consecutivo IS NULL OR NEW.consecutivo = 0 THEN
    SELECT IFNULL(MAX(consecutivo), 0) + 1
      INTO v_consecutivo
      FROM grupos
     WHERE carrera_id = NEW.carrera_id
       AND turno_id = NEW.turno_id
       AND grado_id = NEW.grado_id;
    SET NEW.consecutivo = v_consecutivo;
  END IF;

  SELECT sigla INTO v_sigla_carrera FROM carreras WHERE id = NEW.carrera_id;
  SELECT sigla INTO v_sigla_turno FROM turnos WHERE id = NEW.turno_id;
  SELECT numero INTO v_num_grado FROM grados WHERE id = NEW.grado_id;

  SET NEW.codigo = CONCAT(
    v_sigla_carrera,
    LPAD(v_num_grado, 2, '0'),
    LPAD(NEW.consecutivo, 2, '0'),
    '-',
    v_sigla_turno
  );
END $$
CREATE TRIGGER trg_grupos_codigo_upd
BEFORE UPDATE ON grupos
FOR EACH ROW
BEGIN
  DECLARE v_consecutivo INT;
  DECLARE v_sigla_carrera VARCHAR(10);
  DECLARE v_sigla_turno VARCHAR(5);
  DECLARE v_num_grado INT;

  IF NEW.consecutivo IS NULL OR NEW.consecutivo = 0 THEN
    SELECT IFNULL(MAX(consecutivo), 0) + 1
      INTO v_consecutivo
      FROM grupos
     WHERE carrera_id = NEW.carrera_id
       AND turno_id = NEW.turno_id
       AND grado_id = NEW.grado_id
       AND id <> NEW.id;
    SET NEW.consecutivo = v_consecutivo;
  END IF;

  SELECT sigla INTO v_sigla_carrera FROM carreras WHERE id = NEW.carrera_id;
  SELECT sigla INTO v_sigla_turno FROM turnos WHERE id = NEW.turno_id;
  SELECT numero INTO v_num_grado FROM grados WHERE id = NEW.grado_id;

  SET NEW.codigo = CONCAT(
    v_sigla_carrera,
    LPAD(v_num_grado, 2, '0'),
    LPAD(NEW.consecutivo, 2, '0'),
    '-',
    v_sigla_turno
  );
END $$
DELIMITER ;
