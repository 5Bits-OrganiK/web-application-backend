package _5bits.organik.platform.iam.domain.model.commands;

import _5bits.organik.platform.iam.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.iam.domain.model.valueobjects.FullName;
import _5bits.organik.platform.iam.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.iam.domain.model.valueobjects.Role;
import _5bits.organik.platform.iam.domain.model.valueobjects.SupplierId;

public record CreateUserCommand(FullName fullName, EmailAddress email, Role role, MinimarketId minimarketId, SupplierId supplierId) {
    public CreateUserCommand {
        if (fullName == null) {
            throw new IllegalArgumentException("FullName cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("EmailAddress cannot be null");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
    }
}
