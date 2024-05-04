# OS_Project_Phase2 Report
## Team
- Basil Saeed bm2108258@student.qu.edu.qa
- Fane Doe fd0000003@student.qu.edu.qa
- Sane Doe sd0000002@student.qu.edu.qa
- Zane Doe zd0000004@student.qu.edu.qa

## Challenges
1. First we had trouble on planning the architecture of the project, meaning what classes we would have, and how the client would interact with the server, so we made a sequence diagram for that, and that cleared things up.
2. Regarding the architecture, at first we thought that the Server would have multiple threads of Games running, and we coded half way with this logics, but that was not the case, and we realized that a Game was a shared buffer and the threads to be created were for each Client/Player, and those would write to the shared buffer (Game).
3. We had issues debugging the program, since there is no sequential execution, and more than one thread made it difficult to pinpoint where exactly there was a mistake in the Server/Game logic.
4. ...
## Issues
1. Issue #1 with details
2. Issue #2 with details
3. ...
## Contributions
### Basil Saeed:
- coded all of the database persistence of the player, its ticket and numberOfWins using JDBC for SQLite.
- coded and debugged the parse methods of the Player.java with teammate.
### Mahodi Hasan Sabab
### Mohammad Hassam Tahir Khaili:
### Muhammad Khan:
