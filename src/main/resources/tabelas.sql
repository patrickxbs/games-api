CREATE TABLE IF NOT EXISTS users (
    id int auto_increment primary key,
    nome varchar(50) not null,
    email varchar(60) not null unique,
    senha varchar(100) not null
);

CREATE TABLE IF NOT EXISTS categorias (
    id int auto_increment primary key,
    nome varchar(50) not null unique
);

CREATE TABLE IF NOT EXISTS games (
    id int auto_increment primary key,
    nome varchar(50) not null,
    descricao text not null,
    preco DECIMAL(5,2) not null,
    categoria_id int not null,
    foreign key(categoria_id) references categorias(id)
);