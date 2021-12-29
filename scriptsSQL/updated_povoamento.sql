use egym;

-- povoamento de planos:
INSERT INTO plano(nome, preco, descricao)
VALUES('bronze', 60, "Acesso aos dias da semana, exceto aos sabados e domingo"),
('prata', 80, "Acesso a todos os dias da semana"), ('ouro', 100, "acesso a todos os dias da semana e desconto em produtos");
-- ----------------------------------------------------------------------------------------------------------------------------

-- povoamento de clientes:
INSERT INTO cliente(plano_id, primeiro_nome, nome_meio, ultimo_nome, cpf, data_nascimento, telefone)
VALUES(1,'Rafael', 'Nepomuceno Siqueira', 'Campos', '11111111111', '2000-02-19', '3333-3333');

INSERT INTO cliente(plano_id, primeiro_nome, nome_meio, ultimo_nome, cpf, data_nascimento, telefone)
VALUES(2,'Ramon', 'Nepomuceno Siqueira', 'Campos', '33333333333', '1999-04-20', '3333-3333');

INSERT INTO cliente(plano_id, primeiro_nome, nome_meio, ultimo_nome, cpf, data_nascimento, telefone)
VALUES(1,'Gabriel', 'Nepomuceno Siqueira', 'Campos', '22222222222', '1998-07-15', '3333-3333');
-- -----------------------------------------------------------------------------------------------------------------------------

-- povoamento de fichas:
INSERT INTO ficha(id_cliente, data_inicio, peso_cliente)
VALUES(1, '2021-12-14', 62), (2, '2021-06-22', 78), (3, '2021-08-16', 68);
-- 1_Rafael, 2_Ramon, 3_Gabriel
-- ------------------------------------------------------------------------------------------------------------------------------

-- povoamento de treinos:
INSERT INTO treino(id_ficha, nome, tipo)
VALUES(1, 'A', 'Hipertrofia'), (1, 'B', 'Hipertrofia'), (1, 'C', 'Hipertrofia'),
(2, 'A', 'Hipertrofia'), (2, 'B', 'Hipertrofia'), (2, 'C', 'Hipertrofia'),
(3, 'Corrida', 'Aeróbico'), (3, 'Alongameto', 'Aeróbico'), (3, 'A', 'Hipertrofia');
-- ------------------------------------------------------------------------------------------------------------------------------

-- povoamento de exercício:
INSERT INTO exercicio(nome)
VALUES('Manguito cross'), ('Supino reto barra'), ('Voador'), ('Crucifixo'), -- A
('Pulley'), ('Encolhimento de ombro'), ('Prancha'), ('Bíceps corda'), -- B
('Flexão pé a pé'), ('Agachamento afundo'), ('Mesa flexora'), ('Banco abdutor'),-- C
('Esteira'), ('Bicicleta'), ('Anfersen'), -- Corrida
('Alongamento cobra'), ('Spinal Twist'), ('Alongamento joelho ao peito'); -- Alongamento
-- --------------------------------------------------------------------------------------------------------------------------------

-- povoamento de treino_tem_exercicio:
INSERT INTO treino_tem_exercicio(id_treino, id_exercicio)
VALUES(1,1),(1,2),(1,3),(1,4),  -- A _ RAFAEL
(2,5),(2,6),(2,7),(2,8), -- B _ RAFAEL
(3,9),(3,10),(3,11),(3,12), -- c _ RAFAEL
(4,1),(4,2),(4,3),(4,4), -- A _ RAMON
(5,5),(5,6),(5,7),(5,8), -- B _ RAMON
(6,9),(6,10),(6,11),(6,12), -- C _ RAMON
(7,13),(7,14),(7,15), -- CORRIDA _ GABRIEL
(8,16),(8,17),(8,18), -- ALONGAMENTO _ GABRIEL
(9,1),(9,2),(9,3),(9,4); -- A _ GABRIEL
-- ---------------------------------------------------------------------------------------------------------------------------------

-- povoamento de especificações:
INSERT INTO especificacao(id_exercicio, id_treino, repeticao, carga, series, descricao)
VALUES(1,1,12,5,3,''),(2,1,10,15,4,''),(3,1,10,35,3,''),(4,1,12,8,3,''), -- spec rafael_A
(5,2,10,35,3,'pulley costas'),(6,2,15,10,3,''),(7,2, null, null,3, 'fazer por 1 minuto'),(8,2,10,20,3,''), -- spec rafael_B
(9,3,10,null,3,''),(10,3,10,5,3,''),(11,3,12,15,3,''),(12,3,10,20,3,''), -- spec rafael_C
(1,4,12,10,3,''),(2,4,10,30,4,''),(3,4,10,50,3,''),(4,4,10,15,3,''), -- spec ramon_A
(5,5,10,50,3,'pulley frente'),(6,5,15,20,3,''),(7,5,null,null,3,'fazer por 1 minuto'),(8,5,10,35,3,''), -- spec ramon_B
(9,6,12,null,3,''),(10,6,15,7,3,''),(11,6,10,35,3,''),(12,6,10,35,3,''), -- spec ramon_C
(13,7,null,null,null,'correr por 15 minutos'),(14,7,null,null,null,'fazer por 10 minutos'),(15,7,null,null,null,'fazer por 3 minutos'), -- spec gabriel_CORRIDA
(16,8,null,null,null,'fazer por 1 minuto'),(17,8,null,null,null,'posicionar por 4 minuto'),(18,8,null,null,3,'20 segundos'), -- spec gabriel_ALONGAMENTO
(1,9,12,15,3,''),(2,9,10,20,4,''),(3,9,12,40,3,''),(4,9,12,12,3,''); -- spec gabriel_A