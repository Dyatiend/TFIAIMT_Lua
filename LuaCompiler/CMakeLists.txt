cmake_minimum_required(VERSION 3.21.1)
project(LuaCompiler C)

set(CMAKE_C_STANDARD 99)

add_executable(LuaCompiler parser.tab.c tree_nodes.h print_tree.h print_tree.c create_nodes.h create_nodes.c lex.yy.c)
