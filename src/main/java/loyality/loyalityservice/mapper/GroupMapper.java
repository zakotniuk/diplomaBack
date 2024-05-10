package loyality.loyalityservice.mapper;

import loyality.loyalityservice.dto.GroupDto;
import loyality.loyalityservice.entity.Group;

public class GroupMapper {
    public static GroupDto mapToGroupDto(Group group){
        return new GroupDto(
                group.getId(),
                group.getGroupName(),
               // group.getCompany(),
                group.getCompanyId(),
                group.getCondition(),
                group.getDiscount(),
                group.getAvailablePart(),
                group.getIsDefault()

        );
    }

    public static Group mapToGroup(GroupDto groupDto){
        return new Group(
                groupDto.getId(),
                groupDto.getGroupName(),
             //   groupDto.getCompany(),
                groupDto.getCompanyId(),
                groupDto.getCondition(),
                groupDto.getDiscount(),
                groupDto.getAvailablePart(),
                groupDto.getIsDefault()
        );
    }
}
