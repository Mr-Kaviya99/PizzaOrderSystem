package model.customize;
/**
 * author k2460782
 */

import model.Pizza;

public class PizzaCrustHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleCustomization(OrderCustomizationRequest request) {
        Pizza pizza = request.getPizza();
        if (pizza.getCrust() == null) {
            System.out.println("Applying default crust: Thin Crust");
            pizza.setCrust("Thin Crust");
        }
        if (nextHandler != null) {
            nextHandler.handleCustomization(request);
        }
    }
}

