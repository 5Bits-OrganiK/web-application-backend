package _5bits.organik.platform.suppliers.domain.model.commands;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.BusinessName;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ContactName;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.OrganicCertification;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.PhoneNumber;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierCategory;

import java.util.Set;

public record CreateSupplierCommand(BusinessName businessName, ContactName contactName, PhoneNumber phoneNumber, EmailAddress email, Set<SupplierCategory> categories, OrganicCertification organicCertification) {
    public CreateSupplierCommand {
        if (businessName == null) {
            throw new IllegalArgumentException("BusinessName cannot be null");
        }
        if (contactName == null) {
            throw new IllegalArgumentException("ContactName cannot be null");
        }
        if (phoneNumber == null) {
            throw new IllegalArgumentException("PhoneNumber cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("EmailAddress cannot be null");
        }
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories cannot be empty");
        }
        if (organicCertification == null) {
            throw new IllegalArgumentException("OrganicCertification cannot be null");
        }
    }
}
