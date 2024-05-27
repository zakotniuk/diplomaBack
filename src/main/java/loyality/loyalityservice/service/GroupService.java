package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.GroupDto;

import java.util.List;

public interface GroupService {
    //группа создается в определенной компании!
    GroupDto createGroup(Long companyId, GroupDto groupDto);

    void resetDefaultGroup(Long companyId);

    GroupDto updateGroup(Long companyId, Long groupId, GroupDto groupDto);

    //группы ищутся по определенной компании!
    List<GroupDto> getAllGroups(Long companyId);

    void deleteGroup(Long companyId, Long groupId);
}
