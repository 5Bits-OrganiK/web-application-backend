package _5bits.organik.platform.iam.interfaces.rest.transform;

import _5bits.organik.platform.iam.domain.model.commands.CreateUserCommand;
import _5bits.organik.platform.iam.interfaces.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {
    public static CreateUserCommand toCommandFromResource(CreateUserResource resource) {
        return new CreateUserCommand(
                IamValueObjectFromStringAssembler.toFullNameFromString(resource.fullName()),
                IamValueObjectFromStringAssembler.toEmailAddressFromString(resource.email()),
                IamValueObjectFromStringAssembler.toRoleFromString(resource.role()),
                IamValueObjectFromStringAssembler.toMinimarketIdFromString(resource.minimarketId()),
                IamValueObjectFromStringAssembler.toSupplierIdFromString(resource.supplierId()));
    }
}
