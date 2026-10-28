package _5bits.organik.platform.iam.interfaces.rest.transform;

import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(
                entity.getId(),
                entity.getFullName().value(),
                entity.getEmail().value(),
                entity.getRole().name(),
                entity.getStatus().name(),
                entity.getMinimarketId() == null ? null : entity.getMinimarketId().value(),
                entity.getSupplierId() == null ? null : entity.getSupplierId().value());
    }
}