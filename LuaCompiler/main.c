#include <stdio.h>
#include <stdlib.h>
#include "tree_nodes.h"

extern FILE * yyin;
extern int yyparse();
extern struct chunk_node * chunk_node;
extern void print_program(struct chunk_node * chunk_node, FILE * file);
extern void program_to_xml(struct chunk_node * chunk_node, FILE * file);

int main(int argc, char ** argv){

    yyin = fopen(argv[1], "r");

    FILE * tree = fopen("tree.dot", "w");
    FILE * xml = fopen("xml.xml", "w");

    yyparse();

    print_program(chunk_node, tree);
    program_to_xml(chunk_node, xml);

    return 0;
}
