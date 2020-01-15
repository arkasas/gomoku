package model;

public class Board {
    private int id;
    private String userOne;
    private String userTwo;
    private String board;
    private String state;

    public Board() {}
    public Board(int id, String userOne, String userTwo, String board, String state) {
        this.id = id;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.board = board;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
