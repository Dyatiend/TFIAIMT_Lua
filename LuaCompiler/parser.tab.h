/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     AND = 258,
     BREAK = 259,
     DO = 260,
     ELSE = 261,
     ELSEIF = 262,
     END = 263,
     FALSE = 264,
     FOR = 265,
     FUNCTION = 266,
     IF = 267,
     IN = 268,
     LOCAL = 269,
     NIL = 270,
     NOT = 271,
     OR = 272,
     RETURN = 273,
     REPEAT = 274,
     THEN = 275,
     TRUE = 276,
     UNTIL = 277,
     WHILE = 278,
     EQL = 279,
     NOT_EQL = 280,
     LE = 281,
     GE = 282,
     FLOOR_DIV = 283,
     CONCAT = 284,
     VAR_ARG = 285,
     STRING = 286,
     NUMBER = 287,
     IDENT = 288,
     UNARY = 289
   };
#endif
/* Tokens.  */
#define AND 258
#define BREAK 259
#define DO 260
#define ELSE 261
#define ELSEIF 262
#define END 263
#define FALSE 264
#define FOR 265
#define FUNCTION 266
#define IF 267
#define IN 268
#define LOCAL 269
#define NIL 270
#define NOT 271
#define OR 272
#define RETURN 273
#define REPEAT 274
#define THEN 275
#define TRUE 276
#define UNTIL 277
#define WHILE 278
#define EQL 279
#define NOT_EQL 280
#define LE 281
#define GE 282
#define FLOOR_DIV 283
#define CONCAT 284
#define VAR_ARG 285
#define STRING 286
#define NUMBER 287
#define IDENT 288
#define UNARY 289




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
#line 17 "parser.y"
{
    double number;
    char * ident;
    char * string;

    struct chunk_node * chunk_node;

    struct stmt_node * stmt_node;
    struct stmt_seq_node * stmt_seq_node;

    struct expr_node * expr_node;
    struct expr_seq_node * expr_seq_node;

    struct ident_list_node * ident_list_node;
    struct param_list_node * param_list_node;

    struct field_node * field_node;
    struct field_list_node * field_list_node;

    struct var_node * var_node;
    
    char * field_sep_node;
}
/* Line 1529 of yacc.c.  */
#line 141 "parser.tab.h"
	YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

