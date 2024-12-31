package model.customize;
/**
 * author k2460782
 */

import model.Pizza;

public class PizzaCheeseHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleCustomization(OrderCustomizationRequest request) {
        Pizza pizza = request.getPizza();
        if (pizza.getCheese() == null) {
            System.out.println("Applying default cheese: Mozzarella");
            pizza.setCheese("Mozzarella");
        }
        if (nextHandler != null) {
            nextHandler.handleCustomization(request);
        }
    }
}

