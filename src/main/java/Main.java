import Editor.Controller.Controller;
import Editor.Model.Model;
import Editor.View.UserInterface;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        UserInterface userInterface = new UserInterface("MyRpgMaker", controller);
        model.addObserver(userInterface);
    }
}