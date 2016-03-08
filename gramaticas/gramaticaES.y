/***********************************
** GRAMATICA CON ERROR SINT�CTICO **
***********************************/
%{
  int prueba;
  int prueba2;   
%}
%start E   /*SIMBOLO DE INICIO*/
%nulo EPS  /*SIMBOLO NULO*/
%token id  /*TERMINAL "id"*/
%%
E : T J ;
J : '+' T J | EPS ;
T : F U ;
U : '*' F U | EPS 
F : '(' E ')' | id ;
%%