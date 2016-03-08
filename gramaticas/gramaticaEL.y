/******************************
** GRAMATICA CON ERROR LÉXICO**
*******************************/
%{
  int prueba;
  int prueba2;   
%}
%stat E   /*SIMBOLO DE INICIO*/
%nulo EPS  /*SIMBOLO NULO*/
%token id  /*TERMINAL "id"*/
%%
E : T J ;
J : '+' T J | EPS ;
T : F U ;
U : '*' F U | EPS ;
F : '(' E ')' | id ;
%%