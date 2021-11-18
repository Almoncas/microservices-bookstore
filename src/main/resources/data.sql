
INSERT INTO editorial(id,name) VALUES
	(editorial_sequence.nextval,'Editorial1'),
	(editorial_sequence.nextval,'Editorial2'),
	(editorial_sequence.nextval,'Editorial3');

INSERT INTO book(id,title,author,publish,pages,description,editorial_id) VALUES
	(book_sequence.nextval,'Libro1','Autor1',TO_DATE('01/01/2021', 'DD/MM/YYYY'),100,'Descripcion 1 del libro 1',1),
	(book_sequence.nextval,'Libro2','Autor2',TO_DATE('25/01/2021', 'DD/MM/YYYY'),250,'Descripcion 2 del libro 2',2),
	(book_sequence.nextval,'Libro3','Autor3',TO_DATE('10/12/2021', 'DD/MM/YYYY'),300,'Descripcion 3 del libro 3',1);

