package server;

import model.Board;
import model.BoardController;

import java.util.ArrayList;

public class GomokuController {

    private RandomMachine randomMachine = new RandomMachine();
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    private JSONController jsonController = new JSONController();

    public String generateRandomKey() {
        String id = randomMachine.nextString();
        return id;
    }

    public String getEmptyBoard() {
        ArrayList<Board> boards = databaseConnector.getFreeBoard();
        return jsonController.parseBoardsToJSON(boards);
    }

    public boolean createNewBoard(String userID) {
        return databaseConnector.createBoard(userID);
    }

    public boolean joinToBoard(String userID, String boardId) {
        return databaseConnector.joinToBoard(userID, boardId);
    }

    public String getBoardOwner(String boardId) {
        return databaseConnector.getBoardUserOne(boardId);
    }

    public Board getBoardByUser(String userId) {
        return databaseConnector.getBoardByUser(userId);
    }

    public boolean saveBoardToBase(String boardId, String board) {
        return databaseConnector.saveBoard(boardId, board);
    }
}
