package _5bits.organik.platform.procurements.application.commandservices;

public sealed interface ShipmentOrderCommandFailure
        permits ShipmentOrderCommandFailure.NotFound, ShipmentOrderCommandFailure.InvalidState {
    String messageKey();

    record NotFound() implements ShipmentOrderCommandFailure {
        @Override
        public String messageKey() {
            return "shipmentOrder.error.notFound";
        }
    }

    record InvalidState() implements ShipmentOrderCommandFailure {
        @Override
        public String messageKey() {
            return "shipmentOrder.error.invalidState";
        }
    }
}
