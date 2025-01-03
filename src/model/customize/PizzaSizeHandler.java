package model.customize;
/**
 * author k2460782
 */

import model.Pizza;
import model.enums.PizzaSize;

public class PizzaSizeHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleCustomization(OrderCustomizationRequest request) {
        Pizza pizza = request.getPizza();
        if (pizza.getSize() == null) {
            System.out.println("Applying default pizza size: Medium");
            pizza.setSize(PizzaSize.REGULAR);
        }
        if (nextHandler != null) {
            nextHandler.handleCustomization(request);
        }
    }
}

