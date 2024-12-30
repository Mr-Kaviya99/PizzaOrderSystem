package model.customize;

/**
 * author k2460782
 */
public interface CustomizationHandler {
    void setNextHandler(CustomizationHandler nextHandler);

    void handleCustomization(OrderCustomizationRequest request);
}
