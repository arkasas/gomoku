package model;

public class GameMessage {
    private String userOne;
    private String userTwo;
    private String state;
    private String board;

    public GameMessage() {}

    public GameMessage(String userOne, String userTwo, String state, String board) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.state = state;
        this.board = board;
    }

    public String getUserOne() {
        return userOne;
    }

    public void setUserOne(String userOne) {
        this.userOne = userOne;
    }

    public String getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(String userTwo) {
        this.userTwo = userTwo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
}
