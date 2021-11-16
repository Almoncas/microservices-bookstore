
INSERT INTO editorial(id,name) VALUES
	(editorial_sequence.nextval,'Editorial1'),
	(editorial_sequence.nextval,'Editorial2'),
	(editorial_sequence.nextval,'Editorial3');

INSERT INTO book(id,title,author,publish,paginas,descripcion,editorial) VALUES
	(book_sequence.nextval,'Libro1','Autor1',TO_DATE('01/01/2021', 'DD/MM/YYYY'),100,'Descripcion 1 del libro 1',1),
	(book_sequence.nextval,'Libro2','Autor2',TO_DATE('25/01/2021', 'DD/MM/YYYY'),250,'Descripcion 2 del libro 2',2);

