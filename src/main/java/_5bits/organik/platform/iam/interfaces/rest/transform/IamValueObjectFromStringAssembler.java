package _5bits.organik.platform.iam.interfaces.rest.transform;

import _5bits.organik.platform.iam.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.iam.domain.model.valueobjects.FullName;
import _5bits.organik.platform.iam.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.iam.domain.model.valueobjects.Role;
import _5bits.organik.platform.iam.domain.model.valueobjects.SupplierId;

public class IamValueObjectFromStringAssembler {
    public static FullName toFullNameFromString(String value) {
        return new FullName(value);
    }

    public static EmailAddress toEmailAddressFromString(String value) {
        return new EmailAddress(value);
    }

    public static Role toRoleFromString(String value) {
        return Role.valueOf(value);
    }

    public static MinimarketId toMinimarketIdFromString(String value) {
        return value == null || value.isBlank() ? null : new MinimarketId(value);
    }

    public static SupplierId toSupplierIdFromString(String value) {
        return value == null || value.isBlank() ? null : new SupplierId(value);
    }
}