CREATE DATABASE castores_inventario;
GO

USE castores_inventario;
GO

INSERT INTO roles(nombre)
VALUES ('ADMIN');

INSERT INTO roles(nombre)
VALUES ('ALMACENISTA');

INSERT INTO usuarios
(nombre, correo, password, activo, rol_id)
VALUES
    (
        'Administrador',
        'admin@castores.com',
        '12345',
        1,
        1
    );

INSERT INTO usuarios
(nombre, correo, password, activo, rol_id)
VALUES
    (
        'Almacenista',
        'almacen@castores.com',
        '12345',
        1,
        2
    );