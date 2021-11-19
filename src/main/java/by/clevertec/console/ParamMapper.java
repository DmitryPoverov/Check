package by.clevertec.console;

public class ParamMapper {

    private int id;
    private int quantity;

    public ParamMapper() {
    }

    public int getId() {
        return id;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ParamMapper{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
