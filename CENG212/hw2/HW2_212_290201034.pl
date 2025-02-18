%Start R = Start Row, Start C = Start Column, Exit R = Exit Row, Exit C = Exit Column, Obstacles = List of obstacles
%CurR = Current Row, CurC = Current Column, NextR = Next Row, NextC = Next Column, PathSoFar = Path traversed so far
%Visited = List of visited nodes, Queue = List of nodes to be explored, Moves = List of possible moves, NewQueue = Updated list of nodes to be explored

%And the example usage:
%?- path((1,1), (4,5), [(2,2), (3,2), (4,4)], Path).
%You can add more obstacles to the list to see how the path changes.

%Start to exit path with obstacles.
path((StartR, StartC), (ExitR, ExitC), Obstacles, Path) :-
    bfs([(StartR, StartC, [(StartR, StartC)])], (ExitR, ExitC), Obstacles, RevPath),
    (RevPath \= [], reverse(RevPath, Path) ; write('No path found'), fail). %No path found if the path is empty

%Our base case is when the current node is the exit node.
bfs([(CurR, CurC, PathSoFar) | _], (CurR, CurC), _, PathSoFar).

%Our recursive case is when the current node is not the exit node.
bfs([(CurR, CurC, Visited) | Queue], (ExitR, ExitC), Obstacles, Path) :-
    findall((NextR, NextC, [(NextR, NextC) | Visited]),
            (move((CurR, CurC), (NextR, NextC)),
                \+ member((NextR, NextC), Visited), %Check if the node has been visited
                \+ member((NextR, NextC), Obstacles)), %Check if the node is an obstacle
            Moves),
    append(Queue, Moves, NewQueue), %Add the new moves to the queue
    bfs(NewQueue, (ExitR, ExitC), Obstacles, Path). %Loop through the new queue

%If there is no path found, print a message and fail.
bfs([], _, _, []) :-
    write('No path found'), fail.

%Possible moves in the 5x5 grid.
move((CurR, CurC), (NextR, CurC)) :- NextR is CurR - 1, NextR > 0. %Move up
move((CurR, CurC), (NextR, CurC)) :- NextR is CurR + 1, NextR =< 5. %Move down
move((CurR, CurC), (CurR, NextC)) :- NextC is CurC - 1, NextC > 0. %Move left
move((CurR, CurC), (CurR, NextC)) :- NextC is CurC + 1, NextC =< 5. %Move right

%If the path is too long, in the output there are "...". This is because the path is too long to be displayed in the console.