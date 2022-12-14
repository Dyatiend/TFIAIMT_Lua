/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton implementation for Bison's Yacc-like parsers in C

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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.3"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Using locations.  */
#define YYLSP_NEEDED 0



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
     INT_NUMBER = 287,
     FLOAT_NUMBER = 288,
     IDENT = 289,
     UNARY = 290
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
#define INT_NUMBER 287
#define FLOAT_NUMBER 288
#define IDENT 289
#define UNARY 290




/* Copy the first part of user declarations.  */
#line 1 "parser.y"

    #include <stdio.h>
    #include "print_tree.h"
    #include "create_nodes.h"

    void yyerror(const char * message) {
        fprintf(stderr, "%s", message);
    }

    int yylex();
    int yyparse();
    extern FILE* yyin;

    struct chunk_node * chunk_node;


/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Enabling the token table.  */
#ifndef YYTOKEN_TABLE
# define YYTOKEN_TABLE 0
#endif

#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
#line 17 "parser.y"
{
    int int_number;
    double float_number;
    char * ident;
    char * string;

    struct chunk_node * _chunk_node;

    struct stmt_node * _stmt_node;
    struct stmt_seq_node * _stmt_seq_node;

    struct expr_node * _expr_node;
    struct expr_seq_node * _expr_seq_node;

    struct ident_list_node * _ident_list_node;
    struct param_list_node * _param_list_node;

    struct field_node * _field_node;
    struct field_list_node * _field_list_node;

    struct var_node * _var_node;
    
    char * _field_sep_node;
}
/* Line 193 of yacc.c.  */
#line 207 "parser.tab.c"
	YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif



/* Copy the second part of user declarations.  */


/* Line 216 of yacc.c.  */
#line 220 "parser.tab.c"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(msgid) dgettext ("bison-runtime", msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(msgid) msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(e) ((void) (e))
#else
# define YYUSE(e) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(n) (n)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int i)
#else
static int
YYID (i)
    int i;
#endif
{
  return i;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#     ifndef _STDLIB_H
#      define _STDLIB_H 1
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined _STDLIB_H \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef _STDLIB_H
#    define _STDLIB_H 1
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss;
  YYSTYPE yyvs;
  };

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  YYSIZE_T yyi;				\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (YYID (0))
#  endif
# endif

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack)					\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack, Stack, yysize);				\
	Stack = &yyptr->Stack;						\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  4
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   775

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  58
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  19
/* YYNRULES -- Number of rules.  */
#define YYNRULES  93
/* YYNRULES -- Number of states.  */
#define YYNSTATES  197

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   290

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,    55,     2,    46,    41,     2,
      51,    49,    44,    42,    35,    43,    54,    45,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    36,
      38,    50,    37,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    52,     2,    53,    48,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    56,    39,    57,    40,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      47
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint16 yyprhs[] =
{
       0,     0,     3,     5,     7,    10,    14,    15,    18,    21,
      25,    27,    29,    33,    39,    44,    51,    60,    70,    82,
      90,    98,   107,   110,   115,   116,   122,   124,   127,   129,
     133,   135,   140,   144,   149,   153,   160,   166,   169,   171,
     175,   177,   181,   183,   185,   187,   189,   191,   193,   195,
     197,   199,   203,   205,   209,   213,   217,   221,   225,   229,
     233,   237,   241,   245,   249,   253,   257,   261,   265,   269,
     273,   277,   281,   284,   287,   290,   293,   296,   300,   302,
     304,   305,   307,   311,   313,   316,   320,   325,   327,   331,
     335,   341,   343,   345
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int8 yyrhs[] =
{
      59,     0,    -1,    60,    -1,    61,    -1,    61,    64,    -1,
      61,    64,    36,    -1,    -1,    61,    62,    -1,    61,    36,
      -1,    65,    50,    69,    -1,    67,    -1,     4,    -1,     5,
      60,     8,    -1,    23,    70,     5,    60,     8,    -1,    19,
      60,    22,    70,    -1,    12,    70,    20,    60,    63,     8,
      -1,    12,    70,    20,    60,    63,     6,    60,     8,    -1,
      10,    34,    50,    70,    35,    70,     5,    60,     8,    -1,
      10,    34,    50,    70,    35,    70,    35,    70,     5,    60,
       8,    -1,    10,    68,    13,    69,     5,    60,     8,    -1,
      11,    34,    51,    72,    49,    60,     8,    -1,    14,    11,
      34,    51,    72,    49,    60,     8,    -1,    14,    68,    -1,
      14,    68,    50,    69,    -1,    -1,    63,     7,    70,    20,
      60,    -1,    18,    -1,    18,    69,    -1,    66,    -1,    65,
      35,    66,    -1,    34,    -1,    66,    52,    70,    53,    -1,
      66,    54,    34,    -1,    67,    52,    70,    53,    -1,    67,
      54,    34,    -1,    51,    70,    49,    52,    70,    53,    -1,
      51,    70,    49,    54,    34,    -1,    34,    71,    -1,    34,
      -1,    68,    35,    34,    -1,    70,    -1,    69,    35,    70,
      -1,    15,    -1,     9,    -1,    21,    -1,    32,    -1,    33,
      -1,    31,    -1,    30,    -1,    66,    -1,    67,    -1,    51,
      70,    49,    -1,    73,    -1,    70,    43,    70,    -1,    70,
      44,    70,    -1,    70,    42,    70,    -1,    70,    45,    70,
      -1,    70,    28,    70,    -1,    70,    48,    70,    -1,    70,
      46,    70,    -1,    70,    41,    70,    -1,    70,    40,    70,
      -1,    70,    39,    70,    -1,    70,    29,    70,    -1,    70,
      38,    70,    -1,    70,    26,    70,    -1,    70,    37,    70,
      -1,    70,    27,    70,    -1,    70,    24,    70,    -1,    70,
      25,    70,    -1,    70,     3,    70,    -1,    70,    17,    70,
      -1,    43,    70,    -1,    16,    70,    -1,    55,    70,    -1,
      40,    70,    -1,    51,    49,    -1,    51,    69,    49,    -1,
      73,    -1,    31,    -1,    -1,    68,    -1,    68,    35,    30,
      -1,    30,    -1,    56,    57,    -1,    56,    74,    57,    -1,
      56,    74,    76,    57,    -1,    75,    -1,    74,    76,    75,
      -1,    34,    50,    70,    -1,    52,    70,    53,    50,    70,
      -1,    70,    -1,    35,    -1,    36,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint8 yyrline[] =
{
       0,    90,    90,    94,    95,    96,    99,   100,   101,   122,
     123,   124,   125,   126,   127,   128,   129,   130,   131,   132,
     133,   134,   135,   136,   139,   140,   145,   146,   150,   151,
     155,   156,   157,   158,   159,   160,   161,   164,   168,   169,
     173,   174,   181,   182,   183,   184,   185,   186,   187,   188,
     189,   190,   191,   192,   193,   194,   195,   196,   197,   198,
     199,   200,   201,   202,   203,   204,   205,   206,   207,   208,
     209,   210,   211,   212,   213,   214,   218,   219,   220,   221,
     225,   226,   227,   228,   232,   233,   234,   238,   239,   243,
     244,   245,   249,   250
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || YYTOKEN_TABLE
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "AND", "BREAK", "DO", "ELSE", "ELSEIF",
  "END", "FALSE", "FOR", "FUNCTION", "IF", "IN", "LOCAL", "NIL", "NOT",
  "OR", "RETURN", "REPEAT", "THEN", "TRUE", "UNTIL", "WHILE", "EQL",
  "NOT_EQL", "LE", "GE", "FLOOR_DIV", "CONCAT", "VAR_ARG", "STRING",
  "INT_NUMBER", "FLOAT_NUMBER", "IDENT", "','", "';'", "'>'", "'<'", "'|'",
  "'~'", "'&'", "'+'", "'-'", "'*'", "'/'", "'%'", "UNARY", "'^'", "')'",
  "'='", "'('", "'['", "']'", "'.'", "'#'", "'{'", "'}'", "$accept",
  "chunk", "block", "block_tmp", "stmt", "elseif_seq", "ret_stmt",
  "var_list", "var", "function_call", "ident_list", "expr_seq", "expr",
  "args", "param_list", "table_constructor", "field_list", "field",
  "field_sep", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,    44,    59,    62,    60,   124,
     126,    38,    43,    45,    42,    47,    37,   290,    94,    41,
      61,    40,    91,    93,    46,    35,   123,   125
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    58,    59,    60,    60,    60,    61,    61,    61,    62,
      62,    62,    62,    62,    62,    62,    62,    62,    62,    62,
      62,    62,    62,    62,    63,    63,    64,    64,    65,    65,
      66,    66,    66,    66,    66,    66,    66,    67,    68,    68,
      69,    69,    70,    70,    70,    70,    70,    70,    70,    70,
      70,    70,    70,    70,    70,    70,    70,    70,    70,    70,
      70,    70,    70,    70,    70,    70,    70,    70,    70,    70,
      70,    70,    70,    70,    70,    70,    71,    71,    71,    71,
      72,    72,    72,    72,    73,    73,    73,    74,    74,    75,
      75,    75,    76,    76
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     1,     1,     2,     3,     0,     2,     2,     3,
       1,     1,     3,     5,     4,     6,     8,     9,    11,     7,
       7,     8,     2,     4,     0,     5,     1,     2,     1,     3,
       1,     4,     3,     4,     3,     6,     5,     2,     1,     3,
       1,     3,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     3,     1,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     2,     2,     2,     2,     2,     3,     1,     1,
       0,     1,     3,     1,     2,     3,     4,     1,     3,     3,
       5,     1,     1,     1
};

/* YYDEFACT[STATE-NAME] -- Default rule to reduce with in state
   STATE-NUM when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       6,     0,     2,     3,     1,    11,     6,     0,     0,     0,
       0,    26,     6,     0,    30,     8,     0,     7,     4,     0,
      28,    10,     0,    38,     0,     0,    43,    42,     0,    44,
      48,    47,    45,    46,     0,     0,     0,     0,     0,    49,
      50,     0,    52,     0,    38,    22,    27,    40,     0,     0,
      79,     0,    37,    78,     0,     5,     0,     0,     0,     0,
       0,     0,    12,     0,     0,     0,    80,    73,    75,    72,
       0,    74,    30,     0,    84,    91,     0,    87,     0,     0,
       6,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     6,    76,     0,     0,    29,     0,     9,     0,
      32,     0,    34,     0,     0,    39,    83,    81,     0,    51,
       0,     0,    92,    93,    85,     0,    70,    71,    24,    68,
      69,    65,    67,    57,    63,    66,    64,    62,    61,    60,
      55,    53,    54,    56,    59,    58,    80,    23,    41,    14,
       0,    77,     0,     0,    31,    33,     0,     6,     0,     6,
      89,     0,    86,    88,     0,     0,    13,     0,    36,     0,
       0,    82,     0,     0,     6,     0,    15,     6,    35,     6,
       0,    19,    20,    90,     0,     0,     0,     0,     0,    16,
       6,    21,    17,     6,    25,     0,    18
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     1,     2,     3,    17,   164,    18,    19,    39,    40,
     117,    46,    47,    52,   118,    42,    76,    77,   125
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -49
static const yytype_int16 yypact[] =
{
     -49,    12,   -49,    16,   -49,   -49,   -49,   -30,   -29,   365,
       3,   365,   -49,   365,    -7,   -49,   365,   -49,   -23,   -25,
      23,    27,    30,    32,    10,    -8,   -49,   -49,   365,   -49,
     -49,   -49,   -49,   -49,   365,   365,   365,   365,   126,    23,
      27,   475,   -49,    60,   -49,    41,    65,   625,    96,   505,
     -49,   336,   -49,   -49,   385,   -49,    19,   365,   365,    85,
     365,    91,   -49,   365,   365,    92,   -19,    79,    79,    79,
     415,    79,    -9,   365,   -49,   625,   -17,   -49,   365,   365,
     -49,   365,   365,   365,   365,   365,   365,   365,   365,   365,
     365,   365,   365,   365,   365,   365,   365,   365,    77,   365,
     365,   365,   -49,   -49,   -18,    43,    23,    27,    65,   177,
     -49,   207,   -49,   535,    -3,   -49,   -49,    94,    81,    43,
     365,   237,   -49,   -49,   -49,   307,   677,   652,   -49,   698,
     698,   698,   698,    79,   727,   698,   698,    44,   706,   727,
      18,    18,    79,    79,    79,    79,   -19,    65,   625,   625,
     123,   -49,   365,    98,   -49,   -49,   365,   -49,    35,   -49,
     625,    83,   -49,   -49,   116,    87,   -49,   267,   -49,   445,
     129,   -49,   130,   365,   -49,   365,   -49,   -49,   -49,   -49,
     365,   -49,   -49,   625,   131,   565,   135,   136,   595,   -49,
     -49,   -49,   -49,   -49,   -49,   138,   -49
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int8 yypgoto[] =
{
     -49,   -49,    -6,   -49,   -49,   -49,   -49,   -49,     4,     5,
      61,   -48,    20,   -49,   -12,   -13,   -49,    24,   -49
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If zero, do what YYDEFACT says.
   If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -1
static const yytype_uint8 yytable[] =
{
      22,    53,   157,   104,    23,    25,    48,    20,    21,   108,
      56,   116,     4,    55,    43,    44,   114,   100,   122,   123,
       5,     6,    50,    64,    50,    57,     7,     8,     9,    41,
      10,   151,   100,    49,    11,    12,    54,    44,    62,    13,
     124,   120,    51,    66,    51,    65,    85,    38,    67,    38,
      14,   147,    15,    14,    68,    69,    70,    71,    75,    53,
     106,   107,    94,    95,    96,   171,    97,    16,    24,   115,
      16,    45,    85,    86,   128,    58,    65,    59,   109,    60,
     111,    61,    63,   113,    90,    91,    92,    93,    94,    95,
      96,    99,    97,   121,    98,   152,   150,   153,   126,   127,
     100,   129,   130,   131,   132,   133,   134,   135,   136,   137,
     138,   139,   140,   141,   142,   143,   144,   145,   101,   110,
     148,   149,   174,   175,   176,   112,   115,    97,   146,   158,
     159,   166,   168,   173,   165,    26,   177,   181,   182,   189,
     160,    27,    28,   191,   192,    75,   196,    29,     0,   163,
       0,   170,     0,   172,     0,     0,    30,    31,    32,    33,
      72,     0,     0,     0,     0,     0,    34,     0,   184,    35,
       0,   186,   167,   187,     0,     0,   169,    36,    73,     0,
      78,    37,    38,    74,   194,     0,     0,   195,     0,     0,
       0,     0,     0,   183,    79,   185,     0,     0,     0,     0,
     188,    81,    82,    83,    84,    85,    86,     0,     0,     0,
      78,     0,     0,     0,    87,    88,    89,    90,    91,    92,
      93,    94,    95,    96,    79,    97,     0,     0,     0,     0,
     154,    81,    82,    83,    84,    85,    86,     0,     0,     0,
      78,     0,     0,     0,    87,    88,    89,    90,    91,    92,
      93,    94,    95,    96,    79,    97,     0,     0,     0,     0,
     155,    81,    82,    83,    84,    85,    86,     0,     0,     0,
      78,     0,     0,     0,    87,    88,    89,    90,    91,    92,
      93,    94,    95,    96,    79,    97,     0,     0,     0,     0,
     161,    81,    82,    83,    84,    85,    86,     0,     0,     0,
       0,     0,     0,     0,    87,    88,    89,    90,    91,    92,
      93,    94,    95,    96,     0,    97,    26,     0,     0,     0,
     178,     0,    27,    28,     0,     0,     0,     0,    29,     0,
       0,     0,     0,     0,     0,     0,     0,    30,    31,    32,
      33,    72,     0,     0,     0,    26,     0,    34,     0,     0,
      35,    27,    28,     0,     0,     0,     0,    29,    36,    73,
       0,     0,    37,    38,   162,     0,    30,    31,    32,    33,
      14,     0,     0,     0,    26,     0,    34,     0,     0,    35,
      27,    28,     0,     0,     0,   103,    29,    36,    78,     0,
       0,    37,    38,     0,     0,    30,    31,    32,    33,    14,
       0,     0,    79,     0,     0,    34,     0,     0,    35,    81,
      82,    83,    84,    85,    86,     0,    36,     0,    78,     0,
      37,    38,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    79,    97,   105,     0,     0,     0,     0,    81,
      82,    83,    84,    85,    86,     0,     0,     0,    78,     0,
     179,     0,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    79,    97,   119,     0,     0,     0,     0,    81,
      82,    83,    84,    85,    86,     0,     0,     0,    78,     0,
     180,     0,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    79,    97,     0,    80,     0,     0,     0,    81,
      82,    83,    84,    85,    86,     0,     0,     0,    78,     0,
     102,     0,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    79,    97,     0,     0,     0,     0,     0,    81,
      82,    83,    84,    85,    86,     0,     0,     0,    78,     0,
       0,     0,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    79,    97,     0,     0,     0,     0,     0,    81,
      82,    83,    84,    85,    86,     0,     0,     0,    78,     0,
     156,     0,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    79,    97,     0,   190,     0,     0,     0,    81,
      82,    83,    84,    85,    86,     0,     0,     0,    78,     0,
     193,     0,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    79,    97,     0,     0,     0,     0,     0,    81,
      82,    83,    84,    85,    86,     0,     0,     0,    78,     0,
       0,     0,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    79,    97,     0,     0,     0,     0,     0,    81,
      82,    83,    84,    85,    86,    78,     0,     0,     0,     0,
       0,     0,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,     0,    97,     0,     0,    81,    82,    83,    84,
      85,    86,     0,     0,     0,     0,     0,     0,     0,    87,
      88,    89,    90,    91,    92,    93,    94,    95,    96,     0,
      97,    81,    82,    83,    84,    85,    86,     0,     0,     0,
       0,     0,     0,     0,    87,    88,    89,    90,    91,    92,
      93,    94,    95,    96,     0,    97,    85,    86,     0,     0,
       0,     0,     0,     0,    85,    86,     0,    89,    90,    91,
      92,    93,    94,    95,    96,     0,    97,    91,    92,    93,
      94,    95,    96,     0,    97,    85,    86,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    92,
      93,    94,    95,    96,     0,    97
};

static const yytype_int16 yycheck[] =
{
       6,    14,     5,    51,    34,    34,    12,     3,     3,    57,
      35,    30,     0,    36,    11,    34,    64,    35,    35,    36,
       4,     5,    31,    13,    31,    50,    10,    11,    12,     9,
      14,    49,    35,    13,    18,    19,    16,    34,     8,    23,
      57,    50,    51,    51,    51,    35,    28,    56,    28,    56,
      34,    99,    36,    34,    34,    35,    36,    37,    38,    72,
      56,    56,    44,    45,    46,    30,    48,    51,     7,    34,
      51,    10,    28,    29,    80,    52,    35,    54,    58,    52,
      60,    54,    50,    63,    40,    41,    42,    43,    44,    45,
      46,    50,    48,    73,    34,    52,   102,    54,    78,    79,
      35,    81,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    91,    92,    93,    94,    95,    96,    97,    22,    34,
     100,   101,     6,     7,     8,    34,    34,    48,    51,    35,
      49,     8,    34,    50,   146,     9,    49,     8,     8,     8,
     120,    15,    16,     8,     8,   125,     8,    21,    -1,   125,
      -1,   157,    -1,   159,    -1,    -1,    30,    31,    32,    33,
      34,    -1,    -1,    -1,    -1,    -1,    40,    -1,   174,    43,
      -1,   177,   152,   179,    -1,    -1,   156,    51,    52,    -1,
       3,    55,    56,    57,   190,    -1,    -1,   193,    -1,    -1,
      -1,    -1,    -1,   173,    17,   175,    -1,    -1,    -1,    -1,
     180,    24,    25,    26,    27,    28,    29,    -1,    -1,    -1,
       3,    -1,    -1,    -1,    37,    38,    39,    40,    41,    42,
      43,    44,    45,    46,    17,    48,    -1,    -1,    -1,    -1,
      53,    24,    25,    26,    27,    28,    29,    -1,    -1,    -1,
       3,    -1,    -1,    -1,    37,    38,    39,    40,    41,    42,
      43,    44,    45,    46,    17,    48,    -1,    -1,    -1,    -1,
      53,    24,    25,    26,    27,    28,    29,    -1,    -1,    -1,
       3,    -1,    -1,    -1,    37,    38,    39,    40,    41,    42,
      43,    44,    45,    46,    17,    48,    -1,    -1,    -1,    -1,
      53,    24,    25,    26,    27,    28,    29,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    37,    38,    39,    40,    41,    42,
      43,    44,    45,    46,    -1,    48,     9,    -1,    -1,    -1,
      53,    -1,    15,    16,    -1,    -1,    -1,    -1,    21,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    30,    31,    32,
      33,    34,    -1,    -1,    -1,     9,    -1,    40,    -1,    -1,
      43,    15,    16,    -1,    -1,    -1,    -1,    21,    51,    52,
      -1,    -1,    55,    56,    57,    -1,    30,    31,    32,    33,
      34,    -1,    -1,    -1,     9,    -1,    40,    -1,    -1,    43,
      15,    16,    -1,    -1,    -1,    49,    21,    51,     3,    -1,
      -1,    55,    56,    -1,    -1,    30,    31,    32,    33,    34,
      -1,    -1,    17,    -1,    -1,    40,    -1,    -1,    43,    24,
      25,    26,    27,    28,    29,    -1,    51,    -1,     3,    -1,
      55,    56,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    17,    48,    49,    -1,    -1,    -1,    -1,    24,
      25,    26,    27,    28,    29,    -1,    -1,    -1,     3,    -1,
       5,    -1,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    17,    48,    49,    -1,    -1,    -1,    -1,    24,
      25,    26,    27,    28,    29,    -1,    -1,    -1,     3,    -1,
      35,    -1,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    17,    48,    -1,    20,    -1,    -1,    -1,    24,
      25,    26,    27,    28,    29,    -1,    -1,    -1,     3,    -1,
       5,    -1,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    17,    48,    -1,    -1,    -1,    -1,    -1,    24,
      25,    26,    27,    28,    29,    -1,    -1,    -1,     3,    -1,
      -1,    -1,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    17,    48,    -1,    -1,    -1,    -1,    -1,    24,
      25,    26,    27,    28,    29,    -1,    -1,    -1,     3,    -1,
      35,    -1,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    17,    48,    -1,    20,    -1,    -1,    -1,    24,
      25,    26,    27,    28,    29,    -1,    -1,    -1,     3,    -1,
       5,    -1,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    17,    48,    -1,    -1,    -1,    -1,    -1,    24,
      25,    26,    27,    28,    29,    -1,    -1,    -1,     3,    -1,
      -1,    -1,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    17,    48,    -1,    -1,    -1,    -1,    -1,    24,
      25,    26,    27,    28,    29,     3,    -1,    -1,    -1,    -1,
      -1,    -1,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    -1,    48,    -1,    -1,    24,    25,    26,    27,
      28,    29,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
      38,    39,    40,    41,    42,    43,    44,    45,    46,    -1,
      48,    24,    25,    26,    27,    28,    29,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    37,    38,    39,    40,    41,    42,
      43,    44,    45,    46,    -1,    48,    28,    29,    -1,    -1,
      -1,    -1,    -1,    -1,    28,    29,    -1,    39,    40,    41,
      42,    43,    44,    45,    46,    -1,    48,    41,    42,    43,
      44,    45,    46,    -1,    48,    28,    29,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    42,
      43,    44,    45,    46,    -1,    48
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,    59,    60,    61,     0,     4,     5,    10,    11,    12,
      14,    18,    19,    23,    34,    36,    51,    62,    64,    65,
      66,    67,    60,    34,    68,    34,     9,    15,    16,    21,
      30,    31,    32,    33,    40,    43,    51,    55,    56,    66,
      67,    70,    73,    11,    34,    68,    69,    70,    60,    70,
      31,    51,    71,    73,    70,    36,    35,    50,    52,    54,
      52,    54,     8,    50,    13,    35,    51,    70,    70,    70,
      70,    70,    34,    52,    57,    70,    74,    75,     3,    17,
      20,    24,    25,    26,    27,    28,    29,    37,    38,    39,
      40,    41,    42,    43,    44,    45,    46,    48,    34,    50,
      35,    22,     5,    49,    69,    49,    66,    67,    69,    70,
      34,    70,    34,    70,    69,    34,    30,    68,    72,    49,
      50,    70,    35,    36,    57,    76,    70,    70,    60,    70,
      70,    70,    70,    70,    70,    70,    70,    70,    70,    70,
      70,    70,    70,    70,    70,    70,    51,    69,    70,    70,
      60,    49,    52,    54,    53,    53,    35,     5,    35,    49,
      70,    53,    57,    75,    63,    72,     8,    70,    34,    70,
      60,    30,    60,    50,     6,     7,     8,    49,    53,     5,
      35,     8,     8,    70,    60,    70,    60,    60,    70,     8,
      20,     8,     8,     5,    60,    60,     8
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  */

#define YYFAIL		goto yyerrlab

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      yytoken = YYTRANSLATE (yychar);				\
      YYPOPSTACK (1);						\
      goto yybackup;						\
    }								\
  else								\
    {								\
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))


#define YYTERROR	1
#define YYERRCODE	256


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#define YYRHSLOC(Rhs, K) ((Rhs)[K])
#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)				\
    do									\
      if (YYID (N))                                                    \
	{								\
	  (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;	\
	  (Current).first_column = YYRHSLOC (Rhs, 1).first_column;	\
	  (Current).last_line    = YYRHSLOC (Rhs, N).last_line;		\
	  (Current).last_column  = YYRHSLOC (Rhs, N).last_column;	\
	}								\
      else								\
	{								\
	  (Current).first_line   = (Current).last_line   =		\
	    YYRHSLOC (Rhs, 0).last_line;				\
	  (Current).first_column = (Current).last_column =		\
	    YYRHSLOC (Rhs, 0).last_column;				\
	}								\
    while (YYID (0))
#endif


/* YY_LOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

#ifndef YY_LOCATION_PRINT
# if defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL
#  define YY_LOCATION_PRINT(File, Loc)			\
     fprintf (File, "%d.%d-%d.%d",			\
	      (Loc).first_line, (Loc).first_column,	\
	      (Loc).last_line,  (Loc).last_column)
# else
#  define YY_LOCATION_PRINT(File, Loc) ((void) 0)
# endif
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (YYLEX_PARAM)
#else
# define YYLEX yylex ()
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  switch (yytype)
    {
      default:
	break;
    }
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *bottom, yytype_int16 *top)
#else
static void
yy_stack_print (bottom, top)
    yytype_int16 *bottom;
    yytype_int16 *top;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; bottom <= top; ++bottom)
    YYFPRINTF (stderr, " %d", *bottom);
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, int yyrule)
#else
static void
yy_reduce_print (yyvsp, yyrule)
    YYSTYPE *yyvsp;
    int yyrule;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      fprintf (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       		       );
      fprintf (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, Rule); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif



#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into YYRESULT an error message about the unexpected token
   YYCHAR while in state YYSTATE.  Return the number of bytes copied,
   including the terminating null byte.  If YYRESULT is null, do not
   copy anything; just return the number of bytes that would be
   copied.  As a special case, return 0 if an ordinary "syntax error"
   message will do.  Return YYSIZE_MAXIMUM if overflow occurs during
   size calculation.  */
static YYSIZE_T
yysyntax_error (char *yyresult, int yystate, int yychar)
{
  int yyn = yypact[yystate];

  if (! (YYPACT_NINF < yyn && yyn <= YYLAST))
    return 0;
  else
    {
      int yytype = YYTRANSLATE (yychar);
      YYSIZE_T yysize0 = yytnamerr (0, yytname[yytype]);
      YYSIZE_T yysize = yysize0;
      YYSIZE_T yysize1;
      int yysize_overflow = 0;
      enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
      char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
      int yyx;

# if 0
      /* This is so xgettext sees the translatable formats that are
	 constructed on the fly.  */
      YY_("syntax error, unexpected %s");
      YY_("syntax error, unexpected %s, expecting %s");
      YY_("syntax error, unexpected %s, expecting %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s");
# endif
      char *yyfmt;
      char const *yyf;
      static char const yyunexpected[] = "syntax error, unexpected %s";
      static char const yyexpecting[] = ", expecting %s";
      static char const yyor[] = " or %s";
      char yyformat[sizeof yyunexpected
		    + sizeof yyexpecting - 1
		    + ((YYERROR_VERBOSE_ARGS_MAXIMUM - 2)
		       * (sizeof yyor - 1))];
      char const *yyprefix = yyexpecting;

      /* Start YYX at -YYN if negative to avoid negative indexes in
	 YYCHECK.  */
      int yyxbegin = yyn < 0 ? -yyn : 0;

      /* Stay within bounds of both yycheck and yytname.  */
      int yychecklim = YYLAST - yyn + 1;
      int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
      int yycount = 1;

      yyarg[0] = yytname[yytype];
      yyfmt = yystpcpy (yyformat, yyunexpected);

      for (yyx = yyxbegin; yyx < yyxend; ++yyx)
	if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR)
	  {
	    if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
	      {
		yycount = 1;
		yysize = yysize0;
		yyformat[sizeof yyunexpected - 1] = '\0';
		break;
	      }
	    yyarg[yycount++] = yytname[yyx];
	    yysize1 = yysize + yytnamerr (0, yytname[yyx]);
	    yysize_overflow |= (yysize1 < yysize);
	    yysize = yysize1;
	    yyfmt = yystpcpy (yyfmt, yyprefix);
	    yyprefix = yyor;
	  }

      yyf = YY_(yyformat);
      yysize1 = yysize + yystrlen (yyf);
      yysize_overflow |= (yysize1 < yysize);
      yysize = yysize1;

      if (yysize_overflow)
	return YYSIZE_MAXIMUM;

      if (yyresult)
	{
	  /* Avoid sprintf, as that infringes on the user's name space.
	     Don't have undefined behavior even if the translation
	     produced a string with the wrong number of "%s"s.  */
	  char *yyp = yyresult;
	  int yyi = 0;
	  while ((*yyp = *yyf) != '\0')
	    {
	      if (*yyp == '%' && yyf[1] == 's' && yyi < yycount)
		{
		  yyp += yytnamerr (yyp, yyarg[yyi++]);
		  yyf += 2;
		}
	      else
		{
		  yyp++;
		  yyf++;
		}
	    }
	}
      return yysize;
    }
}
#endif /* YYERROR_VERBOSE */


/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
#else
static void
yydestruct (yymsg, yytype, yyvaluep)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  YYUSE (yyvaluep);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
	break;
    }
}


/* Prevent warnings from -Wmissing-prototypes.  */

#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */



/* The look-ahead symbol.  */
int yychar;

/* The semantic value of the look-ahead symbol.  */
YYSTYPE yylval;

/* Number of syntax errors so far.  */
int yynerrs;



/*----------.
| yyparse.  |
`----------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{
  
  int yystate;
  int yyn;
  int yyresult;
  /* Number of tokens to shift before error messages enabled.  */
  int yyerrstatus;
  /* Look-ahead token as an internal (translated) token number.  */
  int yytoken = 0;
#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

  /* Three stacks and their tools:
     `yyss': related to states,
     `yyvs': related to semantic values,
     `yyls': related to locations.

     Refer to the stacks thru separate pointers, to allow yyoverflow
     to reallocate them elsewhere.  */

  /* The state stack.  */
  yytype_int16 yyssa[YYINITDEPTH];
  yytype_int16 *yyss = yyssa;
  yytype_int16 *yyssp;

  /* The semantic value stack.  */
  YYSTYPE yyvsa[YYINITDEPTH];
  YYSTYPE *yyvs = yyvsa;
  YYSTYPE *yyvsp;



#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  YYSIZE_T yystacksize = YYINITDEPTH;

  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;


  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY;		/* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */

  yyssp = yyss;
  yyvsp = yyvs;

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;


	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),

		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss);
	YYSTACK_RELOCATE (yyvs);

#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;


      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     look-ahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to look-ahead token.  */
  yyn = yypact[yystate];
  if (yyn == YYPACT_NINF)
    goto yydefault;

  /* Not known => get a look-ahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid look-ahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yyn == 0 || yyn == YYTABLE_NINF)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  if (yyn == YYFINAL)
    YYACCEPT;

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the look-ahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token unless it is eof.  */
  if (yychar != YYEOF)
    yychar = YYEMPTY;

  yystate = yyn;
  *++yyvsp = yylval;

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:
#line 90 "parser.y"
    { chunk_node = create_chunk_node((yyvsp[(1) - (1)]._stmt_seq_node)); ;}
    break;

  case 3:
#line 94 "parser.y"
    { (yyval._stmt_seq_node) = (yyvsp[(1) - (1)]._stmt_seq_node); ;}
    break;

  case 4:
#line 95 "parser.y"
    { (yyval._stmt_seq_node) = add_stmt_to_stmt_seq_node((yyvsp[(1) - (2)]._stmt_seq_node), (yyvsp[(2) - (2)]._stmt_node)); ;}
    break;

  case 5:
#line 96 "parser.y"
    { (yyval._stmt_seq_node) = add_stmt_to_stmt_seq_node((yyvsp[(1) - (3)]._stmt_seq_node), (yyvsp[(2) - (3)]._stmt_node)); ;}
    break;

  case 6:
#line 99 "parser.y"
    { (yyval._stmt_seq_node) = create_stmt_seq_node(); ;}
    break;

  case 7:
#line 100 "parser.y"
    { (yyval._stmt_seq_node) = add_stmt_to_stmt_seq_node((yyvsp[(1) - (2)]._stmt_seq_node), (yyvsp[(2) - (2)]._stmt_node)); ;}
    break;

  case 8:
#line 101 "parser.y"
    { (yyval._stmt_seq_node) = (yyvsp[(1) - (2)]._stmt_seq_node); ;}
    break;

  case 9:
#line 122 "parser.y"
    { (yyval._stmt_node) = create_assign_stmt_node((yyvsp[(1) - (3)]._expr_seq_node), (yyvsp[(3) - (3)]._expr_seq_node)); ;}
    break;

  case 10:
#line 123 "parser.y"
    { (yyval._stmt_node) = create_function_call_stmt_node((yyvsp[(1) - (1)]._expr_node)); ;}
    break;

  case 11:
#line 124 "parser.y"
    { (yyval._stmt_node) = create_break_stmt_node(); ;}
    break;

  case 12:
#line 125 "parser.y"
    { (yyval._stmt_node) = create_do_stmt_node((yyvsp[(2) - (3)]._stmt_seq_node)); ;}
    break;

  case 13:
#line 126 "parser.y"
    { (yyval._stmt_node) = create_cycle_stmt_node(WHILE_LOOP, (yyvsp[(2) - (5)]._expr_node), (yyvsp[(4) - (5)]._stmt_seq_node)); ;}
    break;

  case 14:
#line 127 "parser.y"
    { (yyval._stmt_node) = create_cycle_stmt_node(REPEAT_LOOP, (yyvsp[(4) - (4)]._expr_node), (yyvsp[(2) - (4)]._stmt_seq_node)); ;}
    break;

  case 15:
#line 128 "parser.y"
    { (yyval._stmt_node) = create_if_stmt_node((yyvsp[(2) - (6)]._expr_node), (yyvsp[(4) - (6)]._stmt_seq_node), (yyvsp[(5) - (6)]._stmt_seq_node), NULL); ;}
    break;

  case 16:
#line 129 "parser.y"
    { (yyval._stmt_node) = create_if_stmt_node((yyvsp[(2) - (8)]._expr_node), (yyvsp[(4) - (8)]._stmt_seq_node), (yyvsp[(5) - (8)]._stmt_seq_node), (yyvsp[(7) - (8)]._stmt_seq_node)); ;}
    break;

  case 17:
#line 130 "parser.y"
    { (yyval._stmt_node) = create_for_stmt_node((yyvsp[(2) - (9)].ident), (yyvsp[(4) - (9)]._expr_node), (yyvsp[(6) - (9)]._expr_node), NULL, (yyvsp[(8) - (9)]._stmt_seq_node)); ;}
    break;

  case 18:
#line 131 "parser.y"
    { (yyval._stmt_node) = create_for_stmt_node((yyvsp[(2) - (11)].ident), (yyvsp[(4) - (11)]._expr_node), (yyvsp[(6) - (11)]._expr_node), (yyvsp[(8) - (11)]._expr_node), (yyvsp[(10) - (11)]._stmt_seq_node)); ;}
    break;

  case 19:
#line 132 "parser.y"
    { (yyval._stmt_node) = create_foreach_stmt_node((yyvsp[(2) - (7)]._ident_list_node), (yyvsp[(4) - (7)]._expr_seq_node), (yyvsp[(6) - (7)]._stmt_seq_node)); ;}
    break;

  case 20:
#line 133 "parser.y"
    { (yyval._stmt_node) = create_function_def_stmt_node((yyvsp[(2) - (7)].ident), (yyvsp[(4) - (7)]._param_list_node), (yyvsp[(6) - (7)]._stmt_seq_node), false); ;}
    break;

  case 21:
#line 134 "parser.y"
    { (yyval._stmt_node) = create_function_def_stmt_node((yyvsp[(3) - (8)].ident), (yyvsp[(5) - (8)]._param_list_node), (yyvsp[(7) - (8)]._stmt_seq_node), true); ;}
    break;

  case 22:
#line 135 "parser.y"
    { (yyval._stmt_node) = create_local_var_stmt_node((yyvsp[(2) - (2)]._ident_list_node), NULL); ;}
    break;

  case 23:
#line 136 "parser.y"
    { (yyval._stmt_node) = create_local_var_stmt_node((yyvsp[(2) - (4)]._ident_list_node), (yyvsp[(4) - (4)]._expr_seq_node)); ;}
    break;

  case 24:
#line 139 "parser.y"
    { (yyval._stmt_seq_node) = create_elseif_seq_stmt_seq_node(); ;}
    break;

  case 25:
#line 140 "parser.y"
    { (yyval._stmt_seq_node) = add_elseif_seq_stmt_seq_node((yyvsp[(1) - (5)]._stmt_seq_node), (yyvsp[(3) - (5)]._expr_node), (yyvsp[(5) - (5)]._stmt_seq_node)); ;}
    break;

  case 26:
#line 145 "parser.y"
    { (yyval._stmt_node) = create_return_stmt_node(NULL); ;}
    break;

  case 27:
#line 146 "parser.y"
    { (yyval._stmt_node) = create_return_stmt_node((yyvsp[(2) - (2)]._expr_seq_node)); ;}
    break;

  case 28:
#line 150 "parser.y"
    { (yyval._expr_seq_node) = create_var_list_node((yyvsp[(1) - (1)]._var_node)); ;}
    break;

  case 29:
#line 151 "parser.y"
    { (yyval._expr_seq_node) = add_var_to_var_list_node((yyvsp[(1) - (3)]._expr_seq_node), (yyvsp[(3) - (3)]._var_node)); ;}
    break;

  case 30:
#line 155 "parser.y"
    { (yyval._var_node) = create_id_var_node((yyvsp[(1) - (1)].ident)); ;}
    break;

  case 31:
#line 156 "parser.y"
    { (yyval._var_node) = add_expr_to_var_node((yyvsp[(1) - (4)]._var_node), (yyvsp[(3) - (4)]._expr_node)); ;}
    break;

  case 32:
#line 157 "parser.y"
    { (yyval._var_node) = add_id_to_var_node((yyvsp[(1) - (3)]._var_node), (yyvsp[(3) - (3)].ident)); ;}
    break;

  case 33:
#line 158 "parser.y"
    { (yyval._var_node) = create_function_with_expr_var_node((yyvsp[(1) - (4)]._expr_node), (yyvsp[(3) - (4)]._expr_node)); ;}
    break;

  case 34:
#line 159 "parser.y"
    { (yyval._var_node) = create_function_with_id_var_node((yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)].ident)); ;}
    break;

  case 35:
#line 160 "parser.y"
    { (yyval._var_node) = create_expr_with_expr_var_node((yyvsp[(2) - (6)]._expr_node), (yyvsp[(5) - (6)]._expr_node)); ;}
    break;

  case 36:
#line 161 "parser.y"
    { (yyval._var_node) = create_expr_with_id_var_node((yyvsp[(2) - (5)]._expr_node), (yyvsp[(5) - (5)].ident)); ;}
    break;

  case 37:
#line 164 "parser.y"
    { (yyval._expr_node) = create_function_call_expr_node((yyvsp[(1) - (2)].ident), (yyvsp[(2) - (2)]._expr_seq_node)); ;}
    break;

  case 38:
#line 168 "parser.y"
    { (yyval._ident_list_node) = create_ident_list_node((yyvsp[(1) - (1)].ident)); ;}
    break;

  case 39:
#line 169 "parser.y"
    { (yyval._ident_list_node) = add_ident_to_ident_list_node((yyvsp[(1) - (3)]._ident_list_node), (yyvsp[(3) - (3)].ident)); ;}
    break;

  case 40:
#line 173 "parser.y"
    { (yyval._expr_seq_node) = create_expr_seq_node((yyvsp[(1) - (1)]._expr_node)); ;}
    break;

  case 41:
#line 174 "parser.y"
    { (yyval._expr_seq_node) = add_expr_to_expr_seq_node((yyvsp[(1) - (3)]._expr_seq_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 42:
#line 181 "parser.y"
    { (yyval._expr_node) = create_nil_expr_node(); ;}
    break;

  case 43:
#line 182 "parser.y"
    { (yyval._expr_node) = create_bool_expr_node(false); ;}
    break;

  case 44:
#line 183 "parser.y"
    { (yyval._expr_node) = create_bool_expr_node(true); ;}
    break;

  case 45:
#line 184 "parser.y"
    { (yyval._expr_node) = create_int_number_expr_node((yyvsp[(1) - (1)].int_number)); ;}
    break;

  case 46:
#line 185 "parser.y"
    { (yyval._expr_node) = create_float_number_expr_node((yyvsp[(1) - (1)].float_number)); ;}
    break;

  case 47:
#line 186 "parser.y"
    { (yyval._expr_node) = create_string_expr_node((yyvsp[(1) - (1)].string)); ;}
    break;

  case 48:
#line 187 "parser.y"
    { (yyval._expr_node) = create_var_arg_expr_node(); ;}
    break;

  case 49:
#line 188 "parser.y"
    { (yyval._expr_node) = create_var_expr_node((yyvsp[(1) - (1)]._var_node)); ;}
    break;

  case 50:
#line 189 "parser.y"
    { (yyval._expr_node) = (yyvsp[(1) - (1)]._expr_node); ;}
    break;

  case 51:
#line 190 "parser.y"
    { (yyval._expr_node) = create_adjusting_expr_node((yyvsp[(2) - (3)]._expr_node)); ;}
    break;

  case 52:
#line 191 "parser.y"
    { (yyval._expr_node) = create_table_constructor_expr_node((yyvsp[(1) - (1)]._field_list_node)); ;}
    break;

  case 53:
#line 192 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(MINUS, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 54:
#line 193 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(MUL, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 55:
#line 194 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(PLUS, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 56:
#line 195 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(DIV, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 57:
#line 196 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(_FLOOR_DIV, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 58:
#line 197 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(POW, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 59:
#line 198 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(MOD, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 60:
#line 199 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(BIT_AND, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 61:
#line 200 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(XOR, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 62:
#line 201 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(BIT_OR, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 63:
#line 202 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(_CONCAT, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 64:
#line 203 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(LESS, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 65:
#line 204 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(_LE, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 66:
#line 205 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(GREATER, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 67:
#line 206 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(_GE, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 68:
#line 207 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(EQUAL, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 69:
#line 208 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(NOT_EQUAL, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 70:
#line 209 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(LOG_AND, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 71:
#line 210 "parser.y"
    { (yyval._expr_node) = create_bin_expr_node(LOG_OR, (yyvsp[(1) - (3)]._expr_node), (yyvsp[(3) - (3)]._expr_node)); ;}
    break;

  case 72:
#line 211 "parser.y"
    { (yyval._expr_node) = create_unary_expr_Node(UNARY_MINUS, (yyvsp[(2) - (2)]._expr_node)); ;}
    break;

  case 73:
#line 212 "parser.y"
    { (yyval._expr_node) = create_unary_expr_Node(_NOT, (yyvsp[(2) - (2)]._expr_node)); ;}
    break;

  case 74:
#line 213 "parser.y"
    { (yyval._expr_node) = create_unary_expr_Node(LEN, (yyvsp[(2) - (2)]._expr_node)); ;}
    break;

  case 75:
#line 214 "parser.y"
    { (yyval._expr_node) = create_unary_expr_Node(BIT_NOT, (yyvsp[(2) - (2)]._expr_node)); ;}
    break;

  case 76:
#line 218 "parser.y"
    { (yyval._expr_seq_node) = NULL; ;}
    break;

  case 77:
#line 219 "parser.y"
    { (yyval._expr_seq_node) = (yyvsp[(2) - (3)]._expr_seq_node); ;}
    break;

  case 78:
#line 220 "parser.y"
    { (yyval._expr_seq_node) = create_table_constructor_expr_seq_node((yyvsp[(1) - (1)]._field_list_node)); ;}
    break;

  case 79:
#line 221 "parser.y"
    { (yyval._expr_seq_node) = create_string_expr_seq_node((yyvsp[(1) - (1)].string)); ;}
    break;

  case 80:
#line 225 "parser.y"
    { (yyval._param_list_node) = create_param_list_node(NULL, false); ;}
    break;

  case 81:
#line 226 "parser.y"
    { (yyval._param_list_node) = create_param_list_node((yyvsp[(1) - (1)]._ident_list_node), false); ;}
    break;

  case 82:
#line 227 "parser.y"
    { (yyval._param_list_node) = create_param_list_node((yyvsp[(1) - (3)]._ident_list_node), true); ;}
    break;

  case 83:
#line 228 "parser.y"
    { (yyval._param_list_node) = create_param_list_node(NULL, true); ;}
    break;

  case 84:
#line 232 "parser.y"
    { (yyval._field_list_node) = NULL; ;}
    break;

  case 85:
#line 233 "parser.y"
    { (yyval._field_list_node) = (yyvsp[(2) - (3)]._field_list_node); ;}
    break;

  case 86:
#line 234 "parser.y"
    { (yyval._field_list_node) = (yyvsp[(2) - (4)]._field_list_node); ;}
    break;

  case 87:
#line 238 "parser.y"
    { (yyval._field_list_node) = create_field_list_node((yyvsp[(1) - (1)]._field_node)); ;}
    break;

  case 88:
#line 239 "parser.y"
    { (yyval._field_list_node) = add_field_to_field_list_node((yyvsp[(1) - (3)]._field_list_node), (yyvsp[(3) - (3)]._field_node)); ;}
    break;

  case 89:
#line 243 "parser.y"
    { (yyval._field_node) = create_field_node((yyvsp[(1) - (3)].ident), (yyvsp[(3) - (3)]._expr_node), NULL); ;}
    break;

  case 90:
#line 244 "parser.y"
    { (yyval._field_node) = create_field_node(NULL, (yyvsp[(5) - (5)]._expr_node), (yyvsp[(2) - (5)]._expr_node)); ;}
    break;

  case 91:
#line 245 "parser.y"
    { (yyval._field_node) = create_field_node(NULL, (yyvsp[(1) - (1)]._expr_node), NULL); ;}
    break;

  case 92:
#line 249 "parser.y"
    { (yyval._field_sep_node) = (yyvsp[(1) - (1)]._field_sep_node); /*???????????????? ?????? ??????-???? ????-?????????????? ???????? ?????????????? ?????????? ???????????????? ?????????????? ?????? ?????? ',' ?? ';'*/ ;}
    break;

  case 93:
#line 250 "parser.y"
    { (yyval._field_sep_node) = (yyvsp[(1) - (1)]._field_sep_node); /*???????????????? ?????? ??????-???? ????-?????????????? ???????? ?????????????? ?????????? ???????????????? ?????????????? ?????? ?????? ',' ?? ';'*/ ;}
    break;


/* Line 1267 of yacc.c.  */
#line 2160 "parser.tab.c"
      default: break;
    }
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;


  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
      {
	YYSIZE_T yysize = yysyntax_error (0, yystate, yychar);
	if (yymsg_alloc < yysize && yymsg_alloc < YYSTACK_ALLOC_MAXIMUM)
	  {
	    YYSIZE_T yyalloc = 2 * yysize;
	    if (! (yysize <= yyalloc && yyalloc <= YYSTACK_ALLOC_MAXIMUM))
	      yyalloc = YYSTACK_ALLOC_MAXIMUM;
	    if (yymsg != yymsgbuf)
	      YYSTACK_FREE (yymsg);
	    yymsg = (char *) YYSTACK_ALLOC (yyalloc);
	    if (yymsg)
	      yymsg_alloc = yyalloc;
	    else
	      {
		yymsg = yymsgbuf;
		yymsg_alloc = sizeof yymsgbuf;
	      }
	  }

	if (0 < yysize && yysize <= yymsg_alloc)
	  {
	    (void) yysyntax_error (yymsg, yystate, yychar);
	    yyerror (yymsg);
	  }
	else
	  {
	    yyerror (YY_("syntax error"));
	    if (yysize != 0)
	      goto yyexhaustedlab;
	  }
      }
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse look-ahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse look-ahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (yyn != YYPACT_NINF)
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;


      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  if (yyn == YYFINAL)
    YYACCEPT;

  *++yyvsp = yylval;


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#ifndef yyoverflow
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEOF && yychar != YYEMPTY)
     yydestruct ("Cleanup: discarding lookahead",
		 yytoken, &yylval);
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}


#line 253 "parser.y"


