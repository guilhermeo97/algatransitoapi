CREATE TABLE veiculo (
	id bigint not null auto_increment,
    proprietario_id bigint not null,
    marca varchar(20) not null,
    modelo varchar(20) not null,
    placa varchar(7) not null unique,
    status varchar(20) not null,
    data_cadastro datetime not null,
    data_apreensao datetime,
    
    primary key(id),
    foreign key(proprietario_id) references proprietario(id)
)