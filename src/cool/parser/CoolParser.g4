parser grammar CoolParser;

options {
    tokenVocab = CoolLexer;
}

@header{
    package cool.parser;
}

program : (classes+=class SEMICOLON)+ EOF;
class : CLASS className=TYPE (INHERITS parentName=TYPE)? LBRACKET (content+=feature SEMICOLON)* RBRACKET;
feature : ID (LPARAN (params+=formal (COMMA params+=formal)*)? RPARAN) COLON TYPE LBRACKET expr RBRACKET # method
        | ID COLON TYPE (ASSN expr)?                                                                     # attribute
        ;
formal : ID COLON TYPE;
expr : caller=ID (LPARAN (params+=expr (COMMA params+=expr)*)? RPARAN)                      # call
     | caller=expr (AT TYPE)? DOT callee=ID
       (LPARAN (params+=expr (COMMA params+=expr)*)? RPARAN)                                # dispatch
     | IF cond=expr THEN thenBranch=expr ELSE elseBranch=expr FI                            # if
     | WHILE cond=expr LOOP block=expr POOL                                                 # while
     | LBRACKET (exprs+=expr SEMICOLON)+ RBRACKET                                           # block
     | LET decls+=ID COLON decls+=TYPE (decls+=ASSN values+=expr)?
       (COMMA decls+=ID COLON decls+=TYPE (decls+=ASSN values+=expr)?)*? IN block=expr      # let
     | CASE cond=expr OF (ids+=ID COLON types+=TYPE CASE_ARROW exprs+=expr SEMICOLON)+ ESAC # case
     | NEW TYPE                                                                             # new
     | NEG expr                                                                             # negate
     | ISVOID expr                                                                          # isvoid
     | left=expr MULT right=expr                                                            # multiply
     | left=expr DIV right=expr                                                             # divide
     | left=expr PLUS right=expr                                                            # plus
     | left=expr MINUS right=expr                                                           # minus
     | left=expr LESS right=expr                                                            # less
     | left=expr LESSEQ right=expr                                                          # lessEquals
     | left=expr EQ right=expr                                                              # equals
     | NOT expr                                                                             # not
     | <assoc=right> ID ASSN expr                                                           # assign
     | LPARAN expr RPARAN                                                                   # paran
     | ID                                                                                   # id
     | INT                                                                                  # int
     | STRING                                                                               # string
     | (TRUE | FALSE)                                                                       # bool
;