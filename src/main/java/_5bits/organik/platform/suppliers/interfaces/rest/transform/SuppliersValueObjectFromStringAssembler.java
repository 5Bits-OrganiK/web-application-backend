package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.BusinessName;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ContactName;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.OrganicCertification;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.PhoneNumber;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierCategory;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;

public class SuppliersValueObjectFromStringAssembler {
    public static BusinessName toBusinessNameFromString(String value) {
        return new BusinessName(value);
    }

    public static ContactName toContactNameFromString(String value) {
        return new ContactName(value);
    }

    public static PhoneNumber toPhoneNumberFromString(String value) {
        return new PhoneNumber(value);
    }

    public static EmailAddress toEmailAddressFromString(String value) {
        return new EmailAddress(value);
    }

    public static SupplierCategory toSupplierCategoryFromString(String value) {
        return SupplierCategory.valueOf(value);
    }

    public static OrganicCertification toOrganicCertificationFromString(String value) {
        return new OrganicCertification(value);
    }

    public static SupplierId toSupplierIdFromString(String value) {
        return new SupplierId(value);
    }

    public static ProductId toProductIdFromString(String value) {
        return new ProductId(value);
    }

    public static LotCode toLotCodeFromString(String value) {
        return new LotCode(value);
    }
}
