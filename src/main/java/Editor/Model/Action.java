package Editor.Model;

abstract class Action {

    Model model;

    Action(Model model) {
        this.model = model;
    }
    public abstract void undo();
    public abstract void redo();
}