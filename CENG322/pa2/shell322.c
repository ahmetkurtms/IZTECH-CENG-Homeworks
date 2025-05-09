#include "shell322.h"

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
initHistory: Initializes the command history structure.
The history is initialized with a count of 0 and start index of 0.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */



void initHistory(CommandHistory *history)
{
  history->count = 0;
  history->start = 0;
}



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
addToHistory: Adds a command to the history.
index is the index where the command will be added.
The command is added to the history in a circular manner.
If the history is full, it overwrites the oldest command.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */



void addToHistory(CommandHistory *history, const char *command)
{
  int index = (history->start + history->count) % HISTORY_SIZE;
  strncpy(history->commands[index], command, MAX_COMMAND_LENGTH - 1); // MAX_COMMAND_LENGTH - 1 to leave space for null terminator
  history->commands[index][MAX_COMMAND_LENGTH - 1] = '\0';            // Ensure null termination

  if (history->count < HISTORY_SIZE)
  {
    history->count++; // If history is not full, just increment the count
  }
  else
  {
    history->start = (history->start + 1) % HISTORY_SIZE; // If full, move start to the next command and overwrite the oldest command
  }
}



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
printHistory: Prints the command history.
The history is printed in the order of the commands entered.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */

void printHistory(const CommandHistory *history)
{
  for (int i = 0; i < history->count; i++)
  {
    int index = (history->start + i) % HISTORY_SIZE;      // Calculate the index of the command to print
    printf("[%d] %s\n", i + 1, history->commands[index]); // Print the command with its index
  }
}



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
handleBuiltInCommands: Handles built-in commands like:
- cd: Change directory
- pwd: Print working directory
- history: Print command history
- exit: Exit the shell
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */
int handleBuiltInCommands(char **args, CommandHistory *history)
{
  if (args[0] == NULL)
  {
    return 1;
  }

  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  cd: Change directory
  If no argument is provided, change to the home directory.
  If the directory change fails, print an error message.
  If the
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  if (strcmp(args[0], "cd") == 0)
  { // Check if the command is "cd"
    char *dir = args[1];
    if (dir == NULL)
    {
      dir = getenv("HOME"); // Change to home directory if no argument is provided (for me it is /home/ahmetkurt)
    }
    if (chdir(dir) != 0)
    {
      perror("\033[0;31mcd failed\033[0m"); // Red Color, Print error message if directory change fails
    }
    else
    { // Update the PWD environment variable
      char cwd[MAX_COMMAND_LENGTH];
      if (getcwd(cwd, sizeof(cwd)) != NULL)
      {
        setenv("PWD", cwd, 1);
      }
    }
    return 1;
  }

  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  pwd: Print working directory
  If the getcwd function fails, print an error message.
  The current working directory is printed to the standard output.
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  if (strcmp(args[0], "pwd") == 0)
  {                               // Check if the command is "pwd"
    char cwd[MAX_COMMAND_LENGTH]; // Buffer to store the current working directory, max command length = 100
    if (getcwd(cwd, sizeof(cwd)) != NULL)
    {
      printf("%s\n", cwd);
    }
    return 1;
  }

  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  history: Print command history
  The command history is printed in the order of the commands entered.
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  if (strcmp(args[0], "history") == 0)
  { // Check if the command is "history"
    printHistory(history);
    return 1;
  }

  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  exit: Exit the shell
  If the command is "exit", the shell prints a goodbye message.
  The shell exits with a status code of 0.
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  if (strcmp(args[0], "exit") == 0)
  {
    printf("\033[0;30mExiting shell322...\033[0m\n");
    sleep(1); // Sleep for 1 second before exiting
    printf("\033[0;30mGoodbye!\033[0m\n");
    exit(0);
  }

  return 0;
}

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
executePipeCommand: Executes a command with a pipe.
Pipes are used to connect the output of one command to the input of another.
The function creates two child processes:
1. The first child process executes the first command and writes its output to the pipe.
2. The second child process reads from the pipe and executes the second command.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */

void executePipeCommand(char **args1, char **args2)
{
  int pipefd[2];    // pipefd[0] is read end, pipefd[1] is write end
  pid_t pid1, pid2; // Process IDs for the two child processes

  pipe(pipefd); // Create a pipe

  // Create first child process
  pid1 = fork();
  if (pid1 == 0)
  {
    close(pipefd[0]); // Close read end, we only need the write end
    dup2(pipefd[1], STDOUT_FILENO);
    close(pipefd[1]); // Close write end, we only need the read end

    // Print error message if exec fails
    if (execvp(args1[0], args1) == -1)
    {
      perror("shell");
      exit(EXIT_FAILURE); // Exit with failure status (1)
    }
  }

  // Create second child process
  pid2 = fork();
  if (pid2 == 0)
  {
    close(pipefd[1]); // Close write end, we only need the read end
    dup2(pipefd[0], STDIN_FILENO);
    close(pipefd[0]); // Close read end, we only need the write end

    // Print error message if exec fails
    if (execvp(args2[0], args2) == -1)
    {
      perror("shell");
      exit(EXIT_FAILURE); // Exit with failure status (1)
    }
  }

  // Close pipe ends in parent process
  close(pipefd[0]);
  close(pipefd[1]);
  waitpid(pid1, NULL, 0); // Wait for first child process
  waitpid(pid2, NULL, 0); // Wait for second child process
}

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
parseAndExecute: Parses and executes a command line.
The function handles:
1. Background processes (commands ending with '&')
2. Pipe commands (commands connected with '|')
3. Logical AND commands (commands connected with '&&')
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */

void parseAndExecute(char *line, CommandHistory *history)
{
  char *args[MAX_ARGS];
  int background = 0;    // Flag for background process
  char *pipe_cmd = NULL; // Pointer to the pipe command
  char *and_cmd = NULL;  // Pointer to the logical AND command

  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  Check for background process
  If the command ends with '&', set the background flag to 1.
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  if (line[strlen(line) - 1] == '&') // If the command ends with '&'
  {
    background = 1;
  }


  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  Check for pipe command
  If the command contains '|', split the command into two parts.
  The first part is executed in the first child process, and the
  second part is executed in the second child process.
  The pipe connects the output of the first command to the input
  of the second command.
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  pipe_cmd = strstr(line, " | "); // Check for pipe " |"
  if (pipe_cmd)
  {
    *pipe_cmd = '\0';
    pipe_cmd += 3; // Skip " | "

    char *args1[MAX_ARGS], *args2[MAX_ARGS];
    char *token = strtok(line, " ");
    int i = 0;
    while (token != NULL && i < MAX_ARGS - 1)
    { // MAX_ARGS - 1 to leave space for null terminator
      args1[i++] = token;
      token = strtok(NULL, " ");
    }
    args1[i] = NULL; // Ensure null termination


    // Parse second command
    token = strtok(pipe_cmd, " ");
    i = 0;
    while (token != NULL && i < MAX_ARGS - 1)
    {
      args2[i++] = token;
      token = strtok(NULL, " ");
    }
    args2[i] = NULL;


    executePipeCommand(args1, args2); // Execute the pipe command
    return;
  }

  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  Check for logical AND command
  If the command contains '&&', split the command into two parts.
  The first part is executed in the first child process, and the
  second part is executed in the parent process only if the
  first command succeeds (returns 0). (AND operator)
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  and_cmd = strstr(line, " && "); // Check for logical AND " && "
  if (and_cmd)
  {
    *and_cmd = '\0';
    and_cmd += 4; // Skip " && "

    // Parse and execute the first command
    char *first_cmd = strdup(line); // Duplicate the string to avoid modifying the original
    char *token = strtok(first_cmd, " ");
    int i = 0;
    while (token != NULL && i < MAX_ARGS - 1)
    { // MAX_ARGS - 1 to leave space for null terminator
      args[i++] = token;
      token = strtok(NULL, " ");
    }
    args[i] = NULL; // Ensure null termination

    // Check for built-in commands
    // If the first command is a built-in command, execute it
    pid_t pid = fork();
    if (pid == 0)
    { // Child process
      if (execvp(args[0], args) == -1)
      {
        perror("shell");
        exit(EXIT_FAILURE);
      }
    }
    else if (pid < 0)
    { // Fork error
      perror("shell");
    }
    else
    { // Parent process
      int status;
      waitpid(pid, &status, 0); // Wait for the first command to finish
      if (WIFEXITED(status) && WEXITSTATUS(status) == 0)
      { // Check if the first command succeeded
        // If the first command succeeded, execute the second command
        parseAndExecute(and_cmd, history);
      }
    }

    // Free the duplicated string
    free(first_cmd);
    return;
  }

  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  Parse the command line into arguments
  The command line is split into individual arguments using
  whitespace as the delimiter. The arguments are stored in
  the args array. The last element of the array is set to NULL
  to indicate the end of the arguments.
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  char *token = strtok(line, " "); // Split the command line into tokens
  int i = 0;
  while (token != NULL && i < MAX_ARGS - 1)
  {
    args[i++] = token;
    token = strtok(NULL, " ");
  }
  args[i] = NULL;

  if (!handleBuiltInCommands(args, history))
  {
    // If the command is not a built-in command, create a child process
    pid_t pid = fork();

    if (pid == 0)
    { // Child process
      if (execvp(args[0], args) == -1)
      {
        perror("shell");
        exit(EXIT_FAILURE);
      }
    }
    else if (pid < 0)
    { // Fork error
      perror("shell");
    }
    else
    { // Parent process
      if (!background)
      {
        waitpid(pid, NULL, 0); // Wait for the child process to finish
      }
      else
      {
        printf("%d\n", pid); // Print the PID of the background process
      }
    }
  }
}

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
main: Main function of the shell program.
while loop to read commands from the user.
if fgets returns NULL, break the loop.
The command is read from standard input and stored in the line variable.
The command is then parsed and executed using the parseAndExecute function.
The command is added to the history using the addToHistory function.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */

int main()
{
  char line[MAX_COMMAND_LENGTH];
  CommandHistory history;
  initHistory(&history);

  while (1)
  {
    printf("\033[0;32mshell322> \033[0m");
    fflush(stdout);

    if (fgets(line, MAX_COMMAND_LENGTH, stdin) == NULL)
    {
      break;
    }

    // Remove newline character
    size_t len = strlen(line);
    if (len > 0 && line[len - 1] == '\n')
    {
      line[len - 1] = '\0';
    }

    // Skip empty lines
    if (strlen(line) == 0)
    {
      continue;
    }

    // Add to history
    addToHistory(&history, line);

    // Execute command
    parseAndExecute(line, &history);
  }

  return 0;
}
