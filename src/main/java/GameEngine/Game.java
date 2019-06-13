package GameEngine;

import Editor.Model.Map;
import GameEngine.Controller.PlayerController;
import GameEngine.Model.GameModel;
import GameEngine.View.GameView;

import java.util.ArrayList;

public class Game {
    public Game(String title, ArrayList<Map> maps) {
        GameModel gameModel = new GameModel(maps);
        PlayerController playerController = new PlayerController(gameModel);
        GameView gameView = new GameView(title, playerController);
        gameModel.addObserver(gameView);
    }
}