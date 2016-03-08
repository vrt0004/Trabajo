 /*  grammar  for  the  input  to  Yacc  */

      /*  basic  entities  */
%token      IDENTIFIER  /*   includes  identifiers   and  literals  */
%token      C_IDENTIFIER      /*    identifier  (but  not  literal)  followed  by  colon    */
%token      NUMBER            /*    [0-9]+    */

      /*  reserved  words:    %type  =>  TYPE,  %left  =>  LEFT,  etc.  */

%token      LEFT  RIGHT  NONASSOC  TOKEN  PREC  TYPE  START  UNION

%token      MARK  /*  the  %%  mark  */
%token      LCURL /*  the  %{  mark  */
%token      RCURL /*  the  %}  mark  */

      /*  ascii  character  literals  stand  for  themselves  */

%start      spec

%%

spec  :     defs  MARK  rules  tail
      ;

tail  :     MARK  {    In  this  action,  eat  up  the  rest  of  the  file    }
      |     /*  empty:  the  second  MARK  is  optional  */
      ;

defs  :     /*  empty  */
      |     defs  def
      ;

def   :     START  IDENTIFIER
      |     UNION  {  Copy union  definition  to  output  }
      |     LCURL  {  Copy  C  code  to  output  file   }  RCURL
      |     ndefs  rword  tag  nlist
      ;

rword :     TOKEN
      |     LEFT
      |     RIGHT
      |     NONASSOC
      |     TYPE
      ;

tag   :     /*  empty:  union  tag  is  optional  */
      |     '<'  IDENTIFIER  '>'
      ;

nlist :     nmno
      |     nlist  nmno
      |     nlist  ','  nmno
      ;

nmno  :     IDENTIFIER        /*  NOTE:  literal  illegal  with  %type  */
      |     IDENTIFIER  NUMBER      /*  NOTE:  illegal  with  %type  */
      ;

      /*  rules  section  */

rules :     C_IDENTIFIER  rbody  prec
      |     rules  rule
      ;

rule  :     C_IDENTIFIER  rbody  prec
      |     '|'  rbody  prec
      ;

rbody :     /*  empty  */
      |     rbody  IDENTIFIER
      |     rbody  act
      ;

act   :     '{'  {  Copy  action,  translate  $$,  etc.  }  '}'
      ;

prec  :     /*  empty  */
      |     PREC  IDENTIFIER
      |     PREC  IDENTIFIER  act
      |     prec  ';'
      ;
%%