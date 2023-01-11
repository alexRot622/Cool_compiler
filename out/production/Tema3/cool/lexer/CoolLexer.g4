lexer grammar CoolLexer;

tokens { ERROR }

@header{
    package cool.lexer;
}

@members{
    private void raiseError(String msg) {
        setText(msg);
        setType(ERROR);
    }

    private void trimQuotes() {
        setText(getText().substring(1, getText().length()));
        setText(getText().substring(0, getText().length()-1));
    }

    private void escapeChars() {
        StringBuilder builder = new StringBuilder();
        boolean escaped = false;

        for (char c: getText().toCharArray()) {
            if (!escaped) {
                if (c != '\\') builder.append(c);
                else escaped = true;
            }
            else {
                switch (c) {
                    case 'n':
                        builder.append("\n");
                        break;
                    case 't':
                        builder.append("\t");
                        break;
                    case 'b':
                        builder.append("\b");
                        break;
                    case 'f':
                        builder.append("\f");
                        break;
                    default:
                        builder.append(c);
                }
                escaped = false;
            }
        }

        setText(builder.toString());
    }
}

// Fragments used for implementing case insensitivity.
fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];

/* KEYWORDS
   all keywords except 'true' and 'false' are case insensitive;
   for 'true' and 'false' the first letter must be lowercase.
*/
CLASS : C L A S S;
ELSE : E L S E;
FALSE : 'f' A L S E;
FI : F I;
IF : I F;
IN : I N;
INHERITS : I N H E R I T S;
ISVOID : I S V O I D;
LET : L E T;
LOOP : L O O P;
POOL : P O O L;
THEN : T H E N;
WHILE : W H I L E;
CASE : C A S E;
ESAC : E S A C;
NEW : N E W;
OF : O F;
NOT : N O T;
TRUE : 't' R U E;

DOT : '.';
AT : '@';
LBRACKET : '{';
RBRACKET : '}';
SEMICOLON : ';';
COLON : ':';
COMMA : ',';
CASE_ARROW : '=>';

// Operators
ASSN : '<-';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';
NEG : '~';
LESS : '<';
LESSEQ : '<=';
EQ : '=';
LPARAN : '(';
RPARAN : ')';

// Integer
fragment DIGIT : [0-9];
INT : DIGIT+;

// Identifiers and types
fragment LOWERCASE : [a-z];
fragment UPPERCASE : [A-Z];
fragment LETTER: LOWERCASE | UPPERCASE;
ID : (LOWERCASE | '_')(LETTER | '_' | DIGIT)*;
TYPE : UPPERCASE(LETTER | '_' | DIGIT)*;

STRING : '"' ('\u0000' { raiseError("String contains null character"); }
              | '\\\n'
              | '\\\r\n'
              | .)*?
              ( '"'
              | '\n' { raiseError("Unterminated string constant"); }
              | EOF  { raiseError("EOF in string constant"); })
              { if (getType() != ERROR) {
                    trimQuotes();
                    escapeChars();
                    if(getText().length() > 1024) raiseError("String constant too long");
                }
              };

ONE_LINE_COMMENT : '--' .*? '\n' -> skip;
BLOCK_COMMENT : '(*' (BLOCK_COMMENT | .)*? ('*)' { skip(); } | EOF { raiseError("EOF in comment"); });

ERROR : '*)' { raiseError("Unmatched *)"); };

WS : [ \n\f\r\t]+ -> skip;

ANY : . { raiseError("Invalid character: " + getText()); };