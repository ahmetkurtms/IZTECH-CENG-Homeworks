#ifndef SHELL322_H
#define SHELL322_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <errno.h>

#define MAX_COMMAND_LENGTH 100 // Maximum length of a command
#define MAX_ARGS 10 // Maximum number of arguments
#define HISTORY_SIZE 10 // Maximum number of commands in history

// Structure to hold command history
// This structure will store the last N commands entered by the user
typedef struct {
    char commands[HISTORY_SIZE][MAX_COMMAND_LENGTH];
    int count;
    int start;
} CommandHistory;

// Functions
void initHistory(CommandHistory *history);
void addToHistory(CommandHistory *history, const char *command);
void printHistory(const CommandHistory *history);
void executePipeCommand(char **args1, char **args2);
void parseAndExecute(char *line, CommandHistory *history);
int handleBuiltInCommands(char **args, CommandHistory *history);

#endif
